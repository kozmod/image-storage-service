package com.github.kozmo.photostorage.service.path;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public final class ImagesPathLoader implements PathLoader<Collection<Path>> {

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
        final Collection<Path> paths = new LinkedList<>();
        var v = rootDir.resolve(path).toFile();
        for (File file : Objects.requireNonNull(v.listFiles())) {
            if (file.isFile() && isImage(file.toPath())) {
                paths.add(rootDir.relativize(file.toPath()));
            }
        }
        return paths;
    }

    private boolean isImage(Path path) throws IOException {
        var mimetype = Files.probeContentType(path);
        return mimetype != null && mimetype.split("/")[0].equals("image");
    }
}
