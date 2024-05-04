package nexign_project_maven.cdr_service.simulation;

import nexign_project_maven.cdr_service.cdr.CallRecordGenerator;
import nexign_project_maven.cdr_service.utils.SimulationClock;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class DataThreadManager {
    @EventListener(ApplicationReadyEvent.class)
    public synchronized void startDataGeneration() {

        int numberThreads = SimulationClock.getNumberThreads();
        ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
        SimulationClock.initialize();

        // Запуск задач в отдельных потоках
        for (int i = 0; i < numberThreads; i++) {
            executor.submit(() -> {
                while (SimulationClock.simulationActive()) {
                    try {
                        CallRecordGenerator.createCall();
                        SimulationClock.advanceTime();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        executor.shutdown();
    }
}