package com.github.kozmo.photostorage.service;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

public interface PathResourceLoader {

    Resource load(Path path);
}
