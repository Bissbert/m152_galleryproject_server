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

public record ImageCSV(byte[] image) {

    private static final Logger logger = LoggerFactory.getLogger(ImageCSV.class);

    public byte[] getBytes() throws IOException, ImageProcessingException {

        Metadata metaData = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(image);

        metaData = ImageMetadataReader.readMetadata(stream);

        try (Workbook workbook = new XSSFWorkbook()) {
            for (Directory directory : metaData.getDirectories()) {
                Sheet sheet = workbook.createSheet(directory.getName());
                int rowCount = 0;
                for (Tag tag : directory.getTags()) {
                    logger.info("[%s] - %s = %s",
                            directory.getName(), tag.getTagName(), tag.getDescription());
                    Row row = sheet.createRow(rowCount);
                    rowCount++;
                    Cell cell = row.createCell(0);
                    cell.setCellValue(tag.getTagName());
                    sheet.autoSizeColumn(0);
                    Cell cell2 = row.createCell(1);
                    cell2.setCellValue(tag.getDescription());
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
}
