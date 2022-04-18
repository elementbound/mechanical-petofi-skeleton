package com.github.elementbound.mechanical.petofi.poet;

import java.util.Objects;
import java.util.StringJoiner;

public class PoetDescriptor {
    private final String name;
    private final String filename;

    public PoetDescriptor(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoetDescriptor that = (PoetDescriptor) o;
        return Objects.equals(name, that.name) && Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, filename);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PoetDescriptor.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("filename='" + filename + "'")
                .toString();
    }
}
