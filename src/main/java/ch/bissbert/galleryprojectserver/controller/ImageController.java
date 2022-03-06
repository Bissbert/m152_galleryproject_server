package ch.bissbert.galleryprojectserver.controller;

import ch.bissbert.galleryprojectserver.ImageUtil;
import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.repo.ImageMimeTypeRepository;
import ch.bissbert.galleryprojectserver.repo.ImageRepository;
import org.apache.commons.imaging.ImageReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    private static Logger logger = LoggerFactory.getLogger(ImageController.class);


    /**
     * A spring boot rest service using the post method
     * It takes a list of byte arrays and create Image objects using the Util class.
     * The generated images are saved using the image repository.
     *
     * @param image The list of byte arrays
     */
    @PostMapping("/images")
    @CrossOrigin(origins = "http://localhost:3000")
    public void saveImages(@ModelAttribute ImageUpload image) throws IOException, ImageReadException {
        imageRepository.save(
                ImageUtil.createImage(
                        image.getImage().getBytes(),
                        image.getImage().getOriginalFilename(),
                        image.getDescription(),
                        mimeTypeRepository
                )
        );
    }

    /**
     * A spring boot rest service using the get method
     * It returns the images from the repository using pagination in increments of 6 per page
     * The images are returned as a list of byte arrays.
     *
     * @param page The page number
     *             The first page is 0
     *             The last page is the number of pages - 1
     * @param size The number of images per page
     * @return list of image data
     */
    @GetMapping("/images")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Image> getImages(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, name = "sort") List<String> sortBy,
            @RequestParam(required = false) List<Integer> idList
    ) {
        Sort sort = sortBy != null ? sortBy
                .stream()
                .map(ImageController::sortFromString)
                .reduce(Sort::and)
                .orElse(Sort.by("id")) : Sort.by("id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Image> images;
        if (idList == null) {
            images = imageRepository.findAllWithoutImages(pageable).getContent();
        } else {
            images = imageRepository.findAllByIdIn(pageable, idList).getContent();
        }
        logger.info("images from database: ");
        images.stream().map(Image::toString).forEach(s -> logger.info(s));
        return images;
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
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "id", required = true) int id) {
        Image image = imageRepository.findById(id).get();
        return ResponseEntity
                .accepted()
                .contentType(MediaType.parseMediaType(image.getMimeType().getName()))
                .body(image.getFullImage());
    }

    /**
     * A spring boot rest service that serves a single preview image as a byte array.
     * It takes the id of the image as a parameter.
     * The image is returned as a byte array.
     *
     * @param id The id of the image
     *           The id is the primary key of the image
     * @return The preview image as a byte array
     */
    @GetMapping("/images/preview/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<byte[]> getpreviewImage(@PathVariable(name = "id", required = true) int id) {
        Image image = imageRepository.findPreview(id);
        return ResponseEntity
                .accepted()
                .contentType(MediaType.parseMediaType(image.getMimeType().getName()))
                .body(image.getPreviewImage());
    }

    public static Sort sortFromString(String s) {
        String[] split = s.split("-");
        String sortString = split[0];
        Sort sortFromString = Sort.by(sortString);
        if (split.length > 1) {
            switch (split[1].toLowerCase(Locale.ROOT)) {
                case "desc":
                case "descend":
                case "descending":
                    sortFromString = sortFromString.descending();
                    break;
                case "asc":
                case "ascend":
                case "ascending":
                    sortFromString = sortFromString.ascending();
                    break;
                default:
            }
        }
        return sortFromString;
    }
}
