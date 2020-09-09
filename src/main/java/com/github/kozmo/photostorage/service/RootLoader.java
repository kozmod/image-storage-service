package com.github.kozmo.photostorage.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface RootLoader {

    Collection<Path> fromRoot() throws IOException;

    Collection<Path> fromSub(Path dir) throws IOException;
}
