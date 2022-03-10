package ch.bissbert.galleryprojectserver.repo;

import ch.bissbert.galleryprojectserver.data.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query("select new Image(i.id, i.height, i.width, i.bitsPerPixel, i.compressionType, i.name,i.description, i.mimeType) from Image i")
    public Page<Image> findAllWithoutImages(Pageable pageable);

    @Query("select new Image(i.id, i.height, i.width, i.bitsPerPixel, i.compressionType, i.name,i.description, i.mimeType) from Image i where i.id in :ids")
    public Page<Image> findAllByIdIn(Pageable pageable, List<Integer> ids);

    @Query("select new Image(i.id, i.previewImage, i.mimeType) from Image i where i.id = :id")
    public Image findPreview(@Param("id") int id);
}
