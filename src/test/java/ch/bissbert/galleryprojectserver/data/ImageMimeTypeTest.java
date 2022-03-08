package ch.bissbert.galleryprojectserver.data;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ImageMimeTypeTest {

    String name = "test";

    @Test
    void testEquals() {

        ImageMimeType mimeType = new ImageMimeType();
        mimeType.setId(1);
        mimeType.setName(name);
        assertEquals(mimeType, ImageMimeType.builder().name(name).id(1).build());
        assertNotEquals(null, mimeType);
        assertNotEquals("", mimeType);
        assertNotEquals(mimeType, "mimeType");
        assertNotEquals(mimeType, null);
        ImageMimeType mimeType1 = new ImageMimeType(1,"noTest");
        assertNotEquals(mimeType,mimeType1);
    }

    @Test
    void hashTest(){
        ImageMimeType mimeType = ImageMimeType.builder().name(name).id(1).build();
        assertEquals(Objects.hash(mimeType.getId(), mimeType.getName()),mimeType.hashCode());
    }
}