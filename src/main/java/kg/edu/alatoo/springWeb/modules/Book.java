package kg.edu.alatoo.springWeb.modules;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {

    @Id @GeneratedValue
    private long id;

    @NotBlank(message = "It shouldn't be empty")
    private String title;

    private String author;

    private String publisher;
    private String isbn;

    /*@Column
    private boolean given = true;*/

    @ManyToMany
    private Set<Borrower> borrowers;



}
