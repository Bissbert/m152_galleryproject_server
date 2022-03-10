package ch.bissbert.galleryprojectserver;

import ch.bissbert.galleryprojectserver.data.Image;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
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
import java.util.List;

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
                    System.out.format("[%s] - %s = %s",
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
                workbook.close();
                return outputStream.toByteArray();
            }
        }
    }
}
