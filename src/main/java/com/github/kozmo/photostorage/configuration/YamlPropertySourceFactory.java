package com.github.kozmo.photostorage.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;

public final class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
        var factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        var properties = Objects.requireNonNull(factory.getObject());

        return new PropertiesPropertySource(
                Objects.requireNonNull(encodedResource.getResource().getFilename()),
                properties);
    }
}
