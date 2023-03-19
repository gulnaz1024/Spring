package kg.edu.alatoo.springWeb.controllers;

import jakarta.validation.Valid;
import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.modules.Borrower;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import kg.edu.alatoo.springWeb.repos.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Controller + ResponseBody = RestController
@Controller
public class MainController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;
    @GetMapping("/main")
    public String showMainPage(Book book) {
        return "main";
    }
    @GetMapping("/addbook")
    public String showSignUpForm(Book book) {
        return "add-book";
    }
    @GetMapping("/addnewborrower")
    public String showBorrowerAddForm(Borrower borrower) {
        return "add-newBorrower";
    }

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @GetMapping({"/index","/"})
    public String showBookList(Model model) {

        Iterable<Book> books = bookRepository.findAll();
        for (Book book :
                books) {
            book.getTitle();

        }
        model.addAttribute("books", books);
        return "index";
    }
    @GetMapping({"/borrower-list"})
    public String showBorrowerList(Model model) {
        List<Borrower> borrowers = borrowerRepository.findAll();
        model.addAttribute("borrowers", borrowers);
        return "borrower-list";
    }


    @GetMapping({"/book-borrower"})
    public String showBorrowerAndBookList(Model model) {

        List<Book> books = bookRepository.findAll();
        books = books.stream().filter(b->!b.getBorrowers().isEmpty()).collect(Collectors.toList());

        for (Book book : books) {
            book.getTitle();
            book.getBorrowers();
        }
        model.addAttribute("books", books);
        return "book-borrower";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book books = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("book", books);
        return "update-book";
    }

    @GetMapping("/editBorrower/{id}")
    public String showUpdateBorrowerForm(@PathVariable("id") long id, Model model) {
        Borrower borrowers = borrowerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("borrower", borrowers);
        return "update-borrower";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        try {
            bookRepository.delete(book);

            redirectAttributes.addFlashAttribute("message", "The book with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/index";
    }
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid Book book,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookRepository.save(book);
        return "redirect:/index";
    }
    @PostMapping("/updateBorrower/{id}")
    public String updateBorrower(@PathVariable("id") long id, @Valid Borrower borrower,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            borrower.setId(id);
            return "update-borrower";
        }

        borrowerRepository.save(borrower);
        return "redirect:/borrower-list";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            return "add-book";
        }

        bookRepository.save(book);

        return "redirect:/index";
    }

    @PostMapping("/addnewborrower")
    public String addNewBorrower(@Valid Borrower borrower, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            return "add-newBorrower";
        }

        borrowerRepository.save(borrower);

        return "redirect:/borrower-list";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam("filter") String filter, Map<String, Object> model) {
        Iterable<Book> books;

        if (filter != null && !filter.isEmpty()) {
            books = bookRepository.findBookByTitleOrAuthorOrIsbn(filter, filter, filter);
            model.put("filter", filter);
        } else {
            books = bookRepository.findAll();
        }

        model.put("books", books);

        return "index";
    }

    @GetMapping("/index/{id}/given/{status}")
    public String updateGivenStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean given,
                                                Model model, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.updateGivenStatus(id, given);

            String status = given ? "given" : "disabled";
            String message = "The Book id=" + id + " has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/index";
    }

    @GetMapping(value = "/addBookBorrower/{id}")
    public String getBookBorrower(@PathVariable(value = "id") Long id, Model model){

        Book book = bookRepository.findBookById(id);
        List<Borrower> borrower = borrowerRepository.findAll();

        if(!book.getBorrowers().isEmpty()){
            return "redirect:/index";
        }

        model.addAttribute("book", book);
        model.addAttribute("borrowers", borrower);

        return "add-borrower";
    }

    @PostMapping(value = "/addBookBorrower/{id}")
    public String addBookBorrower(@PathVariable(value = "id") Long bookId, @RequestParam long borrowerId, Model model){

        Book book = bookRepository.findBookById(bookId);
        Borrower borrower = borrowerRepository.findBorrowerById(borrowerId);

        if(book != null)
        {
            book.getBorrowers().add(borrower);

        }
        bookRepository.save(book);
        return "redirect:/book-borrower";
    }


    @GetMapping("/isbn/{isbn}")
    @ResponseBody
    public Book getByIsbn(@PathVariable(name = "isbn") String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

}
