package kg.edu.alatoo.springWeb.services;

import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void updateBookById(long id, Book book) {
        Book existed = bookRepository.findBookById(id);
        existed.setTitle(book.getTitle());
        existed.setAuthor(book.getAuthor());
        existed.setPublisher(book.getPublisher());
        existed.setIsbn(book.getIsbn());
        bookRepository.save(existed);
    }


    @Override
    public Book findById(long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);

    }



    @Override
    public void patchBookById(long id, Book book) {
        Book existed = bookRepository.findBookById(id);
        if (StringUtils.hasText(book.getTitle())) {
            existed.setTitle(book.getTitle());
        }
        if (StringUtils.hasText(book.getAuthor())) {
            existed.setAuthor(book.getAuthor());
        }
        if (StringUtils.hasText(book.getPublisher())) {
            existed.setPublisher(book.getPublisher());
        }
        if (StringUtils.hasText(book.getIsbn())) {
            existed.setIsbn(book.getIsbn());
        }
        bookRepository.save(existed);
    }


}
