package ch.bissbert.galleryprojectserver.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ImageTest {

    @Test
    public void testEquals() {
        Image id1Image = new Image();
        id1Image.setId(1);
        assertEquals(id1Image, Image.builder().id(1).build());
        assertNotEquals(id1Image, null);
        assertNotEquals(id1Image, "null");
    }

    @Test
    public void noConstructorCrash() {
        Image image1 = new Image(1, 10, 10, 10, "Test", "testname", "descr", ImageMimeType.builder().build());
        Image image2 = new Image(2, new byte[10], ImageMimeType.builder().build());
        assertNotEquals(image1.getId(), image2.getId());
    }
}