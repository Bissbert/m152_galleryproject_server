package ch.bissbert.galleryprojectserver.controller;

import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.data.ImageMimeType;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageControllerData {
    public final Image IMAGE = Image.builder()
            .id(0)
            .name("testname")
            .bitsPerPixel(10)
            .compressionType("PNG")
            .build();

    public final Image IMAGE_ID_1 = Image.builder()
            .id(1)
            .name("testname")
            .bitsPerPixel(10)
            .compressionType("PNG")
            .previewImage(new byte[2])
            .fullImage(Thread.currentThread().getContextClassLoader().getResourceAsStream("fluo.jpeg").readAllBytes())
            .mimeType(ImageMimeType.builder().name("image/png").id(1).build())
            .build();

    public final Image IMAGE_ID_2 = Image.builder()
            .id(2)
            .name("testname")
            .bitsPerPixel(10)
            .compressionType("PNG")
            .build();

    public final List<String> SORT_STRINGS = new ArrayList<>() {{
        add("id-desc");
        add("height");
    }};

    public final Sort SORT = Sort.by("id").descending().and(Sort.by("height"));

    public final List<Integer> IDS = new ArrayList<>() {{
        add(1);
        add(2);
    }};

    public ImageControllerData() throws IOException {
    }
}
