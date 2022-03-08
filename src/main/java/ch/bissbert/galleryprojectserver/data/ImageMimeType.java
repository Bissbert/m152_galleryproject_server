package ch.bissbert.galleryprojectserver.data;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "image_mime_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageMimeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "ImageMimeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
