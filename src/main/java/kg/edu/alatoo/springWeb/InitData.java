package kg.edu.alatoo.springWeb;

import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class InitData implements InitializingBean {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public void afterPropertiesSet() {

        List<Book> books = new ArrayList<>();
        Book sherlok = new Book("Sherlock Holms","Artur", "Bishkek", "3541354" );
        System.out.println("Before save: " + books);
        bookRepository.saveAll(books);
        System.out.println("After save: "+ books);

    }
}
