package com.github.kozmo.photostorage.service.resource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileResourceLoader implements ResourceLoader {
    final Path rootDir;
    final ResourceLoader resourceLoader;

    public FileResourceLoader(String root, ResourceLoader resourceLoader) {
        this.rootDir = Paths.get(root);
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Resource getResource(String location) {
        var resolvedPath = "file:" + rootDir.resolve(Paths.get(location));
        return resourceLoader.getResource(resolvedPath);
    }

    @Override
    public ClassLoader getClassLoader() {
        return resourceLoader.getClassLoader();
    }
}
