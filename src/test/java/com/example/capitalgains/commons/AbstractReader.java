package com.example.capitalgains.commons;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public abstract class AbstractReader {

    private static final String RESOURCE_PATH = "./src/test/resources/txt/";

    public static String readExamplesTxt(String fileName) throws IOException {
        try {
            return String.join(
                    " ",
                    Files.readAllLines(Paths.get(RESOURCE_PATH + fileName + ".txt"), StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            log.error("failed to read file {} to String | {}",
                    RESOURCE_PATH + fileName + ".txt", e.getMessage(), e.getCause());
            throw e;
        }
    }

    public static BufferedReader bufferedReadExamplesTxt(String fileName) throws IOException {
        try {
            return Files.newBufferedReader(Paths.get(RESOURCE_PATH + fileName + ".txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("failed to read file {} to BufferedReader | {}",
                    RESOURCE_PATH + fileName + ".txt", e.getMessage(), e.getCause());
            throw e;
        }
    }
}
