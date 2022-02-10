package ch.bissbert.galleryprojectserver;

import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.data.ImageMimeType;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static Image createImage(byte[] imageArray) throws IOException, ImageReadException {
        ImageInfo imageInfo = Imaging.getImageInfo(imageArray);

        return Image.builder()
                .fullImage(imageArray)
                .previewImage(toPreview(imageArray, imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getFormat().getExtension()))
                .bitsPerPixel(imageInfo.getBitsPerPixel())
                .compressionType(imageInfo.getCompressionAlgorithm().name())
                .mimeType(getMimeType(imageInfo.getMimeType()))
                .build();
    }

    private static ImageMimeType getMimeType(String mimeType) {
        return null;
    }

    /**
     * A preview image is a smaller version of the original image.
     *
     * @param imageArray the original image
     * @param width      the width of the original image
     * @param height     the height of the original image
     * @param extension  the extension of the original image
     * @return a smaller version of the original image
     */
    private static byte[] toPreview(byte[] imageArray, int width, int height, String extension) throws IOException {
        int maxWidth = 200;
        int maxHeight = 200;
        int newWidth = width;
        int newHeight = height;
        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                newWidth = maxWidth;
                newHeight = (int) (height * ((double) maxWidth / width));
            } else {
                newHeight = maxHeight;
                newWidth = (int) (width * ((double) maxHeight / height));
            }
        }
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageArray));
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, extension, baos);
        return baos.toByteArray();
    }

    public static List<Image> createImages(List<byte[]> images) throws IOException, ImageReadException {
        List<Image> imagesList = new ArrayList<>();
        for (byte[] imageAsByte : images) {
            imagesList.add(createImage(imageAsByte));
        }
        return imagesList;
    }
}
