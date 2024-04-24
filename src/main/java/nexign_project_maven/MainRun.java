package nexign_project_maven;

import nexign_project_maven.cdr.CallGenerator;


import java.io.IOException;
import java.util.concurrent.ExecutorService;


import static nexign_project_maven.Main.currentTime;
import static nexign_project_maven.Main.endTime;

public class MainRun {
    public static synchronized void runThreads(ExecutorService executor, int nThread) throws IOException, InterruptedException {
     Thread.sleep(4000);//может можно избежать паузы. пока пусть будет

     // Создаем задачи для генерации звонков в каждом потоке
     for (int i = 0; i < nThread; i++) {
      executor.submit(() -> {
       while (currentTime.get() < endTime) {
           try {
               CallGenerator.createCall();
               currentTime.getAndAdd((long) (Math.random() * 12345));//вынести логику эту куда-нибудь в одно место
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }
      });
     }
    }
}