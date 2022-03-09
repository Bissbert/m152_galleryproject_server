package ch.bissbert.galleryprojectserver.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class ImageTest {

    @Test
    void testEquals() {
        Image id1Image = new Image();
        id1Image.setId(1);
        assertEquals(id1Image, Image.builder().id(1).build());
        assertNotEquals(null, id1Image);
        assertNotEquals("null", id1Image);
        assertFalse(id1Image.equals(null));
        assertFalse(id1Image.equals("null"));
    }

    @Test
    void noConstructorCrash() {
        Image image1 = new Image(1, 10, 10, 10, "Test", "testname", "descr", ImageMimeType.builder().build());
        Image image2 = new Image(2, new byte[10], ImageMimeType.builder().build());
        assertNotEquals(image1.getId(), image2.getId());
        assertNotEquals(image1, image2);
    }

    @Test
    public void testHashCode() {
        Image image = Image.builder().id(1).build();
        assertEquals(Objects.hash(image.getId()), image.hashCode());
    }
}