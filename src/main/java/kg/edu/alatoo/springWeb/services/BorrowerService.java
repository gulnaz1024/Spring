package kg.edu.alatoo.springWeb.services;

import kg.edu.alatoo.springWeb.modules.Borrower;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public interface BorrowerService {

    List<Borrower> findAll();

    Borrower saveBorrower(Borrower book);


    void updateBorrowerById(long id, Borrower Borrower);

    Borrower findById(long id);

    void deleteById(long id);

    void patchBorrowerById(long id, Borrower Borrower);
}
