package com.github.elementbound.mechanical.petofi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WeightedRandomWordSelectorTest {
    @Test
    public void selectWordShouldReturnEmptyOnUnknown() {
        // Given
        WeightedRandomWordSelector wordSelector = new WeightedRandomWordSelector();
        MarkovChain chain = new MarkovChain();
        chain.link("foo", "bar");

        // When
        String actual = wordSelector.selectWord(chain, "quix");

        // Then
        assertThat(actual, is(""));
    }

    @Test(dataProvider = "selectWordProvider")
    public void selectWordShouldReturn(int randomValue, String expected) {
        // Given
        Random random = mock(Random.class);
        WeightedRandomWordSelector wordSelector = new WeightedRandomWordSelector(random);
        MarkovChain chain = new MarkovChain();
        chain.link("foo", "bar");
        chain.link("foo", "bar");
        chain.link("foo", "bar");
        chain.link("foo", "quix");
        chain.link("foo", "quix");
        chain.link("foo", "baz");

        when(random.nextInt(6)).thenReturn(randomValue);
        when(random.nextInt()).thenReturn(randomValue);
        when(random.nextLong()).thenReturn((long)randomValue);

        // When
        String actual = wordSelector.selectWord(chain, "foo");

        // Then
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] selectWordProvider() {
        return new Object[][]{
                {0, "baz"},
                {1, "quix"},
                {2, "quix"},
                {3, "bar"},
                {4, "bar"},
                {5, "bar"}
        };
    }
}