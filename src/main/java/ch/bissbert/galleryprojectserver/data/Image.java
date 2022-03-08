package ch.bissbert.galleryprojectserver.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    public Image(int id, int height, int width, int bitsPerPixel, String compressionType, String name, String description, ImageMimeType mimeType) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.bitsPerPixel = bitsPerPixel;
        this.compressionType = compressionType;
        this.name = name;
        this.description = description;
        this.mimeType = mimeType;
    }

    public Image(int id, byte[] previewImage, ImageMimeType mimeType) {
        this.id = id;
        this.previewImage = previewImage;
        this.mimeType = mimeType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonIgnore
    @Column(name = "full_image")
    private byte[] fullImage;

    @JsonIgnore
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

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "mime_typeid")
    private ImageMimeType mimeType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fullImage=" + Arrays.toString(fullImage) +
                ", previewImage=" + Arrays.toString(previewImage) +
                ", height=" + height +
                ", width=" + width +
                ", bitsPerPixel=" + bitsPerPixel +
                ", compressionType='" + compressionType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mimeType=" + mimeType +
                '}';
    }
}
