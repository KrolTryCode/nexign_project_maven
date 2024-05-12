package nexign_project_maven.cdr.controller;

import nexign_project_maven.cdr.model.Transaction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static nexign_project_maven.cdr.cdr_service.CallRecordGenerator.transactionRepository;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
