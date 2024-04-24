package nexign_project_maven;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class Main {
    public static AtomicLong currentTime = new AtomicLong();

    // Рассчитываем время окончания симуляции
    private static final long oneYearInMillis = (365 * 24 * 60 * 60 * 1000L)/(365*24*60/2); // один год в миллисекундах, но пока что один день/24/60*2, за 2 минуты крч _)))
    public static  long endTime = System.currentTimeMillis() + oneYearInMillis;

    public static void main(String[] args) {
        currentTime.set(endTime - oneYearInMillis);//вынесити это куда-нибудь вообще отсюда, и все переменные выше

        final int nThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads); // Пул из n потоков

        // Запуск задач в отдельных потоках
        executor.submit(() -> {
            SpringApplication.run(Main.class, args);
        });

        executor.submit(() -> {
            try {
                MainRun.runThreads(executor, nThreads);
                executor.shutdown();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        // Закрытие ExecutorService по завершению всех задач
        //executor.shutdown();
    }
}