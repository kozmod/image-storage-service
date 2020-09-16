package com.github.kozmo.photostorage;

import com.github.kozmo.photostorage.service.ImagesPathLoader;
import com.github.kozmo.photostorage.service.TreeDirPathLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import java.nio.file.Paths;
import java.util.Comparator;

@Configuration
public class Conf {

    @Value("${app.file.path}")
    private String searchDir;

    @Bean
    public ImagesPathLoader imagesLoader() {
        return new ImagesPathLoader(searchDir, new DefaultResourceLoader());
    }

    @Bean
    public TreeDirPathLoader treeDirRootLoader() {
        return new TreeDirPathLoader(
                Paths.get(searchDir),
                Comparator.comparing(ptu -> ptu.value().getFileName().toString())
        );
    }
}
