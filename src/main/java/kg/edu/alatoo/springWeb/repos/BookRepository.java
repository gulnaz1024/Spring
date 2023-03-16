package kg.edu.alatoo.springWeb.repos;


import kg.edu.alatoo.springWeb.modules.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import kg.edu.alatoo.springWeb.modules.Book;
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByIsbn(String isbn);


    List<Book> findBookByTitleOrAuthorOrIsbn(String title, String author, String isbn);

    List<Book> findAll();

    Book findBookById(long id);


    @Query("UPDATE Book t SET t.given = :given WHERE t.id = :id")
    @Modifying
    public void updateGivenStatus(Integer id, boolean given);
}
