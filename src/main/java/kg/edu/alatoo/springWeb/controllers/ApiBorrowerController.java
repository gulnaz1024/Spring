package kg.edu.alatoo.springWeb.controllers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


import kg.edu.alatoo.springWeb.modules.Borrower;
import kg.edu.alatoo.springWeb.services.BorrowerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/borrower")
@RequiredArgsConstructor
@Slf4j
public class ApiBorrowerController {

    private final BorrowerService borrowerService;

    @GetMapping
    public List<Borrower> allBorrowers() {
        log.debug("Getting all product"); // use like this
        return borrowerService.findAll();
    }

    @PatchMapping( "{borrowerId}")
    public ResponseEntity<?> updatePatchById(@PathVariable("borrowerId") long borrowerId, @RequestBody Borrower borrower) {
        log.debug("Patch product with id " + borrowerId + ": "  + borrower);
        borrowerService.patchBorrowerById(borrowerId,borrower);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping( "{borrowerId}")
    public Borrower getBorrowerById(@PathVariable("borrowerId") long borrowerId) {
        return borrowerService.findById(borrowerId);
    }

    @DeleteMapping("{borrowerId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("borrowerId") long borrowerId) {
        borrowerService.deleteById(borrowerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> createBorrower(@RequestBody Borrower borrower) {
        Borrower savedBorrower = borrowerService.saveBorrower(borrower);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location","/api/v1/borrower/" + savedBorrower.getId());
        return new ResponseEntity<>(responseHeaders,HttpStatus.CREATED);
    }

    @PutMapping("/{borrowerId}")
    public ResponseEntity<?> updateBorrower(@PathVariable("borrowerId") long borrowerId, @RequestBody Borrower borrower) {
        borrowerService.updateBorrowerById(borrowerId,borrower);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
