package ch.bissbert.galleryprojectserver.repo;

import ch.bissbert.galleryprojectserver.data.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}