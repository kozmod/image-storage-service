package com.github.kozmo.photostorage.controller;

import com.github.kozmo.photostorage.service.ImagesRootLoader;
import com.github.kozmo.photostorage.service.TreeDirRootLoader;
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
    private final TreeDirRootLoader treeDirRootLoader;

    public DirController(ImagesRootLoader imagesRootLoader, TreeDirRootLoader treeDirRootLoader) {
        this.imagesRootLoader = imagesRootLoader;
        this.treeDirRootLoader = treeDirRootLoader;
    }

    @GetMapping("/")
    public String loadPathsFromRootDir(Map<String, Object> model) throws IOException {
        model.put("dirs", imagesRootLoader.fromRoot());
        model.put("tree", treeDirRootLoader.fromRoot());
        return TEMPLATE;
    }

    @ResponseBody
    @RequestMapping(value = "/img/{path}")
    public Resource getImage(@PathVariable("path") String path) {
        return imagesRootLoader.load(Paths.get(path));
    }
}
