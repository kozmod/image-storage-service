package com.github.kozmo.photostorage.service.path;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public final class PagingPathLoader implements PathLoader<PagingPathLoader.Result> {

    private final PathLoader<Collection<Path>> loader;
    private final long skip;
    private final long limit;

    public PagingPathLoader(PathLoader<Collection<Path>> loader, long skip, long limit) {
        this.loader = loader;
        this.skip = skip;
        this.limit = limit;
    }

    @Override
    public Result fromRoot() throws IOException {
        return fromSub(Paths.get(""));
    }

    @Override
    public Result fromSub(Path path) throws IOException {
        var loaded = loader.fromSub(path);
        var paths = loaded.stream()
                .skip(skip)
                .limit(limit)
                .collect(Collectors.toList());
        return new Result(paths, loaded.size());
    }

    public static class Result{
        private final Collection<Path> paths;
        private final int total;

        public Result(Collection<Path> paths, int all) {
            this.paths = paths;
            this.total = all;
        }

        public Collection<Path> paths() {
            return paths;
        }

        public int total() {
            return total;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "paths=" + paths +
                    ", total=" + total +
                    '}';
        }
    }
}
