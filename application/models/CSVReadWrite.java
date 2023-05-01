package application.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVReadWrite {
    private File csvFile;
    private FileWriter csvWriter;
    BufferedReader csvReader;

    public CSVReadWrite() throws IOException {
        createFile();
    }

    private void createFile() throws IOException {
        this.csvFile = new File("tasks.csv");
        this.csvWriter = new FileWriter(this.csvFile, true);
        this.csvReader = new BufferedReader(new FileReader(this.csvFile));
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
    }

    public String[][] readFromCSV() throws IOException {
        String row;
        String[][] data = new String[10][3];
        int i = 0;

        while ((row = csvReader.readLine()) != null) {
            String[] line = row.split(",");
            for (int j = 0; j < line.length; j++) {
                String removedQuotes = line[j].substring(1, line[j].length() - 1);
                data[i][j] = removedQuotes;
            }
            i++;
        }
        cleanUp();
        return data;
    }

    public void updateCSV(Task oldTask, Task newTask) throws IOException {
    
        String csvBody[][] = readFromCSV();

        //Searching the csv contents for row that matches the old task
        for(int i = 0; i < csvBody.length; i++) {

            if(csvBody[i][0].equals(oldTask.getTitle()) &&
                csvBody[i][1].equals(oldTask.getDescription()) &&
                csvBody[i][2].equals(oldTask.getDueDate())) {

                //If a match is found, grab the row and replace it with the new task
                csvBody[i][0] = newTask.getTitle();
                csvBody[i][1] = newTask.getDescription();
                csvBody[i][2] = newTask.getDueDate();

                break;
            }
        }

        //Create a new file that contains the new data
        cleanUp();
        clearFile();

        for(int i = 0; i <= csvBody.length; i++) {
            Task temp = new Task(csvBody[i][0], csvBody[i][1], csvBody[i][2]);

            if(temp.getTitle() == null) {
                break;
            }
            writeToCSV(temp);
        }
        cleanUp();
    }

    private void clearFile() throws IOException{
        this.csvFile.delete();
        createFile();
    }

    public void cleanUp() throws IOException{
        this.csvWriter.close();
        this.csvReader.close();
        
    }
}

