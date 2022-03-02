package ch.bissbert.galleryprojectserver.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "full_image")
    private byte[] fullImage;

    @Column(name = "Preview_image")
    private byte[] previewImage;

    @Column(name = "Height")
    private int height;

    @Column(name = "Width")
    private int width;

    @Column(name = "Bits_Per_Pixel")
    private int bitsPerPixel;

    @Column(name = "Compression")
    private String compressionType;

    @ManyToOne
    @JoinColumn(name = "Mime_TypeID")
    private ImageMimeType mimeType;
}
