package com.github.elementbound.mechanical.petofi;

import java.util.*;

public class MarkovChain {
    private final Map<Link, Integer> linkOccurrences = new HashMap<>();

    public void link(String from, String to) {
        // TASK
    }

    public List<WeightedLink> getLinksFrom(String word) {
        List<WeightedLink> result = new ArrayList<>();

        // TASK

        return result;
    }

    public Set<WeightedLink> getLinks() {
        Set<WeightedLink> result = new HashSet<>();

        // TASK

        return result;
    }

    public static class Link {
        private final String from;
        private final String to;

        public Link(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link link = (Link) o;
            return from.equals(link.from) && to.equals(link.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Link.class.getSimpleName() + "[", "]")
                    .add("from='" + from + "'")
                    .add("to='" + to + "'")
                    .toString();
        }
    }

    public static class WeightedLink {
        private final String from;
        private final String to;
        private final int weight;

        public WeightedLink(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeightedLink that = (WeightedLink) o;
            return weight == that.weight && Objects.equals(from, that.from) && Objects.equals(to, that.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, weight);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", WeightedLink.class.getSimpleName() + "[", "]")
                    .add("from='" + from + "'")
                    .add("to='" + to + "'")
                    .add("weight=" + weight)
                    .toString();
        }
    }
}
