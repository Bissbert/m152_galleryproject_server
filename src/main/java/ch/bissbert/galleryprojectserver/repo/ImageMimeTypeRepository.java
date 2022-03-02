package ch.bissbert.galleryprojectserver.repo;

import ch.bissbert.galleryprojectserver.data.ImageMimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageMimeTypeRepository extends JpaRepository<ImageMimeType, Integer> {
    ImageMimeType findImageMimeTypeByName(String name);
}