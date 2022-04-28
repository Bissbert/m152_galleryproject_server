package ch.bissbert.galleryprojectserver;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class for the extraction from the Metadata from the Image. Extrated as an Excel file.
 *
 * @author Bissbert, LuckAndPluck
 * @version 1.0
 * @since 1.0
 */

public record ImageCSV(byte[] image) {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCSV.class);

    /**
     * @return the Excel file containing the metadata as a byte array
     * @throws IOException              when the byte array provided is not valid image type
     * @throws ImageProcessingException when the image is processes by the reader and doesn't have the needed data
     */

    public byte[] getBytes() throws IOException, ImageProcessingException {

        ByteArrayInputStream stream = new ByteArrayInputStream(image);
        Metadata metaData = ImageMetadataReader.readMetadata(stream);

        try (Workbook workbook = new XSSFWorkbook()) {
            for (Directory directory : metaData.getDirectories()) {

                Sheet sheet = workbook.createSheet(directory.getName());
                int rowCount = 0;

                for (Tag tag : directory.getTags()) {

                    LOGGER.info(String.format("[%s] - %s = %s", directory.getName(), tag.getTagName(), tag.getDescription()));

                    Row row = sheet.createRow(rowCount);
                    rowCount++;

                    Cell tagNameCell = row.createCell(0);
                    tagNameCell.setCellValue(tag.getTagName());
                    sheet.autoSizeColumn(0);

                    Cell tagValueCell = row.createCell(1);
                    tagValueCell.setCellValue(tag.getDescription());
                    sheet.autoSizeColumn(1);

                }
            }


            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageCSV imageCSV = (ImageCSV) o;

        return Arrays.equals(image, imageCSV.image);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(image);
    }

    @Override
    public String toString() {
        return "ImageCSV{" + "image=" + Arrays.toString(image) + '}';
    }
}
