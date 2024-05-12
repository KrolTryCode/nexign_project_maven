package nexign_project_maven.cdr.controller;

import nexign_project_maven.cdr.model.Subscriber;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static nexign_project_maven.cdr.cdr_service.CallRecordGenerator.subscriberRepository;


@RestController
@RequestMapping("/subscribers")
public class SubscriberController {
    @GetMapping
    public List<Subscriber> getSubscribers() {
        return subscriberRepository.findAll();
    }
}
