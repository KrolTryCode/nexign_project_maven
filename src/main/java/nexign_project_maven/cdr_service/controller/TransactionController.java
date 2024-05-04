package nexign_project_maven.cdr_service.controller;

import nexign_project_maven.cdr_service.model.Transaction;
import nexign_project_maven.cdr_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
