package com.github.kozmo.photostorage;

import com.github.kozmo.photostorage.service.DirContent;
import com.github.kozmo.photostorage.service.DirResource;
import com.github.kozmo.photostorage.service.ImagesLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

@Configuration
public class Conf {

    @Value("${app.file.path}")
    private String searchDir;

    @Bean
    public DirContent dirContent() {
        return new DirContent();
    }

    @Bean
    public DirResource dirResourceLoader() {
        return new DirResource(searchDir, new DefaultResourceLoader());
    }

    @Bean
    public ImagesLoader imagesLoader(DirContent dc, DirResource drl) {
        return new ImagesLoader(dc, drl);
    }
}
