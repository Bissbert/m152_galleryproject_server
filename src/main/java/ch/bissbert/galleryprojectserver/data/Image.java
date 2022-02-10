package ch.bissbert.galleryprojectserver.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "FullImage")
    private byte[] fullImage;

    @Column(name = "PreviewImage")
    private byte[] previewImage;

    @Column(name = "Height")
    private int height;

    @Column(name = "Width")
    private int width;

    @Column(name = "BitsPerPixel")
    private int bitsPerPixel;

    @Column(name = "Compression")
    private String compressionType;

    @ManyToOne
    @JoinColumn(name = "MimeTypeID")
    private ImageMimeType mimeType;
}
