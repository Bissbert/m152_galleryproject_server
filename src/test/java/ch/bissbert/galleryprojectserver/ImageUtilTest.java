package ch.bissbert.galleryprojectserver;

import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.data.ImageMimeType;
import ch.bissbert.galleryprojectserver.repo.ImageMimeTypeRepository;
import org.apache.commons.imaging.ImageReadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ImageUtilTest {

    byte[] fullImage;
    byte[] previewImage;
    String name = "fluo.jpeg";
    String description = "An Image of a fluorite";
    ImageMimeTypeRepository mimeTypeRepository = mock(ImageMimeTypeRepository.class);
    ImageMimeType imageMimeType = ImageMimeType.builder().name("image/jpeg").id(1).build();
    int height = 3024, width = 4032;

    String jpegMime = "image/jpeg";

    @BeforeEach
    void setUp() throws IOException {
        fullImage = Thread.currentThread().getContextClassLoader().getResourceAsStream("fluo.jpeg").readAllBytes();
        previewImage = Thread.currentThread().getContextClassLoader().getResourceAsStream("fluoPrev.jpeg").readAllBytes();
        when(mimeTypeRepository.findImageMimeTypeByName(jpegMime)).thenReturn(imageMimeType);
    }

    @Test
    void createImage() throws IOException, ImageReadException {
        Image image = ImageUtil.createImage(fullImage, name, description, mimeTypeRepository);
        verify(mimeTypeRepository).findImageMimeTypeByName(anyString());
        assertEquals(name, image.getName());
        assertEquals(description, image.getDescription());
        assertEquals(height, image.getHeight());
        assertEquals(width, image.getWidth());
        assertEquals("JPEG", image.getCompressionType());
        assertArrayEquals(previewImage, image.getPreviewImage());
        assertEquals(24, image.getBitsPerPixel());
        assertEquals(imageMimeType, image.getMimeType());
    }

    @Test
    void getMimeType() {
        String inexistent = "image/notADataType";
        ImageUtil.getMimeType(inexistent, mimeTypeRepository);
        verify(mimeTypeRepository).findImageMimeTypeByName(inexistent);
        verify(mimeTypeRepository).save(ImageMimeType.builder().name(inexistent).build());

        ImageUtil.getMimeType(jpegMime, mimeTypeRepository);
        verify(mimeTypeRepository).findImageMimeTypeByName(jpegMime);
        verify(mimeTypeRepository, never()).save(ImageMimeType.builder().name(jpegMime).build());
    }

    @Test
    void toPreview() throws IOException {
        assertArrayEquals(previewImage, ImageUtil.toPreview(fullImage, width, height, "jpeg"));
    }
}