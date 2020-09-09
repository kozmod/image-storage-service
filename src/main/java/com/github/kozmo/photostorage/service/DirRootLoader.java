package com.github.kozmo.photostorage.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public final class DirRootLoader implements RootLoader {

    private final Path rootDir;

    public DirRootLoader(String root) {
        this.rootDir = Paths.get(root);
    }

    @Override
    public Collection<Path> fromRoot() throws IOException {
        return fromSub(Paths.get(""));
    }

    @Override
    public Collection<Path> fromSub(Path path) throws IOException {
        return Files.list(rootDir.resolve(path))
                .map(Path::toFile)
                .filter(File::isDirectory)
                .map(File::toPath)
                .map(rootDir::relativize)
                .collect(Collectors.toList());
    }
}
