package com.github.elementbound.mechanical.petofi.poet;

import java.util.Objects;
import java.util.StringJoiner;

import com.github.elementbound.mechanical.petofi.MarkovChain;
import com.github.elementbound.mechanical.petofi.WeightedRandomWordSelector;
import com.github.elementbound.mechanical.petofi.WordSanitizer;
import com.github.elementbound.mechanical.petofi.WordSuggestionProvider;

public class PoetContext {
    private final MarkovChain markovChain;
    private final WordSanitizer wordSanitizer;
    private final WeightedRandomWordSelector wordSelector;
    private final WordSuggestionProvider suggestionProvider;

    private PoetContext(MarkovChain markovChain, WordSanitizer wordSanitizer, WeightedRandomWordSelector wordSelector, WordSuggestionProvider suggestionProvider) {
        this.markovChain = markovChain;
        this.wordSanitizer = wordSanitizer;
        this.wordSelector = wordSelector;
        this.suggestionProvider = suggestionProvider;
    }

    public static Builder builder() {
        return new Builder();
    }

    public MarkovChain getMarkovChain() {
        return markovChain;
    }

    public WordSanitizer getWordSanitizer() {
        return wordSanitizer;
    }

    public WeightedRandomWordSelector getWordSelector() {
        return wordSelector;
    }

    public WordSuggestionProvider getSuggestionProvider() {
        return suggestionProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoetContext that = (PoetContext) o;
        return Objects.equals(markovChain, that.markovChain) && Objects.equals(wordSanitizer, that.wordSanitizer) && Objects.equals(wordSelector, that.wordSelector) && Objects.equals(suggestionProvider, that.suggestionProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markovChain, wordSanitizer, wordSelector, suggestionProvider);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PoetContext.class.getSimpleName() + "[", "]")
                .add("markovChain=" + markovChain)
                .add("wordSanitizer=" + wordSanitizer)
                .add("wordSelector=" + wordSelector)
                .add("suggestionProvider=" + suggestionProvider)
                .toString();
    }

    public static class Builder {
        private MarkovChain markovChain;
        private WordSanitizer wordSanitizer;
        private WeightedRandomWordSelector wordSelector;
        private WordSuggestionProvider suggestionProvider;

        public Builder withMarkovChain(MarkovChain markovChain) {
            this.markovChain = markovChain;
            return this;
        }

        public Builder withWordSanitizer(WordSanitizer wordSanitizer) {
            this.wordSanitizer = wordSanitizer;
            return this;
        }

        public Builder withWordSelector(WeightedRandomWordSelector wordSelector) {
            this.wordSelector = wordSelector;
            return this;
        }

        public Builder withSuggestionProvider(WordSuggestionProvider suggestionProvider) {
            this.suggestionProvider = suggestionProvider;
            return this;
        }

        public PoetContext build() {
            return new PoetContext(markovChain, wordSanitizer, wordSelector, suggestionProvider);
        }
    }
}
