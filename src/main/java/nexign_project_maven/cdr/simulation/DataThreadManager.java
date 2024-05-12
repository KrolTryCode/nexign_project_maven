package nexign_project_maven.cdr.simulation;

import nexign_project_maven.cdr.cdr_service.CallRecordGenerator;
import nexign_project_maven.cdr.utils.SimulationClock;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static nexign_project_maven.cdr.cdr_service.CdrGenerator.handlerExistsFiles;
import static nexign_project_maven.cdr.cdr_service.CdrGenerator.useFileGenerator;

/**
 * Component responsible for managing the simulation data generation threads.
 * This class listens to the application's readiness and starts the data generation process
 * based on the configuration provided.
 */
@Component
public class DataThreadManager {
    /**
     * Initiates the data generation process when the application is fully ready.
     * It decides the mode of operation based on the configuration - either generating new CDR data
     * or processing existing files.
     */
    @EventListener(ApplicationReadyEvent.class)
    public synchronized void startDataGeneration() {

        if (useFileGenerator) {
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
        } else {
            handlerExistsFiles();
        }
    }
}