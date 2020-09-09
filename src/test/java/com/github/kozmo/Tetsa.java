package com.github.kozmo;

import com.github.kozmo.photostorage.utils.CheckedFunction;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Tetsa {

    @Test
    void name() throws IOException {
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
              .peek(x -> System.out.println("x " +x))
//                .map(Paths::get)
                .collect(Collectors.toList());
    }
}
