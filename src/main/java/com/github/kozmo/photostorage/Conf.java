package com.github.kozmo.photostorage;

import com.github.kozmo.photostorage.service.ImagesRootLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

@Configuration
public class Conf {

    @Value("${app.file.path}")
    private String searchDir;

    @Bean
    public ImagesRootLoader imagesLoader() {
        return new ImagesRootLoader(searchDir, new DefaultResourceLoader());
    }
}
