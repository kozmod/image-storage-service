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

public final class ImagesDirLoader {

    private final String rootDir;
    private final ResourceLoader resourceLoader;

    public ImagesDirLoader(String root, ResourceLoader resourceLoader) {
        this.rootDir = root;
        this.resourceLoader = resourceLoader;
    }

    public Resource load(String dir) throws IOException {
        var path = "file:" + rootDir + "/" + dir;
        return resourceLoader.getResource(path);
    }

    public Collection<Path> inRootDir() throws IOException {
        return paths(rootDir);
    }

    public Collection<Path> paths(String dir) throws IOException {
        return Files.list(Paths.get(dir))
                .map(Path::toFile)
                .filter(File::isFile)
                .filter(CheckedFunction.predicate(this::isImage))
                .map(File::getAbsolutePath)
                .map(this::replaceRoot)
                .map(Paths::get)
                .collect(Collectors.toList());
    }

    private boolean isImage(File file) throws IOException {
        var mimetype = Files.probeContentType(file.toPath());
        return mimetype != null && mimetype.split("/")[0].equals("image");
    }

    private String replaceRoot(String val) {
        return val.replaceFirst(rootDir, "/");
    }
}