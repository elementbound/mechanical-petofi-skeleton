package com.github.elementbound.mechanical.petofi.poet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.testng.annotations.Test;

import com.github.elementbound.mechanical.petofi.MarkovChain;
import com.github.elementbound.mechanical.petofi.WordSanitizer;

public class MarkovChainPopulatorTest {
    @Test
    public void populateShouldAddLinks() {
        // Given
        WordSanitizer wordSanitizer = mock(WordSanitizer.class);
        when(wordSanitizer.isValid(anyString())).thenReturn(true);
        when(wordSanitizer.isValid("e")).thenReturn(false);
        when(wordSanitizer.sanitize(anyString())).then(invocation -> invocation.getArgument(0));

        MarkovChain chain = new MarkovChain();
        List<String> words = List.of("a", "b", "c", "a", "b", "a", "e", "c");

        MarkovChainPopulator chainPopulator = new MarkovChainPopulator(wordSanitizer);

        // When
        chainPopulator.populate(chain, words);

        // Then
        assertThat(chain.getLinks(), containsInAnyOrder(
                new MarkovChain.WeightedLink("a", "b", 2),
                new MarkovChain.WeightedLink("b", "c", 1),
                new MarkovChain.WeightedLink("c", "a", 1),
                new MarkovChain.WeightedLink("b", "a", 1),
                new MarkovChain.WeightedLink("a", "c", 1)
        ));
    }
}