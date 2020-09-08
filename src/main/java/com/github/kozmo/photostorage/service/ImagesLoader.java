package com.github.kozmo.photostorage.service;

import com.github.kozmo.photostorage.utils.CheckedFunction;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

public class ImagesLoader {

    private final DirContent dirLoader;
    private final DirResource dirResource;

    public ImagesLoader(DirContent dirLoader, DirResource dirResource) {
        this.dirLoader = dirLoader;
        this.dirResource = dirResource;
    }

    public Resource load(String dir) throws IOException {
        return dirResource.get(dir);
    }

    public Collection<Path> inRoot() throws IOException {
        return paths(dirResource.root());
    }

    public Collection<Path> paths(String dir) throws IOException {
        return dirLoader.files(dir, CheckedFunction.predicate(this::isImage));
    }

    private boolean isImage(Path path) throws IOException {
        var mimetype = Files.probeContentType(path);
        return mimetype != null && mimetype.split("/")[0].equals("image");
    }
}
