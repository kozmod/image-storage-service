package com.github.kozmo.photostorage.service.path;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;

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
        final var loaded = loader.fromSub(path);
        final var paths = new LinkedList<Path>();
        var i = 0;
        var tmpLimit = limit;
        for (Path p : loaded) {
            i++;
            if (i <= skip) {
                tmpLimit++;
                continue;
            }
            if (i > tmpLimit) {
                break;
            }
            paths.add(p);
        }
        return new Result(paths, loaded.size());
    }

    public static class Result {
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
