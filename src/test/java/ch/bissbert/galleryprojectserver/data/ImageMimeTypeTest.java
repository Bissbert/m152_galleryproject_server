package ch.bissbert.galleryprojectserver.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ImageMimeTypeTest {

    @Test
    public void testEquals() {
        String name = "test";
        ImageMimeType mimeType = new ImageMimeType();
        mimeType.setId(1);
        mimeType.setName(name);
        assertEquals(mimeType, ImageMimeType.builder().name(name).id(1).build());
        assertNotEquals(null, mimeType);
        assertNotEquals("", mimeType);
    }
}