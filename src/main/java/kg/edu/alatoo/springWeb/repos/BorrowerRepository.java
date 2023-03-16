package kg.edu.alatoo.springWeb.repos;

import kg.edu.alatoo.springWeb.modules.Borrower;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BorrowerRepository extends CrudRepository<Borrower, Long> {

    Set<Borrower> findAll();
    Borrower findBorrowerById(long borrowerId);
}
