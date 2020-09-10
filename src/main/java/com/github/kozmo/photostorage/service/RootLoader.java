package com.github.kozmo.photostorage.service;

import java.io.IOException;
import java.nio.file.Path;

public interface RootLoader<T> {

    T fromRoot() throws IOException;

    T fromSub(Path dir) throws IOException;
}
