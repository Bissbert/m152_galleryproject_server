package ch.bissbert.galleryprojectserver.controller;

import ch.bissbert.galleryprojectserver.ImageUtil;
import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.repo.ImageMimeTypeRepository;
import ch.bissbert.galleryprojectserver.repo.ImageRepository;
import com.drew.imaging.ImageProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ImageControllerTest {

    private final ImageController imageController = new ImageController();

    @Mock
    ImageRepository imageRepository;

    @Mock
    ImageMimeTypeRepository imageMimeTypeRepository;

    ImageControllerData data;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        data = new ImageControllerData();
        imageController.setImageRepository(imageRepository);
        imageController.setMimeTypeRepository(imageMimeTypeRepository);


        when(imageRepository.findAllWithoutImages(any()))
                .thenReturn(new PageImpl<>(new ArrayList<>() {{
                            add(data.IMAGE);
                        }})
                );

        when(imageRepository.findAllByIdIn(any(), eq(data.IDS)))
                .thenReturn(new PageImpl<>(new ArrayList<>() {{
                            add(data.IMAGE_ID_1);
                            add(data.IMAGE_ID_2);
                        }})
                );

        when(imageRepository.getById(1)).thenReturn(data.IMAGE_ID_1);

        when(imageRepository.findPreview(1)).thenReturn(data.IMAGE_ID_1);
    }

    @Test
    void saveImages() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "someFile.png",
                "test.png",
                Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png")
        );
        ImageUpload imageUpload = new ImageUpload();
        imageUpload.setImage(multipartFile);
        imageUpload.setDescription("WHY");
        imageController.saveImages(imageUpload);
        verify(imageRepository).save(
                ImageUtil.createImage(
                        multipartFile.getBytes(),
                        multipartFile.getName(),
                        imageUpload.getDescription(),
                        imageMimeTypeRepository
                )
        );
    }

    @Test
    void getImagesWithoutId() {
        List<Image> images = imageController.getImages(0, 2, null, null);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        verify(imageRepository).findAllWithoutImages(pageable);
        assertEquals(images.get(0), data.IMAGE);
    }

    @Test
    void getImagesWithSort() {
        List<Image> images = imageController.getImages(0, 2, data.SORT_STRINGS, null);
        Pageable pageable = PageRequest.of(0, 2, data.SORT);
        verify(imageRepository).findAllWithoutImages(pageable);
        assertEquals(images.get(0), data.IMAGE);
    }

    @Test
    void getImagesWithId() {
        List<Image> images = imageController.getImages(0, 2, null, data.IDS);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        verify(imageRepository).findAllByIdIn(pageable, data.IDS);
        assertEquals(data.IMAGE_ID_1, images.get(0));
    }

    @Test
    void getImage() {
        ResponseEntity<byte[]> responseEntity = imageController.getImage(1);
        verify(imageRepository).getById(1);
        assertEquals(data.IMAGE_ID_1.getFullImage(), responseEntity.getBody());
    }

    @Test
    void getpreviewImage() {
        ResponseEntity<byte[]> responseEntity = imageController.getpreviewImage(1);
        verify(imageRepository).findPreview(1);
        assertEquals(data.IMAGE_ID_1.getPreviewImage(), responseEntity.getBody());
    }

    @Test
    public void deleteImage() {
        imageController.deleteImage(1);
        verify(imageRepository).deleteById(1);
    }

    @Test
    void getMetaData() throws ImageProcessingException, IOException {
        final int id = 1;
        imageController.getMetaData(1);
        verify(imageRepository).getById(1);
    }

    @Test
    void sortFromString() {
        //desc
        assertEquals(Sort.by("id").descending(), ImageController.sortFromString("id-desc"));
        assertEquals(Sort.by("id").descending(), ImageController.sortFromString("id-descend"));
        assertEquals(Sort.by("id").descending(), ImageController.sortFromString("id-descending"));
        //asc
        assertEquals(Sort.by("id").ascending(), ImageController.sortFromString("id-asc"));
        assertEquals(Sort.by("id").ascending(), ImageController.sortFromString("id-ascend"));
        assertEquals(Sort.by("id").ascending(), ImageController.sortFromString("id-ascending"));
    }
}