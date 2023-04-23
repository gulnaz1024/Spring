package kg.edu.alatoo.springWeb.modules;

import jakarta.persistence.*;
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
public class Borrower {

    @Id @GeneratedValue
    private long id;
    private String name;
    private String email;
    private String phone_number;

    @ManyToMany(mappedBy = "borrowers")
    private Set<Book> books;

}

