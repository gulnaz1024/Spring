package kg.edu.alatoo.springWeb.services;

import kg.edu.alatoo.springWeb.modules.Book;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface BookService {

    List<Book> findAll();

    Book saveBook(Book Book);


    void updateBookById(long id, Book Book);

    Book findById(long id);

    void deleteById(long id);

    void patchBookById(long id, Book Book);
}
