package com.github.kozmo.photostorage.controller;

import com.github.kozmo.photostorage.service.ImagesDirLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class DirController {

    private final static String TEMPLATE = "test";

    private final ImagesDirLoader imagesDirLoader;

    public DirController(ImagesDirLoader imagesDirLoader) {
        this.imagesDirLoader = imagesDirLoader;
    }

    @GetMapping("/")
    public String loadPathsFromRootDir(Map<String, Object> model) throws IOException {
        model.put("dirs", imagesDirLoader.inRootDir());
        return TEMPLATE;
    }

    @ResponseBody
    @RequestMapping(value = "/img/{path}")
    public Resource getImage(@PathVariable("path") String path) throws IOException {
        return imagesDirLoader.load(path);
    }
}
