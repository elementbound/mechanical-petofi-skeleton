package com.github.elementbound.mechanical.petofi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WordSuggestionProvider {
    private final MarkovChain chain;
    private final WordSanitizer wordSanitizer;

    public WordSuggestionProvider(MarkovChain chain, WordSanitizer wordSanitizer) {
        this.chain = chain;
        this.wordSanitizer = wordSanitizer;
    }

    public String autocomplete(String wordFragment) {
        Set<MarkovChain.WeightedLink> links = chain.getLinks();
        wordFragment = wordFragment.trim();
        String result = wordFragment;

        // TASK

        return result;
    }

    public List<String> suggest(String word, int count) {
        List<MarkovChain.WeightedLink> links = chain.getLinksFrom(word);
        List<String> suggestions = new ArrayList<>(count);

        // TASK

        return suggestions;
    }
}
