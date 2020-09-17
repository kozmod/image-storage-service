package com.github.kozmo.photostorage.controller;

import com.github.kozmo.photostorage.configuration.AppProperties;
import com.github.kozmo.photostorage.service.path.PagingPathLoader;
import com.github.kozmo.photostorage.service.resource.FileResourceLoader;
import com.github.kozmo.photostorage.service.path.PathLoader;
import com.github.kozmo.photostorage.service.path.TreeDirPathLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;

@Controller
public class DirController {

    /* thymeleaf vars */
    private static final String PREVIOUS_BUTTON_DISABLED = "previousButtonDisabled";
    private static final String NEXT_BUTTON_DISABLED = "nextButtonDisabled";
    private static final String SKIP_PREVIOUS = "skipPrevious";
    private static final String SKIP_NEXT = "skipNext";
    private static final String IMG_GALLERY_PATH = "imgGalleryPath";
    private static final String DIRS = "dirs";
    private static final String TREE_ROOT = "treeRoot";
    private static final String ROOT = "root";

    /* templates and fragments */
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
        model.put(PREVIOUS_BUTTON_DISABLED, true);
        model.put(NEXT_BUTTON_DISABLED, !(result.total() > properties.getLimit()));
        model.put(SKIP_PREVIOUS, 0);
        model.put(SKIP_NEXT, properties.getLimit());
        model.put(IMG_GALLERY_PATH, ROOT);
        model.put(DIRS, result.paths());
        model.put(TREE_ROOT, treeDirRootLoader.fromRoot());
        return DASH;
    }

    @GetMapping(value = "/dir/{path}")
    public String getDirImageBlock(@PathVariable("path") String path, @RequestParam Long skip, Map<String, Object> model) throws IOException {
        var nextPrevious = NextPreviousMove.fromSkipAndLimit(skip, properties.getLimit());
        model.put(SKIP_PREVIOUS, nextPrevious.left());
        model.put(SKIP_NEXT, nextPrevious.right());
        model.put(IMG_GALLERY_PATH, path);

        var result = loaderFactory.apply(nextPrevious.skip()).fromSub(fromRoot(path));
        model.put(PREVIOUS_BUTTON_DISABLED, nextPrevious.left() <= 0);
        model.put(NEXT_BUTTON_DISABLED, nextPrevious.right() >= result.total());

        model.put(DIRS, result.paths());
        return GALLERY;
    }

    private static Path fromRoot(String path) {
        return Paths.get(path.equals(ROOT) ? "" : path);
    }

    @ResponseBody
    @RequestMapping(value = "/img")
    public Resource getImage(@RequestParam String path) {
        return resourceLoader.getResource(path);
    }
}
