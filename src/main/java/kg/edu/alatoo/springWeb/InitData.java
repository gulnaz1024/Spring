package kg.edu.alatoo.springWeb;

import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.modules.Borrower;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import kg.edu.alatoo.springWeb.repos.BorrowerRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements InitializingBean {

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public InitData(BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public void afterPropertiesSet() {

        if (bookRepository.count() == 0) {
            List<Book> books = new ArrayList<>();
            Book sherlok = new Book("Sherlock Holms", "Artur", "Bishkek", "3541354");
            books.add(sherlok);
            System.out.println("Before save: " + books);
            bookRepository.saveAll(books);
            System.out.println("After save: " + books);

            Borrower gulnaz = new Borrower("Gulnaz", "gulnaz@gmail.com", "+996555505050");
            Borrower halah = new Borrower("Halah", "halah@gmail.com", "+996555505050");
            borrowerRepository.save(gulnaz);
            borrowerRepository.save(halah);
        }

    }
}
