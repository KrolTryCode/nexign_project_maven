package nexign_project_maven.cdr_service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private String phoneNumber;

    public Long getId() {
        return id;
    }
}