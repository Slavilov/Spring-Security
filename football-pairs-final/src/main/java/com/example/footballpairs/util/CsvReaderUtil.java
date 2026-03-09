package com.example.footballpairs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CsvReaderUtil {

    private CsvReaderUtil() {
    }

    public static List<String[]> readRows(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    line = removeBom(line);
                    continue;
                }

                rows.add(parseLine(line));
            }
        }

        return rows;
    }

    private static String removeBom(String value) {
        if (value != null && !value.isEmpty() && value.charAt(0) == '\uFEFF') {
            return value.substring(1);
        }
        return value;
    }

    private static String[] parseLine(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);

            if (symbol == '"') {
                insideQuotes = !insideQuotes;
            } else if (symbol == ',' && !insideQuotes) {
                columns.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(symbol);
            }
        }

        columns.add(current.toString().trim());
        return columns.toArray(new String[0]);
    }
}
