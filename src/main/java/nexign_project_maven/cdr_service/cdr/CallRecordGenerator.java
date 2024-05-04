package nexign_project_maven.cdr_service.cdr;

import nexign_project_maven.cdr_service.utils.SimulationClock;
import nexign_project_maven.cdr_service.model.Subscriber;
import nexign_project_maven.cdr_service.model.Transaction;
import nexign_project_maven.cdr_service.repository.SubscriberRepository;
import nexign_project_maven.cdr_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;


@Component
public class CallRecordGenerator {

    private static SubscriberRepository subscriberRepository;
    private static TransactionRepository transactionRepository;
    @Autowired
    public CallRecordGenerator(SubscriberRepository subscriberRepository, TransactionRepository transactionRepository) {
        CallRecordGenerator.subscriberRepository = subscriberRepository;
        CallRecordGenerator.transactionRepository = transactionRepository;
    }

    //возможно добавить логику чтобы пока один абонент занят, ему не могли звонить?
    public static void createCall() throws InterruptedException {
        List<Subscriber> subscribers = subscriberRepository.findAll();

        Random random = new Random();
        if (subscribers.size() > 1) {
            Subscriber subscriberNumber = subscribers.get(random.nextInt(subscribers.size()));
            Subscriber contactNumber = subscribers.get(random.nextInt(subscribers.size()));
            while (subscriberNumber.getId().equals(contactNumber.getId())) {
                contactNumber = subscribers.get(random.nextInt(subscribers.size()));
            }

            Transaction transaction = new Transaction();
            transaction.setCall_type(random.nextBoolean() ? "01" : "02");
            transaction.setSubscriber_number(subscriberNumber.getPhoneNumber());
            transaction.setContact_number(contactNumber.getPhoneNumber());
            transaction.setStart_time(SimulationClock.getCurrentTime());
            transaction.setEnd_time(transaction.getStart_time() + SimulationClock.getCallTime());

            transactionRepository.save(transaction);
        }
    }
}