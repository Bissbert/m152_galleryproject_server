package ch.bissbert.galleryprojectserver;

import com.drew.imaging.ImageProcessingException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImageCSVTest {

    ImageCSV imageCSV;

    @BeforeEach
    void setUp() throws IOException {
        byte[] image = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("fluo.jpeg")).readAllBytes();
        imageCSV = new ImageCSV(image);
    }

    @Test
    void getBytes() throws ImageProcessingException, IOException {
        byte[] byteWorksheet = imageCSV.getBytes();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteWorksheet)){
            Workbook workbook = WorkbookFactory.create(byteArrayInputStream);
            Sheet jpegSheet = workbook.getSheet("JPEG");
            assertNotNull(jpegSheet);
            assertEquals("Baseline", jpegSheet.getRow(0).getCell(1).getStringCellValue());
            assertEquals("8 bits", jpegSheet.getRow(1).getCell(1).getStringCellValue());
            assertEquals("3024 pixels", jpegSheet.getRow(2).getCell(1).getStringCellValue());
        }
    }

    @Test
    void image() {
        assertNotNull(imageCSV.image());
    }
}