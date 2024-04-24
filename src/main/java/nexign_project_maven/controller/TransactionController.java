package nexign_project_maven.controller;

import nexign_project_maven.model.Subscriber;
import nexign_project_maven.model.Transaction;
import nexign_project_maven.repository.SubscriberRepository;
import nexign_project_maven.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
