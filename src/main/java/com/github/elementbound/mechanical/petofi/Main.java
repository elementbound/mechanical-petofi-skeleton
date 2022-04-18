package com.github.elementbound.mechanical.petofi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.github.elementbound.mechanical.petofi.poet.PoetContext;
import com.github.elementbound.mechanical.petofi.poet.PoetContextFactory;
import com.github.elementbound.mechanical.petofi.poet.PoetDescriptor;
import com.github.elementbound.mechanical.petofi.poet.PoetDescriptorProvider;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TabBehaviour;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Main {
    private static final int SUGGESTION_COUNT = 5;
    private PoetDescriptor promptedPoet;

    public static void main(String[] args) {
        (new Main()).run();
    }

    private PoetDescriptor promptPoet(WindowBasedTextGUI tui, List<PoetDescriptor> descriptors) {
        ActionListDialogBuilder dialogBuilder = new ActionListDialogBuilder()
                .setTitle("Choose your poet!")
                .setCanCancel(false);

        promptedPoet = null;

        for (PoetDescriptor descriptor : descriptors) {
            dialogBuilder.addAction(descriptor.getName(), () -> {
                promptedPoet = descriptor;
            });
        }

        dialogBuilder.build().showDialog(tui);

        return promptedPoet;
    }

    private Window showPopup(WindowBasedTextGUI tui, String text) {
        Window window = new BasicWindow("");
        window.setHints(List.of(
                Window.Hint.CENTERED,
                Window.Hint.MODAL
        ));
        window.setComponent(new Label(text));
        tui.addWindow(window);

        return window;
    }

    private void runEditor(WindowBasedTextGUI tui, PoetContext poetContext) {
        MarkovChain chain = poetContext.getMarkovChain();
        WeightedRandomWordSelector wordSelector = poetContext.getWordSelector();
        WordSuggestionProvider wordSuggestionProvider = poetContext.getSuggestionProvider();

        Window window = new BasicWindow("Mechanical Petőfi");
        window.setHints(List.of(
                Window.Hint.NO_DECORATIONS,
                Window.Hint.FIT_TERMINAL_WINDOW,
                Window.Hint.FULL_SCREEN
        ));

        GridLayout layout = new GridLayout(SUGGESTION_COUNT);
        Panel contentPanel = new Panel(layout);

        var textBox = new TextBox(new TerminalSize(80, 1024));
        textBox.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true, true,
                SUGGESTION_COUNT, 1
        ));
        contentPanel.addComponent(textBox);

        tui.getScreen().setTabBehaviour(TabBehaviour.ALIGN_TO_COLUMN_4);

        window.addWindowListener(new WindowListener() {
            @Override
            public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
                textBox.setPreferredSize(new TerminalSize(newSize.getColumns(), newSize.getRows() - 1));
            }

            @Override
            public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
            }

            @Override
            public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
                if (keyStroke.getKeyType() == KeyType.Tab) {
                    String text = textBox.getText();
                    String[] words = text.split("\\s+");
                    if (text.isEmpty())
                        return;

                    String lastWord = words[words.length - 1];

                    if (text.substring(text.length() - 1).isBlank()) {
                        String lastActualWord = "";
                        for (int i = words.length - 1; i >= 0; --i) {
                            if (!words[i].isBlank()) {
                                lastActualWord = words[i];
                                break;
                            }
                        }

                        String newWord = wordSelector.selectWord(chain, lastActualWord);
                        textBox.setText(textBox.getText() + newWord + " ");

                        TerminalPosition caretPosition = textBox.getCaretPosition();
                        textBox.setCaretPosition(caretPosition.getRow(), caretPosition.getColumn() + newWord.length() + 1);
                    } else {
                        String autocompleted = wordSuggestionProvider.autocomplete(lastWord);
                        text = text.substring(0, text.length() - lastWord.length()) + autocompleted;
                        textBox.setText(text);

                        TerminalPosition caretPosition = textBox.getCaretPosition();
                        textBox.setCaretPosition(caretPosition.getRow(), caretPosition.getColumn() + autocompleted.length() - lastWord.length());
                    }
                }
            }

            @Override
            public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
            }
        });

        var suggestionButtons = new ArrayList<Label>(SUGGESTION_COUNT);
        for (int i = 0; i < SUGGESTION_COUNT; ++i) {
            var suggestion = new Label("");
            suggestion.setLayoutData(GridLayout.createLayoutData(
                    GridLayout.Alignment.CENTER,
                    GridLayout.Alignment.BEGINNING,
                    true, false,
                    1, 1
            ));

            contentPanel.addComponent(suggestion);
            suggestionButtons.add(suggestion);
        }

        window.setComponent(contentPanel);
        tui.addWindow(window);

        textBox.setTextChangeListener((s, b) -> {
            String[] words = s.split("\\s+");
            if (words.length == 0)
                return;

            String lastWord = words[words.length - 1];
            List<String> suggested = wordSuggestionProvider.suggest(lastWord, SUGGESTION_COUNT);

            for (int i = 0; i < suggestionButtons.size() && !suggested.isEmpty(); ++i)
                suggestionButtons.get(i).setText(i < suggested.size() ?
                        suggested.get(i) :
                        "");
        });

        tui.waitForWindowToClose(window);
    }

    public void run() {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        PoetDescriptorProvider poetProvider = new PoetDescriptorProvider();
        PoetContextFactory poetFactory = new PoetContextFactory();

        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            WindowBasedTextGUI tui = new MultiWindowTextGUI(screen);

            PoetDescriptor poet = promptPoet(tui, poetProvider.getPoets());

            Window loadingWindow = showPopup(tui, "Loading poet " + poet.getName() + "...");
            tui.updateScreen();
            PoetContext poetContext = poetFactory.createContext(poet);
            tui.removeWindow(loadingWindow);

            new MessageDialogBuilder()
                    .setText("Poet primed!")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(tui);

            runEditor(tui, poetContext);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
