package com.github.kozmo.photostorage.service.path;

import com.github.kozmo.photostorage.utils.CheckedFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public class ImagesPathLoader implements PathLoader<Collection<Path>> {

    final Path rootDir;

    public ImagesPathLoader(String rootDir) {
        this.rootDir = Paths.get(rootDir);
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

    boolean isImage(Path path) throws IOException {
        var mimetype = Files.probeContentType(path);
        return mimetype != null && mimetype.split("/")[0].equals("image");
    }
}
