package com.github.elementbound.mechanical.petofi;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.elementbound.mechanical.petofi.poet.PoetContext;
import com.github.elementbound.mechanical.petofi.poet.PoetContextFactory;
import com.github.elementbound.mechanical.petofi.poet.PoetDescriptor;
import com.github.elementbound.mechanical.petofi.poet.PoetDescriptorProvider;

public class Main {

    public static final String DEFAULT_STARTING_WORD = "zeng";

    public static void main(String[] args) {
        PoetDescriptorProvider poetProvider = new PoetDescriptorProvider();
        PoetContextFactory poetFactory = new PoetContextFactory();
        PoetDescriptor poetDescriptor = poetProvider.getPoets().get(0);
        PoetContext poetContext = poetFactory.createContext(poetDescriptor);

        List<String> knownWords = poetContext.getMarkovChain().getLinks().stream()
                .map(MarkovChain.WeightedLink::getFrom)
                .collect(Collectors.toList());

        String startingWord = knownWords.size() > 0
                ? knownWords.get((new Random()).nextInt(knownWords.size()))
                : DEFAULT_STARTING_WORD;

        System.out.println();
        generatePoem(poetContext.getMarkovChain(), poetContext.getWordSelector(), startingWord);
        System.out.println(poetDescriptor.getName());
    }

    private static void generatePoem(MarkovChain chain, WeightedRandomWordSelector wordSelector, String word) {
        for (int k = 0; k < 6; ++k) {
            for (int j = 0; j < 4; ++j) {
                for (int i = 0; i < 8; ++i) {
                    System.out.print(word + " ");
                    word = wordSelector.selectWord(chain, word);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
