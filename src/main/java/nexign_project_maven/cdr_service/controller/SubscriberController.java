package nexign_project_maven.cdr_service.controller;

import nexign_project_maven.cdr_service.model.Subscriber;
import nexign_project_maven.cdr_service.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @GetMapping
    public List<Subscriber> getSubscribers() {
        return subscriberRepository.findAll();
    }
}
