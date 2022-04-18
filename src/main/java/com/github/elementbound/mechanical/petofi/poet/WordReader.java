package com.github.elementbound.mechanical.petofi.poet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordReader {
    public List<String> read(String resourceName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream resource = classLoader.getResourceAsStream(resourceName);

        return read(resource);
    }

    public List<String> read(InputStream is) {
        List<String> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(is)) {
            // TASK
        }

        return result;
    }
}
