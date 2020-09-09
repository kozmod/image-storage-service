package com.github.kozmo.photostorage.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Deprecated
public final class DirContent {

    Collection<Path> all(String dir, Predicate<Path> filter) throws IOException {
        var searchPath = Paths.get(dir);
        return Files.list(searchPath)
                .filter(filter)
                .collect(Collectors.toList());
    }

    Collection<Path> files(String dir, Predicate<Path> filter) throws IOException {
        Predicate<Path> predicate = p -> p.toFile().isFile();
        return all(dir, predicate.and(filter));
    }

    Collection<Path> files(String dir) throws IOException {
        return all(dir, p -> p.toFile().isFile());
    }

    Collection<Path> dirs(String dir) throws IOException {
        return all(dir, p -> p.toFile().isDirectory());
    }
}
