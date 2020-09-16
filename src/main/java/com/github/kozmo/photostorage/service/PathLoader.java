package com.github.kozmo.photostorage.service;

import java.io.IOException;
import java.nio.file.Path;

public interface PathLoader<T> {

    T fromRoot() throws IOException;

    T fromSub(Path dir) throws IOException;
}
