package com.github.kozmo.photostorage.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public final class DirResource {

    private final String root;
    private final ResourceLoader resourceLoader;

    public DirResource(String root, ResourceLoader resourceLoader) {
        this.root = root;
        this.resourceLoader = resourceLoader;
    }

    public Resource get(String fileName) {
        return resourceLoader.getResource(root + fileName);
    }

    public String root(){
        return root;
    }
}
