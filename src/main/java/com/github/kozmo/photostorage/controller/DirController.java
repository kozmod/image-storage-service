package com.github.kozmo.photostorage.controller;

import com.github.kozmo.photostorage.service.ImagesRootLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Controller
public class DirController {

    private final static String TEMPLATE = "test2";

    private final ImagesRootLoader imagesRootLoader;

    public DirController(ImagesRootLoader imagesRootLoader) {
        this.imagesRootLoader = imagesRootLoader;
    }

    @GetMapping("/")
    public String loadPathsFromRootDir(Map<String, Object> model) throws IOException {
        model.put("dirs", imagesRootLoader.fromRoot());
        return TEMPLATE;
    }

    @ResponseBody
    @RequestMapping(value = "/img/{path}")
    public Resource getImage(@PathVariable("path") String path) {
        return imagesRootLoader.load(Paths.get(path));
    }
}
