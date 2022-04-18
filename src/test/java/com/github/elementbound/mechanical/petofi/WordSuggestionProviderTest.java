package com.github.elementbound.mechanical.petofi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

import java.util.List;

import org.testng.annotations.Test;

public class WordSuggestionProviderTest {
    @Test
    public void autocompleteShouldReturnFragmentOnUnknown() {
        // Given
        MarkovChain chain = new MarkovChain();
        WordSanitizer wordSanitizer = new WordSanitizer();
        WordSuggestionProvider suggestionProvider = new WordSuggestionProvider(chain, wordSanitizer);
        chain.link("foo", "bar");
        chain.link("bar", "foo");

        String fragment = "quix";

        // When
        String actual = suggestionProvider.autocomplete(fragment);

        // Then
        assertThat(actual, is(fragment));
    }

    @Test
    public void autocompleteShouldReturnBestMatch() {
        // Given
        MarkovChain chain = new MarkovChain();
        WordSanitizer wordSanitizer = new WordSanitizer();
        WordSuggestionProvider suggestionProvider = new WordSuggestionProvider(chain, wordSanitizer);
        chain.link("foo", "bar");
        chain.link("foo", "quix");
        chain.link("fae", "bar");
        chain.link("bar", "foo");

        // Adding whitespace will make sure the fragment is sanitized
        String fragment = "  f";
        String expected = "foo";

        // When
        String actual = suggestionProvider.autocomplete(fragment);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void suggestShouldReturnEmptyOnUnknown() {
        // Given
        MarkovChain chain = new MarkovChain();
        WordSanitizer wordSanitizer = new WordSanitizer();
        WordSuggestionProvider suggestionProvider = new WordSuggestionProvider(chain, wordSanitizer);
        chain.link("foo", "bar");
        chain.link("bar", "foo");

        String word = "quix";

        // When
        List<String> actual = suggestionProvider.suggest(word, 5);

        // Then
        assertThat(actual, is(empty()));
    }

    @Test
    public void suggestShouldReturnExpected() {
        // Given
        MarkovChain chain = new MarkovChain();
        WordSanitizer wordSanitizer = new WordSanitizer();
        WordSuggestionProvider suggestionProvider = new WordSuggestionProvider(chain, wordSanitizer);
        chain.link("foo", "bar");
        chain.link("foo", "bar");
        chain.link("foo", "bar");
        chain.link("foo", "quix");
        chain.link("foo", "quix");
        chain.link("foo", "baz");
        chain.link("bar", "quix");

        String word = "foo";

        // When
        List<String> actual = suggestionProvider.suggest(word, 2);

        // Then
        assertThat(actual, contains("bar", "quix"));
    }
}