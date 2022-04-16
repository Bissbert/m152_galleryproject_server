package ch.bissbert.galleryprojectserver.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUpload {
    private MultipartFile image;
    private String description;
    private String title;
}
