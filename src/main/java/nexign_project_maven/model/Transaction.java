package nexign_project_maven.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Transaction {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String call_type;
    private String subscriber_number;
    private String contact_number;
    private Long start_time;
    private Long end_time;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCall_type() {
        return call_type;
    }

    public void setCall_type(String transactionType) {
        this.call_type = transactionType;
    }

    public String getSubscriber_number() {
        return subscriber_number;
    }

    public void setSubscriber_number(String subscriberPhoneNumber) {
        this.subscriber_number = subscriberPhoneNumber;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contactPhoneNumber) {
        this.contact_number = contactPhoneNumber;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long startTime) {
        this.start_time = startTime;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long endTime) {
        this.end_time = endTime;
    }
}
