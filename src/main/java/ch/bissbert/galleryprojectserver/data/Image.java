package ch.bissbert.galleryprojectserver.data;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_image")
    private byte[] fullImage;

    @Column(name = "preview_image")
    private byte[] previewImage;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

    @Column(name = "bits_per_pixel")
    private int bitsPerPixel;

    @Column(name = "compression")
    private String compressionType;

    @ManyToOne
    @JoinColumn(name = "mime_typeid")
    private ImageMimeType mimeType;

    @Override
    public String toString() {
        return "Image{" +
                "\nid=" + id +
                ",\nfullImage=" + Arrays.toString(fullImage) +
                ",\npreviewImage=" + Arrays.toString(previewImage) +
                ",\nheight=" + height +
                ",\nwidth=" + width +
                ",\nbitsPerPixel=" + bitsPerPixel +
                ",\ncompressionType='" + compressionType + '\'' +
                ",\nmimeType=" + mimeType +
                "\n}";
    }
}
