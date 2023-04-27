package kg.edu.alatoo.springWeb.controllers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.services.BookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final BookService bookService;

    @GetMapping
    public List<Book> allBooks() {
        log.debug("Getting all product"); // use like this
        return bookService.findAll();
    }

    @PatchMapping( "{bookId}")
    public ResponseEntity<?> updatePatchById(@PathVariable("bookId") long id, @RequestBody Book book) {
        log.debug("Patch product with id " + id + ": "  + book);
        bookService.patchBookById(id,book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping( "{bookId}")
    public Book getBookById(@PathVariable("bookId") long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("bookId") long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location","/api/v1/book/" + savedBook.getId());
        return new ResponseEntity<>(responseHeaders,HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable("bookId") long id, @RequestBody Book book) {
        bookService.updateBookById(id,book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
