package com.github.kozmo.photostorage.controller;

import com.github.kozmo.photostorage.service.FileResourceLoader;
import com.github.kozmo.photostorage.service.PathLoader;
import com.github.kozmo.photostorage.service.TreeDirPathLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Controller
public class DirController {

    private final static String DASH = "image_dash";
    private final static String GALLERY = "fragment/image_gallery";

    private final Function<Long, ? extends PathLoader<Collection<Path>>> loaderFactory;
    private final TreeDirPathLoader treeDirRootLoader;
    private final FileResourceLoader resourceLoader;

    public DirController(
            Function<Long, ? extends PathLoader<Collection<Path>>> loaderFactory,
            TreeDirPathLoader treeDirRootLoader,
            FileResourceLoader resourceLoader) {
        this.loaderFactory = loaderFactory;
        this.treeDirRootLoader = treeDirRootLoader;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/")
    public String loadPathsFromRootDir(Map<String, Object> model) throws IOException {
        model.put("dirs", loaderFactory.apply(0L).fromRoot());
        model.put("treeRoot", treeDirRootLoader.fromRoot());
        return DASH;
    }


    @GetMapping(value = "/dir/{path}")
    public String getDirImageBlock(@PathVariable("path") String path, Map<String, Object> model) throws IOException {
        path = path.equals("root") ? "" : path;
        model.put("dirs", loaderFactory.apply(0L).fromSub(Paths.get(path)));
        return GALLERY;
    }

    @ResponseBody
    @RequestMapping(value = "/img")
    public Resource getImage(@RequestParam String path) {
        return resourceLoader.getResource(path);
    }
}
