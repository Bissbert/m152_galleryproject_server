package ch.bissbert.galleryprojectserver.controller;

import ch.bissbert.galleryprojectserver.Util;
import ch.bissbert.galleryprojectserver.repo.ImageRepository;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the image resource.
 * Has an autowire to the image repository.
 *
 * @author Bissbert
 * @version 1.0
 * @since 1.0
 */
@RestController
public class ImageController {


    @Autowired
    private ImageRepository imageRepository;

    /**
     * A spring boot rest service using the post method
     * It takes a list of byte arrays and create Image objects using the Util class.
     * The generated images are saved using the image repository.
     *
     * @param images The list of byte arrays
     */
    @PostMapping("/saveImages")
    public void saveImages(@RequestBody List<byte[]> images) throws IOException, ImageReadException {
        imageRepository.saveAll(Util.createImages(images));
    }

    /**
     * A spring boot rest service using the get method
     * It returns the images from the repository using pagination in increments of 6 per page
     * The images are returned as a list of byte arrays.
     * @param page The page number
     *             The first page is 0
     *             The last page is the number of pages - 1
     */
    @GetMapping("/getImages")
    public void getImages(int page)  throws IOException, ImageReadException{
        Pageable galleryPage = PageRequest.of(page,6);
        imageRepository.findAll(galleryPage);
    }
}
