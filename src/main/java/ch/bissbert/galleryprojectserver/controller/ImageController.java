package ch.bissbert.galleryprojectserver.controller;

import ch.bissbert.galleryprojectserver.ImageUtil;
import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.repo.ImageMimeTypeRepository;
import ch.bissbert.galleryprojectserver.repo.ImageRepository;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    @Autowired
    private ImageMimeTypeRepository mimeTypeRepository;

    /**
     * A spring boot rest service using the post method
     * It takes a list of byte arrays and create Image objects using the Util class.
     * The generated images are saved using the image repository.
     *
     * @param image The list of byte arrays
     */
    @PostMapping("/images")
    @CrossOrigin(origins = "http://localhost:3000")
    public void saveImages(@RequestParam(name = "image") MultipartFile image) throws IOException, ImageReadException {
        imageRepository.save(ImageUtil.createImage(image.getBytes(), mimeTypeRepository));
    }

    /**
     * A spring boot rest service using the get method
     * It returns the images from the repository using pagination in increments of 6 per page
     * The images are returned as a list of byte arrays.
     *
     * @param page The page number
     *             The first page is 0
     *             The last page is the number of pages - 1
     */
    @GetMapping("/images")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Image> getImages(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return imageRepository.findAll(pageable).getContent();
    }

    /**
     * A spring boot rest service that serves a single image as a byte array.
     * It takes the id of the image as a parameter.
     * The image is returned as a byte array.
     *
     * @param id The id of the image
     *           The id is the primary key of the image
     * @return The image as a byte array
     */
    @GetMapping("/images/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public byte[] getImage(@PathVariable(name = "id", required = true) int id) {
        return imageRepository.findById(id).get().getFullImage();
    }
}
