package com.github.elementbound.mechanical.petofi.poet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.testng.annotations.Test;

public class WordReaderTest {
    @Test
    public void readShouldReturnExpected() {
        // Given
        WordReader reader = new WordReader();

        // When
        List<String> actual = reader.read("lipsum.txt");

        // Then
        assertThat(actual, contains(
                "Lorem", "ipsum", "dolor", "sit", "amet,", "consectetur",
                "adipiscing", "elit,", "sed", "do", "eiusmod", "tempor",
                "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua."
        ));
    }
}