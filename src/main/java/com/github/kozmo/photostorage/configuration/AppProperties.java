package com.github.kozmo.photostorage.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:properties.yml", factory = YamlPropertySourceFactory.class)
public class AppProperties {

    @Value("${file.search-dir}")
    private String searchDir;

    @Value("${file.limit}")
    private long limit;

    public String getSearchDir() {
        return searchDir;
    }

    public long getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "AppProperties{" +
                "searchDir='" + searchDir + '\'' +
                ", limit=" + limit +
                '}';
    }
}
