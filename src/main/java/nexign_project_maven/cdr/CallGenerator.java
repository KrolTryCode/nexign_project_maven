package nexign_project_maven.cdr;

import nexign_project_maven.model.Subscriber;
import nexign_project_maven.model.Transaction;
import nexign_project_maven.repository.SubscriberRepository;
import nexign_project_maven.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static nexign_project_maven.Main.currentTime;

@Service
public class CallGenerator {
    private static SubscriberRepository subscriberRepository;
    private static TransactionRepository transactionRepository;
    @Autowired
    public CallGenerator(SubscriberRepository subscriberRepository, TransactionRepository transactionRepository) {
        CallGenerator.subscriberRepository = subscriberRepository;
        CallGenerator.transactionRepository = transactionRepository;
    }


    //возможно добавить логику чтобы пока один абонент занят, ему не могли звонить?
    //зачем сюда time передавать, если если общая переменная
    public static void createCall() throws InterruptedException {
        List<Subscriber> subscribers = subscriberRepository.findAll();

        Random random = new Random();
        if (subscribers.size() > 1) {
            Subscriber subscriberOne = subscribers.get(random.nextInt(subscribers.size()));
            Subscriber subscriberTwo = subscribers.get(random.nextInt(subscribers.size()));
            while (subscriberOne.getId().equals(subscriberTwo.getId())) {
                subscriberTwo = subscribers.get(random.nextInt(subscribers.size()));
            }

            Transaction transaction = new Transaction();
            transaction.setCall_type(random.nextBoolean() ? "01" : "02");
            transaction.setSubscriber_number(subscriberOne.getPhoneNumber());
            transaction.setContact_number(subscriberTwo.getPhoneNumber());
            transaction.setStart_time(currentTime.get() / 1000);
            transaction.setEnd_time(transaction.getStart_time() + random.nextInt(3600));
            transactionRepository.save(transaction);

            //Thread.sleep(2345); //для проверки параллельности
        }
    }
}