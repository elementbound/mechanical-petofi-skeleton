package com.github.elementbound.mechanical.petofi.poet;

import java.util.List;

import com.github.elementbound.mechanical.petofi.MarkovChain;
import com.github.elementbound.mechanical.petofi.WordSanitizer;

public class MarkovChainPopulator {
    private final WordSanitizer wordSanitizer;

    public MarkovChainPopulator(WordSanitizer wordSanitizer) {
        this.wordSanitizer = wordSanitizer;
    }

    public void populate(MarkovChain chain, List<String> words) {
        // TASK
    }
}
