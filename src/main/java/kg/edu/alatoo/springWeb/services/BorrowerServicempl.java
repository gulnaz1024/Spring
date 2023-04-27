package kg.edu.alatoo.springWeb.services;

import kg.edu.alatoo.springWeb.modules.Borrower;
import kg.edu.alatoo.springWeb.repos.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BorrowerServicempl implements BorrowerService{
    private final Map<Long, Borrower> borrowerData = new HashMap<>();

    private final BorrowerRepository borrowerRepository;
    public BorrowerServicempl(BorrowerRepository borrowerRepository) {
//        Book prod1 = Book.builder()
//                .title("Product 1")
//                .author("Product 1 Description")
//                .publisher("2.52")
//                .isbn("123456")
//                .build();
//
//        borrowerData.put(prod1.getId(), prod1);

        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public List<Borrower> findAll() {

        return borrowerRepository.findAll();
    }

    @Override
    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @Override
    public void updateBorrowerById(long id, Borrower borrower) {
        Borrower existed = borrowerRepository.findBorrowerById(id);
        existed.setName(borrower.getName());
        existed.setEmail(borrower.getEmail());
        existed.setPhone_number(borrower.getPhone_number());
        borrowerRepository.save(existed);
    }


    @Override
    public Borrower findById(long id) {
        return borrowerRepository.findBorrowerById(id);
    }

    @Override
    public void deleteById(long id) {
        borrowerRepository.deleteById(id);

    }



    @Override
    public void patchBorrowerById(long id, Borrower borrower) {
        Borrower existed = borrowerRepository.findBorrowerById(id);
        if (StringUtils.hasText(borrower.getName())) {
            existed.setName(borrower.getName());
        }
        if (StringUtils.hasText(borrower.getEmail())) {
            existed.setEmail(borrower.getEmail());
        }
        if (StringUtils.hasText(borrower.getPhone_number())) {
            existed.setPhone_number(borrower.getPhone_number());
        }
        borrowerRepository.save(existed);
    }


}
