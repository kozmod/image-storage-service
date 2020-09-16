package com.github.kozmo.photostorage.service.path;

import com.github.kozmo.photostorage.utils.CheckedFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public final class PagingImagesPathLoader extends ImagesPathLoader {

    private final long skip;
    private final long limit;

    public PagingImagesPathLoader(String root, long skip, long limit) {
        super(root);
        this.skip = skip;
        this.limit = limit;
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
                .filter(CheckedFunction.predicate(super::isImage))
                .skip(skip)
                .limit(limit)
                .map(rootDir::relativize)
                .collect(Collectors.toList());
    }
}
