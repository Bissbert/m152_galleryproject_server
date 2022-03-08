package ch.bissbert.galleryprojectserver.data;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageMimeType that = (ImageMimeType) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ImageMimeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
