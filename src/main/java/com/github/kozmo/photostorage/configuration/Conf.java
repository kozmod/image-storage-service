package com.github.kozmo.photostorage.configuration;

import com.github.kozmo.photostorage.service.path.ImagesPathLoader;
import com.github.kozmo.photostorage.service.path.PagingImagesPathLoader;
import com.github.kozmo.photostorage.service.path.PathLoader;
import com.github.kozmo.photostorage.service.path.TreeDirPathLoader;
import com.github.kozmo.photostorage.service.resource.FileResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;

@Configuration
public class Conf {

    private final AppProperties properties;

    public Conf(AppProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FileResourceLoader defaultResourceLoader() {
        return new FileResourceLoader(properties.getSearchDir(), new DefaultResourceLoader());
    }

    @Bean
    public Function<Long, ? extends PathLoader<Collection<Path>>> imagePathLoaderFactory() {
        return skip -> new PagingImagesPathLoader(properties.getSearchDir(), skip, properties.getLimit());
    }

    @Bean
    public ImagesPathLoader imagesLoader() {
        return new ImagesPathLoader(properties.getSearchDir());
    }

    @Bean
    public TreeDirPathLoader treeDirRootLoader() {
        return new TreeDirPathLoader(
                Paths.get(properties.getSearchDir()),
                Comparator.comparing(ptu -> ptu.value().getFileName().toString())
        );
    }
}
