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

    private final static String DASH = "image_dash";
    private final static String GALLERY = "fragment/image_gallery";

    private final ImagesRootLoader imagesRootLoader;
    private final TreeDirRootLoader treeDirRootLoader;

    public DirController(ImagesRootLoader imagesRootLoader, TreeDirRootLoader treeDirRootLoader) {
        this.imagesRootLoader = imagesRootLoader;
        this.treeDirRootLoader = treeDirRootLoader;
    }

    @GetMapping("/")
    public String loadPathsFromRootDir(Map<String, Object> model) throws IOException {
        model.put("dirs", imagesRootLoader.fromRoot());
        model.put("treeRoot", treeDirRootLoader.fromRoot());
        return DASH;
    }


    @GetMapping(value = "/dir/{path}")
    public String getDirImageBlock(@PathVariable("path") String path,Map<String, Object> model) throws IOException {
        path = path.equals("root") ? "" : path;
        model.put("dirs", imagesRootLoader.fromSub(Paths.get(path)));
        return GALLERY;
    }

    @ResponseBody
    @RequestMapping(value = "/img")
    public Resource getImage(@RequestParam String path) {
        return imagesRootLoader.load(Paths.get(path));
    }
}
