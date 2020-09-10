package com.github.kozmo.photostorage.service;

import com.github.kozmo.photostorage.utils.CheckedFunction;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public final class ImagesRootLoader implements RootLoader<Collection<Path>> {

    private final Path rootDir;
    private final ResourceLoader resourceLoader;

    public ImagesRootLoader(String root, ResourceLoader resourceLoader) {
        this.rootDir = Paths.get(root);
        this.resourceLoader = resourceLoader;
    }

    public Resource load(Path path) {
        var resolvedPath = "file:" + rootDir.resolve(path);
        return resourceLoader.getResource(resolvedPath);
    }

    @Override
    public Collection<Path> fromRoot() throws IOException {
        return fromSub(Paths.get(""));
    }

    @Override
    public Collection<Path> fromSub(Path path) throws IOException {
        return Files.list(rootDir.resolve(path))
                .map(Path::toFile)
                .filter(File::isFile)
                .map(File::toPath)
                .filter(CheckedFunction.predicate(this::isImage))
                .map(rootDir::relativize)
                .collect(Collectors.toList());
    }

    private boolean isImage(Path path) throws IOException {
        var mimetype = Files.probeContentType(path);
        return mimetype != null && mimetype.split("/")[0].equals("image");
    }
}
