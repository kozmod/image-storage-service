package com.github.kozmo.photostorage.service;

import java.nio.file.Path;
import java.util.*;

public final class PathTreeUnit {
    private final Path val;
    private final Collection<PathTreeUnit> children;

    public PathTreeUnit(Path val, Collection<PathTreeUnit> children) {
        this.val = val;
        this.children = children;
    }

    public boolean addChild(PathTreeUnit ptu) {
        return children.add(ptu);
    }

    public Path getValue() {
        return val;
    }

    public Collection<PathTreeUnit> getChildren() {
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
