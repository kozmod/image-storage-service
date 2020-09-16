package com.github.kozmo.photostorage.service.path;

import java.nio.file.Path;
import java.util.*;

public final class PathTreeUnit {
    private final Path val;
    private final Collection<PathTreeUnit> children;

    public PathTreeUnit(Path val, Collection<PathTreeUnit> children) {
        this.val = val;
        this.children = children;
    }

    public boolean add(PathTreeUnit ptu) {
        return children.add(ptu);
    }

    public Path value() {
        return val;
    }

    public Collection<PathTreeUnit> children() {
        return children;
    }

    @Override
    public String toString() {
        return "PathTreeUnit{" +
                "val=" + val +
                ", children=" + children +
                '}';
    }
}
