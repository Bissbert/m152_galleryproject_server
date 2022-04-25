package ch.bissbert.galleryprojectserver;

import ch.bissbert.galleryprojectserver.data.Image;
import ch.bissbert.galleryprojectserver.data.ImageMimeType;
import ch.bissbert.galleryprojectserver.repo.ImageMimeTypeRepository;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.awt.Image.SCALE_SMOOTH;

/**
 * Class for modifying the Image.
 *
 * @author Bissbert, LuckAndPluck
 * @version 1.0
 * @since 1.0
 */

public class ImageUtil {
    private static final int MAX_PREVIEW_SIZE = 500;

    private ImageUtil() {
    }

    /**
     * creates an image instance from the given image array, name and description.
     * The missing information if generated from the image array.
     *
     * @param imageArray         the image array
     * @param name               the name of the image
     * @param description        the description of the image
     * @param mimeTypeRepository the repository for the mime types
     * @return the image instance
     * @throws IOException        if the image array could not be read
     * @throws ImageReadException if the image array could not be read
     */
    public static Image createImage(
            byte[] imageArray,
            String name,
            String description,
            ImageMimeTypeRepository mimeTypeRepository
    ) throws IOException, ImageReadException {
        ImageInfo imageInfo = Imaging.getImageInfo(imageArray);

        return Image.builder()
                .name(name)
                .description(description)
                .fullImage(imageArray)
                .previewImage(toPreview(imageArray, imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getFormat().getExtension()))
                .bitsPerPixel(imageInfo.getBitsPerPixel())
                .compressionType(imageInfo.getCompressionAlgorithm().name())
                .mimeType(getMimeType(imageInfo.getMimeType(), mimeTypeRepository))
                .height(imageInfo.getHeight())
                .width(imageInfo.getWidth())
                .build();
    }

    /**
     * gets the mimetype for the given mime type.
     * If the mime type does not exist, it is created.
     *
     * @param mimeType                the mime type as string
     * @param imageMimeTypeRepository the repository for the mime types
     * @return the mime type instance
     */
    protected static ImageMimeType getMimeType(String mimeType, ImageMimeTypeRepository imageMimeTypeRepository) {
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
    protected static byte[] toPreview(byte[] imageArray, int width, int height, String extension) throws IOException {
        int newWidth = width;
        int newHeight = height;
        if (width > MAX_PREVIEW_SIZE || height > MAX_PREVIEW_SIZE) {
            if (width > height) {
                newWidth = MAX_PREVIEW_SIZE;
                newHeight = (int) (height * ((double) MAX_PREVIEW_SIZE / width));
            } else {
                newHeight = MAX_PREVIEW_SIZE;
                newWidth = (int) (width * ((double) MAX_PREVIEW_SIZE / height));
            }
        }
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageArray));
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, extension, baos);
        return baos.toByteArray();
    }
}
