package com.github.elementbound.mechanical.petofi;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class WeightedRandomWordSelector {
    private final Random random;

    public WeightedRandomWordSelector() {
        this(new Random());
    }

    public WeightedRandomWordSelector(Random random) {
        this.random = random;
    }

    public String selectWord(MarkovChain chain, String word) {
        List<MarkovChain.WeightedLink> links = chain.getLinksFrom(word);
        links.sort(Comparator.comparing(MarkovChain.WeightedLink::getWeight));

        // TASK

        return "";
    }
}
