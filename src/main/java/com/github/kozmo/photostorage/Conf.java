package com.github.kozmo.photostorage;

import com.github.kozmo.photostorage.service.path.ImagesPathLoader;
import com.github.kozmo.photostorage.service.path.PagingImagesPathLoader;
import com.github.kozmo.photostorage.service.path.PathLoader;
import com.github.kozmo.photostorage.service.path.TreeDirPathLoader;
import com.github.kozmo.photostorage.service.resource.FileResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;

@Configuration
public class Conf {

    @Value("${app.file.path}")
    private String searchDir;

    @Value("${app.file.paging}")
    private long limit;

    @Bean
    public FileResourceLoader defaultResourceLoader() {
        return new FileResourceLoader(searchDir, new DefaultResourceLoader());
    }

    @Bean
    public Function<Long, ? extends PathLoader<Collection<Path>>> imagePathLoaderFactory() {
        return skip -> new PagingImagesPathLoader(searchDir, skip, limit);
    }

    @Bean
    public ImagesPathLoader imagesLoader() {
        return new ImagesPathLoader(searchDir);
    }

    @Bean
    public TreeDirPathLoader treeDirRootLoader() {
        return new TreeDirPathLoader(
                Paths.get(searchDir),
                Comparator.comparing(ptu -> ptu.value().getFileName().toString())
        );
    }
}
