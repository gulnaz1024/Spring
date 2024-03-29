package kg.edu.alatoo.springWeb.modules;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class Book {

    @Id @GeneratedValue
    private long id;

    @NotBlank(message = "It shouldn't be empty")
    private String title;

    private String author;

    private String publisher;
    private String isbn;

    @Column(nullable = true, length = 64)
    private String photos;
    /*@Column
    private boolean given = true;*/

    @ManyToMany
    private Set<Borrower> borrowers;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null) return null;

        return "/user-photos/" + id + "/" + photos;
    }


}