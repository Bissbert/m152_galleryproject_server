package ch.bissbert.galleryprojectserver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class is used to upload an image.
 *  @author Bissbert
 *  @version 1.0
 *  @since 1.2
 */
@Getter
@Setter
public class ImageUpload {
    private MultipartFile image;
    private String description;
    private String title;
}
