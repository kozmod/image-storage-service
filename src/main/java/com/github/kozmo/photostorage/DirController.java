package com.github.kozmo.photostorage;


import com.github.kozmo.photostorage.service.DirContent;
import com.github.kozmo.photostorage.service.DirResource;
import com.github.kozmo.photostorage.service.ImagesLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class DirController {

    private final static String TEMPLATE = "test";

    private final ImagesLoader imagesLoader;

    public DirController(ImagesLoader imagesLoader) {
        this.imagesLoader = imagesLoader;
    }

    @GetMapping("/")
    public String index(Map<String, Object> model) throws IOException {
        model.put("dirs",imagesLoader.inRoot());
        return TEMPLATE;
    }

    @ResponseBody
    @RequestMapping(value = "/img/{path}") // produces = "image/bmp
    public Resource getImage(@PathVariable("path") String path) throws IOException {
        return imagesLoader.load(path);
    }
}
