package com.github.elementbound.mechanical.petofi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WordSanitizerTest {
    @Test(dataProvider = "isValidDataProvider")
    public void isValidShouldReturnExpected(String word, boolean expected) {
        // Given
        WordSanitizer wordSanitizer = new WordSanitizer();

        // When
        boolean actual = wordSanitizer.isValid(word);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(dataProvider = "sanitizeDataProvider")
    public void sanitizeShouldReturnExpected(String word, String expected) {
        // Given
        WordSanitizer wordSanitizer = new WordSanitizer();

        // When
        String actual = wordSanitizer.sanitize(word);

        // Then
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] isValidDataProvider() {
        return new Object[][]{
                {"", false},
                {"  ", false},
                {"#!", false},
                {"foo", true},
                {"Fő", true},
                {"  foo ", true}
        };
    }

    @DataProvider
    public Object[][] sanitizeDataProvider() {
        return new Object[][]{
                {"foo", "foo"},
                {"Foo", "foo"},
                {"  foo ", "foo"},
                {" ŐfelsÉge", "őfelsége"}
        };
    }
}