package ch.bissbert.galleryprojectserver;

import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.data.ImageMimeType;
import ch.bissbert.galleryprojectserver.repo.ImageMimeTypeRepository;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class ImageUtil {
    public static Image createImage(byte[] imageArray, String name, ImageMimeTypeRepository mimeTypeRepository) throws IOException, ImageReadException {
        ImageInfo imageInfo = Imaging.getImageInfo(imageArray);

        return Image.builder()
                .name(name)
                .description(imageInfo.getComments().stream().reduce((s, s2) -> s + "\n" + s2).orElse(null))
                .fullImage(imageArray)
                .previewImage(toPreview(imageArray, imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getFormat().getExtension()))
                .bitsPerPixel(imageInfo.getBitsPerPixel())
                .compressionType(imageInfo.getCompressionAlgorithm().name())
                .mimeType(getMimeType(imageInfo.getMimeType(), mimeTypeRepository))
                .height(imageInfo.getHeight())
                .width(imageInfo.getWidth())
                .build();
    }

    private static ImageMimeType getMimeType(String mimeType, ImageMimeTypeRepository imageMimeTypeRepository) {
        ImageMimeType imageMimeType = imageMimeTypeRepository.findImageMimeTypeByName(mimeType);
        if (imageMimeType == null) {
            imageMimeType = ImageMimeType.builder().name(mimeType).build();
            imageMimeTypeRepository.save(imageMimeType);
        }
        return imageMimeType;
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

    public static List<Image> createImages(List<byte[]> images, String name, ImageMimeTypeRepository mimeTypeRepository) throws IOException, ImageReadException {
        List<Image> imagesList = new ArrayList<>();
        for (byte[] imageAsByte : images) {
            imagesList.add(createImage(imageAsByte, name, mimeTypeRepository));
        }
        return imagesList;
    }
}
