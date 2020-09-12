package com.github.kozmo;

import com.github.kozmo.photostorage.service.PathTreeUnit;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Tetsa {

    @Test
    void pathTest() throws IOException {
        var root = Paths.get("/Users/a18388871/IdeaProjects/image-storage-service");
        System.out.println(root.resolve(Paths.get("")));
        Files.list(root.resolve(Paths.get("")))
                .map(Path::toFile)
                .filter(File::isFile)
                .map(File::toPath)
                .peek(x -> System.out.println(x))
                .map(x -> root.relativize(x))
                .peek(x -> System.out.println(x))
                .map(x -> root.resolve(x))
                .peek(x -> System.out.println("x " + x))
//                .map(Paths::get)
                .collect(Collectors.toList());
    }

    @Test
    void walkTest() throws IOException {
        var root = Paths.get("/Users/a18388871/IdeaProjects/image-storage-service");
        Files.walk(root).sorted().forEach(System.out::println);
    }

    @Test
    void walkFileTest() throws IOException {
        class DirVisitor<T extends Path> extends SimpleFileVisitor<T> {
            public PathTreeUnit current;
            public final Deque<PathTreeUnit> parents = new ArrayDeque<>();

            @Override
            public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) {
                if (current == null) {
                    current = new PathTreeUnit(dir, new LinkedList<>());
                } else {
                    var tmpCurrent = new PathTreeUnit(dir, new LinkedList<>());
                    current.add(tmpCurrent);
                    parents.add(current);
                    current = tmpCurrent;
                }
                System.out.format("%s in \n", dir);
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException {
                System.out.format("%s out \n", dir);
                if (!parents.isEmpty()) {
                    current = parents.pollLast();
                }
                return CONTINUE;
            }
        }
        DirVisitor<Path> visitor = new DirVisitor<>();

        var root = Paths.get("/Users/a18388871/IdeaProjects/image-storage-service");
        Files.walkFileTree(root, visitor);
        var c = visitor.current;
        System.out.println(c);
    }
}
