package kg.edu.alatoo.springWeb.repos;

import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.modules.Borrower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface BorrowerRepository extends CrudRepository<Borrower, Long> {


    List<Borrower> findAll();


    Borrower findBorrowerById(long borrowerId);

    Borrower deleteById(long borrowerId);

    Borrower save(Borrower borrower);
}
