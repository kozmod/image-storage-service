package com.github.kozmo.photostorage.service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;

public final class TreeDirRootLoader implements RootLoader<PathTreeUnit> {

    private final Path rootDir;
    private final Comparator<PathTreeUnit> unitComparator;

    public TreeDirRootLoader(Path rootDir, Comparator<PathTreeUnit> unitComparator) {
        this.rootDir = rootDir;
        this.unitComparator = unitComparator;
    }

    @Override
    public PathTreeUnit fromRoot() throws IOException {
        return fromSub(Paths.get(""));
    }

    @Override
    public PathTreeUnit fromSub(Path path) throws IOException {
        DirVisitor<Path> visitor = new DirVisitor<>();
        Files.walkFileTree(rootDir.resolve(path), visitor);
        return visitor.current;
    }

    private class DirVisitor<T extends Path> extends SimpleFileVisitor<T> {
        public PathTreeUnit current;
        public final Deque<PathTreeUnit> parents = new ArrayDeque<>();

        @Override
        public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) {
            if (current == null) {
                current = sortedByNamePathUnit(dir);
            } else {
                var tmpCurrent = sortedByNamePathUnit(dir);
                current.add(tmpCurrent);
                parents.add(current);
                current = tmpCurrent;
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException {
            if (!parents.isEmpty()) {
                current = parents.pollLast();
            }
            return CONTINUE;
        }

        private PathTreeUnit sortedByNamePathUnit(T dir) {
            return new PathTreeUnit(rootDir.relativize(dir), new TreeSet<>(unitComparator));
        }
    }
}
