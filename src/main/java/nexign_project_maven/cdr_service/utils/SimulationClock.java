package nexign_project_maven.cdr_service.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static nexign_project_maven.cdr_service.utils.Constants.*;

public class SimulationClock {

    private static int randomCallDuration = 0;
    private static final AtomicLong currentTime = new AtomicLong();
    private static final long oneYearInMillis = (GENERATION_DURATION_IN_DAYS * 24 * 60 * 60);
    private static final long customCurrentTime = System.currentTimeMillis() / 1000;
    private static final long endTime = customCurrentTime + oneYearInMillis;
    private static final Random random = new Random();

    public static void initialize() {
        System.out.println(customCurrentTime);
        System.out.println(endTime);
        currentTime.set(customCurrentTime);
    }

    public static long getCurrentTime() {
        return currentTime.get();
    }

    public static void advanceTime() throws InterruptedException {
        currentTime.getAndAdd(random.nextInt(RANGE_DELTA_TIME));
        Thread.sleep(randomCallDuration + random.nextInt(PAUSE_BETWEEN_CALL));
    }

    public static long getCallTime() {
        randomCallDuration = random.nextInt(CALL_DURATION);
        return randomCallDuration;
    }

    public static int getNumberThreads() {
        return NUMBER_THREADS;
    }

    public static boolean simulationActive() {
        return getCurrentTime() < endTime;
    }
}