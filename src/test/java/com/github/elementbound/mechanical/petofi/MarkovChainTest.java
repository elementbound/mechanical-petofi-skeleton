package com.github.elementbound.mechanical.petofi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;

import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MarkovChainTest {
    @Test
    public void getLinksShouldReturnNoLinks() {
        // Given
        MarkovChain chain = new MarkovChain();

        // When
        Set<MarkovChain.WeightedLink> links = chain.getLinks();

        // Then
        assertThat(links, empty());
    }

    @Test(dataProvider = "linksProvider")
    public void getLinksShouldReturnExpectedLinks(String[][] inputLinks, MarkovChain.WeightedLink[] expectedLinks) {
        // Given
        MarkovChain chain = new MarkovChain();

        for (String[] input : inputLinks)
            chain.link(input[0], input[1]);

        // When
        Set<MarkovChain.WeightedLink> actualLinks = chain.getLinks();

        // Then
        assertThat(actualLinks, containsInAnyOrder(expectedLinks));
    }

    @Test
    public void getLinksFromShouldReturnEmptyOnUnknown() {
        // Given
        MarkovChain chain = new MarkovChain();
        chain.link("a", "b");

        // When
        List<MarkovChain.WeightedLink> links = chain.getLinksFrom("b");

        // Then
        assertThat(links, empty());
    }

    @Test
    public void getLinksFromShouldReturnLinks() {
        // Given
        MarkovChain chain = new MarkovChain();
        chain.link("a", "b");
        chain.link("a", "c");
        chain.link("a", "c");
        chain.link("b", "c");

        // When
        List<MarkovChain.WeightedLink> links = chain.getLinksFrom("a");

        // Then
        assertThat(links, containsInAnyOrder(
                new MarkovChain.WeightedLink("a", "b", 1),
                new MarkovChain.WeightedLink("a", "c", 2)
        ));
    }

    @DataProvider
    public Object[][] linksProvider() {
        return new Object[][]{
                {new String[][]{
                        {"a", "b"}
                }, new MarkovChain.WeightedLink[]{
                        new MarkovChain.WeightedLink("a", "b", 1)
                }},

                {new String[][]{
                        {"a", "b"},
                        {"b", "c"},
                        {"c", "a"},
                        {"a", "b"},
                }, new MarkovChain.WeightedLink[]{
                        new MarkovChain.WeightedLink("a", "b", 2),
                        new MarkovChain.WeightedLink("b", "c", 1),
                        new MarkovChain.WeightedLink("c", "a", 1),
                }},
        };
    }
}