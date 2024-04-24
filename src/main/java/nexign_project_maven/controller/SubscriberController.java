package nexign_project_maven.controller;

import nexign_project_maven.model.Subscriber;
import nexign_project_maven.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @GetMapping
    public List<Subscriber> getSubscribers() {
        return subscriberRepository.findAll();
    }
}
