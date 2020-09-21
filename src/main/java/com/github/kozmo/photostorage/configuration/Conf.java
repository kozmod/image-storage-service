package com.github.kozmo.photostorage.configuration;

import com.github.kozmo.photostorage.service.path.ImagesPathLoader;
import com.github.kozmo.photostorage.service.path.PagingPathLoader;
import com.github.kozmo.photostorage.service.path.PathLoader;
import com.github.kozmo.photostorage.service.path.TreeDirPathLoader;
import com.github.kozmo.photostorage.service.resource.FileResourceLoader;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import java.nio.file.Paths;
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
    public Function<Long, ? extends PathLoader<PagingPathLoader.Result>> imagePathLoaderFactory(ImagesPathLoader loader) {
        return skip -> new PagingPathLoader(loader, skip, properties.getLimit());
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
