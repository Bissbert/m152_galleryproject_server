package ch.bissbert.galleryprojectserver.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageTest {

    @Test
    public void testEquals() {
        Image id1Image = new Image();
        id1Image.setId(1);
        assertEquals(id1Image, Image.builder().id(1).build());
    }
}