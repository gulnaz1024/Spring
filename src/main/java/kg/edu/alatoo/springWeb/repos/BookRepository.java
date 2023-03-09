package kg.edu.alatoo.springWeb.repos;


import kg.edu.alatoo.springWeb.modules.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByIsbn(String isbn);
    List<Book> findBookByTitle(String title);
}
