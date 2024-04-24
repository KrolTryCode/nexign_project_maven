package nexign_project_maven.cdr;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class CdrGenerator {
    private static int fileCount = 0; // номер текущего файла
    private static int lineCount = 0; // количество строк в текущем файле
    private static final int MAX_LINES = 10; // максимальное количество строк в файле
    private static final String CDR_DIRECTORY = "./CDR";
    private static FileWriter currentWriter; // текущий файловый поток

    public static synchronized void generateCdr(String data) {
        try {
            File directory = new File(CDR_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdir();
            }

            if (currentWriter == null || lineCount >= MAX_LINES) {
                if (currentWriter != null) {
                    currentWriter.close();
                }
                File file = new File(CDR_DIRECTORY + "/cdr_" + fileCount + ".txt");
                if (file.exists() && lineCount >= MAX_LINES) {
                    fileCount++;
                    file = new File(CDR_DIRECTORY + "/cdr_" + fileCount + ".txt"); // переход к новому файлу
                }
                currentWriter = new FileWriter(file, true); // true - для дозаписи данных
                lineCount = countLinesInFile(file); // инициализация lineCount
            }

            currentWriter.write(data + System.lineSeparator());
            lineCount++;
            currentWriter.flush(); // гарантируем запись данных на диск
        } catch (IOException e) {
            System.out.println("Error writing to CDR file: " + e.getMessage());
        }
    }

    private static int countLinesInFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int lines = 0;
            while (reader.readLine() != null) lines++;
            return lines;
        }
    }
}


