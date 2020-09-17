package com.github.kozmo.photostorage.controller;

import com.github.kozmo.photostorage.configuration.AppProperties;
import com.github.kozmo.photostorage.service.path.PagingPathLoader;
import com.github.kozmo.photostorage.service.resource.FileResourceLoader;
import com.github.kozmo.photostorage.service.path.PathLoader;
import com.github.kozmo.photostorage.service.path.TreeDirPathLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;

@Controller
public class DirController {

    private final static String DASH = "image_dash";
    private final static String GALLERY = "fragment/image_gallery";

    private final Function<Long, ? extends PathLoader<PagingPathLoader.Result>> loaderFactory;
    private final AppProperties properties;
    private final TreeDirPathLoader treeDirRootLoader;
    private final FileResourceLoader resourceLoader;

    public DirController(
            Function<Long, ? extends PathLoader<PagingPathLoader.Result>> loaderFactory,
            AppProperties properties,
            TreeDirPathLoader treeDirRootLoader,
            FileResourceLoader resourceLoader) {
        this.loaderFactory = loaderFactory;
        this.properties = properties;
        this.treeDirRootLoader = treeDirRootLoader;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/")
    public String loadPathsFromRootDir(Map<String, Object> model) throws IOException {
        var result = loaderFactory.apply(0L).fromRoot();
        model.put("leftButtonDisabled", true);
        model.put("rightButtonDisabled", !(result.getAllSize() > properties.getLimit()));
        model.put("limit", properties.getLimit());
        model.put("dirs", result.getPaths());
        model.put("treeRoot", treeDirRootLoader.fromRoot());
        return DASH;
    }


    @GetMapping(value = "/dir/{path}")
    public String getDirImageBlock(@PathVariable("path") String path, Map<String, Object> model) throws IOException {
        path = path.equals("root") ? "" : path;
        var result = loaderFactory.apply(0L).fromSub(Paths.get(path));
        model.put("leftButton", true);
        model.put("rightButton", !(result.getAllSize() > properties.getLimit()));
        model.put("limit", properties.getLimit());
        model.put("dirs", result.getPaths());
        return GALLERY;
    }

//    @GetMapping(value = "/dir/paging/{path}") //todo add ajax function
//    public String getDirImageBlock(@PathVariable("path") String path, @RequestParam Long limit, Map<String, Object> model) throws IOException {
//        var skip = properties.getLimit() + limit;
//        skip = skip < 0 ? 0 : skip;
//
//        path = path.equals("root") ? "" : path;
//        var dirs = loaderFactory.apply(skip).fromSub(Paths.get(path));
//
//        model.put("leftButton", true); //todo think
//        model.put("rightButton", dirs.size() >= properties.getLimit());
//        model.put("dirs", dirs);
//        return GALLERY;
//    }

    @ResponseBody
    @RequestMapping(value = "/img")
    public Resource getImage(@RequestParam String path) {
        return resourceLoader.getResource(path);
    }
}
