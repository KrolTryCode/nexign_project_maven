package nexign_project_maven.cdr.cdr_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.*;

import static nexign_project_maven.cdr.utils.Constants.*;

/**
 * Service for generating CDR (Call Detail Record) files and sending them to Kafka.
 * This service supports both file generation and direct Kafka submission.
 */
@Service
public class CdrGenerator {

    private static FileWriter currentWriter; // текущий файловый поток
    private static File currentFile; // текущий файл
    private static int fileCount = 0; // номер текущего файла
    private static int lineCount = 0; // количество строк в текущем файле

    private static KafkaTemplate<String, String> kafkaTemplate;
    public static boolean useFileGenerator;

    /**
     * Constructs a new CdrGenerator with specified Kafka template and configuration.
     *
     * @param kafkaTemplate the Kafka template for message sending
     * @param useFileGenerator flag to determine if file generation is used
     */
    @Autowired
    public CdrGenerator(KafkaTemplate<String, String> kafkaTemplate, @Value("${app.use-file-generator}") boolean useFileGenerator) {
        CdrGenerator.kafkaTemplate = kafkaTemplate;
        CdrGenerator.useFileGenerator = useFileGenerator;
    }


    /**
     * Generates CDR files and writes data
     *
     * @param data the CDR data to be written to file
     */
    public static synchronized void generateCdrFiles(String data) {
        try {
            ensureDirectoryExists();
            prepareFile();
            writeDataToFile(data);
        } catch (IOException e) {
            System.err.println("Error writing to CDR file: " + e.getMessage());
        }
    }

    /**
     * Ensures that the directory for CDR files exists.
     */
    private static void ensureDirectoryExists() {
        File directory = new File(CDR_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Prepares a new file for writing CDR data.
     *
     * @throws IOException if an I/O error occurs
     */
    private static void prepareFile() throws IOException {
        if (currentWriter == null || lineCount >= MAX_LINES) {
            closeAndSendCurrentFile();
            fileCount++;
            currentFile = new File(CDR_DIRECTORY + CDR_NAMES + fileCount + ".txt");
            currentWriter = new FileWriter(currentFile, false);
            lineCount = 0;
        }
    }

    /**
     * Closes the current CDR file and sends its content to Kafka.
     */
    private static void closeAndSendCurrentFile() {
        if (currentWriter != null) {
            try {
                currentWriter.close();
            } catch (IOException e) {
                System.err.println("Error closing CDR file: " + e.getMessage());
            }
            sendFileToKafka(currentFile);
            currentWriter = null;
        }
    }

    /**
     * Generates CDR files and writes data to them or sends directly to Kafka based on configuration.
     *
     * @param data the CDR data to be written to file or sent to Kafka
     */
    private static void sendFileToKafka(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append(System.lineSeparator());
            }
            kafkaTemplate.send(CDR_TOPIC, fileContent.toString());
        } catch (IOException e) {
            System.err.println("Error reading CDR file for Kafka: " + e.getMessage());
        }
    }

    /**
     * Writes the provided data to the current CDR file.
     *
     * @param data the data to write
     * @throws IOException if an I/O error occurs during writing
     */
    private static void writeDataToFile(String data) throws IOException {
        currentWriter.write(data + System.lineSeparator());
        lineCount++;
        currentWriter.flush();
    }

    /**
     * Handles existing files in the CDR directory, sending each to Kafka.
     */
    public static void handlerExistsFiles() {
        File directory = new File(CDR_DIRECTORY);
        File[] files = directory.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                sendFileToKafka(file);
            }
        }
    }
}
