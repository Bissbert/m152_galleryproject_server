package ch.bissbert.galleryprojectserver.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ImageTest {

    @Test
    public void testEquals() {
        Image id1Image = new Image();
        id1Image.setId(1);
        assertEquals(id1Image, Image.builder().id(1).build());
    }
}