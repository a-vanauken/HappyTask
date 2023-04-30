package application.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVReadWrite {
    private File csvFile;
    private FileWriter csvWriter;

    public CSVReadWrite() throws IOException {
        createFile();
    }

    private void createFile() throws IOException {
        this.csvFile = new File("tasks.csv");
        this.csvWriter = new FileWriter(this.csvFile, true);
    }

    public void writeToCSV(Task task) throws IOException {

        if(this.csvFile == null) {
            createFile();
        }

        String[] taskProperties = {task.getTitle(), task.getDescription(), task.getDueDate()};

        int i = 1;
        for (String property : taskProperties) {

            csvWriter.append("\"");
            csvWriter.append(property.replaceAll("\"","\"\""));
            csvWriter.append("\"");

            if (i++ != taskProperties.length) {
                this.csvWriter.append(',');
            }

        }
        this.csvWriter.append("\n");
        this.csvWriter.close();
    }
}



