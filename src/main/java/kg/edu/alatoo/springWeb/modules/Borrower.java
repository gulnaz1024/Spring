package kg.edu.alatoo.springWeb.modules;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Borrower {

    @Id @GeneratedValue
    private long id;
    private String name;
    private String email;
    private String phone_number;

    @ManyToMany(mappedBy = "borrowers")
    private Set<Book> books;


    public Borrower() {
    }

    public Borrower(String name, String email, String phone_number) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public long getId() {
        return id;
    }

    public Borrower setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrower borrower = (Borrower) o;
        return id == borrower.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }




}

