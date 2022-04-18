package com.github.elementbound.mechanical.petofi.poet;

import java.util.List;

import com.github.elementbound.mechanical.petofi.MarkovChain;
import com.github.elementbound.mechanical.petofi.WeightedRandomWordSelector;
import com.github.elementbound.mechanical.petofi.WordSanitizer;
import com.github.elementbound.mechanical.petofi.WordSuggestionProvider;

public class PoetContextFactory {
    public PoetContext createContext(PoetDescriptor descriptor) {
        MarkovChain chain = new MarkovChain();
        WordSanitizer wordSanitizer = new WordSanitizer();
        WeightedRandomWordSelector wordSelector = new WeightedRandomWordSelector();
        WordSuggestionProvider suggestionProvider = new WordSuggestionProvider(chain, wordSanitizer);

        WordReader wordReader = new WordReader();
        MarkovChainPopulator chainPopulator = new MarkovChainPopulator(wordSanitizer);

        List<String> words = wordReader.read(descriptor.getFilename());
        chainPopulator.populate(chain, words);

        return PoetContext.builder()
                .withMarkovChain(chain)
                .withSuggestionProvider(suggestionProvider)
                .withWordSanitizer(wordSanitizer)
                .withWordSelector(wordSelector)
                .build();
    }
}
