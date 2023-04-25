package kg.edu.alatoo.springWeb.services;

import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServicempl implements BookService{
    private final Map<Long, Book> bookData = new HashMap<>();

    private final BookRepository bookRepository;
    public BookServicempl(BookRepository bookRepository) {
//        Book prod1 = Book.builder()
//                .title("Product 1")
//                .author("Product 1 Description")
//                .publisher("2.52")
//                .isbn("123456")
//                .build();
//
//        bookData.put(prod1.getId(), prod1);

        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {

        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book Book) {
        return null;
    }

    @Override
    public void updateBookById(long id, Book Book) {

    }

    @Override
    public Book findById(long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void patchBookById(long id, Book Book) {

    }


}
