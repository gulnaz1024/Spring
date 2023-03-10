package kg.edu.alatoo.springWeb.controllers;

import jakarta.validation.Valid;
import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Controller + ResponseBody = RestController
@Controller
public class MainController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/main")
    public String showMainPage(Book book) {
        return "main";
    }
    @GetMapping("/addbook")
    public String showSignUpForm(Book book) {
        return "add-book";
    }

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


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book books = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("book", books);
        return "update-book";
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

    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            return "add-book";
        }

        bookRepository.save(book);

        Iterable<Book> books = bookRepository.findAll();

        model.put("books", books);
        return "redirect:/index";
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


    @GetMapping("/isbn/{isbn}")
    @ResponseBody
    public Book getByIsbn(@PathVariable(name = "isbn") String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

}
