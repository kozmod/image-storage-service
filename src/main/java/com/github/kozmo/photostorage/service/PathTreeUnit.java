package com.github.kozmo.photostorage.service;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public final class PathTreeUnit {
    private final Path val;
    private final List<PathTreeUnit> children;

    public PathTreeUnit(Path val) {
        this.val = val;
        this.children = new LinkedList<>();
    }

    public boolean addChild(PathTreeUnit ptu){
        return children.add(ptu);
    }

    public Path getValue() {
        return val;
    }

    public List<PathTreeUnit> getChildren() {
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
