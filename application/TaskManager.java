package application;

import application.Task.*;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public final class TaskManager {

    private static ArrayList<Task> Tasks = new ArrayList<>();

    private static int nextId = 0;
    
    public static int GetNumberOfTasks(){
        return Tasks.size();
    }

    public static Task GetTaskAtIndex(int index){
        return Tasks.get(index);
    }

    public static void init() {
        Tasks = new ArrayList<>(Tasks);

        try {
            Load();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void DeleteTask(int Id){
        Task taskToDelete = GetTaskOfId(Id);
        Tasks.remove(taskToDelete);

        try {
            Save();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void UpdateTask(int id, String title, String description, String dueDate, Column todo) {

        Task task = GetTaskOfId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setColumn(todo);

        try {
            Save();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static int AddTask(String title, String description, String dueDate, Column column){
        Task task = new Task(nextId++, title, description, dueDate, column);
        Tasks.add(task);

        return task.id;
    }


    public static Task GetTaskOfId(int Id){
        return Tasks.get(Id);
    }


    public static void Save() throws IOException {
        
        File file = new File("tasks.csv");
        file.delete();
        file.createNewFile();
        FileWriter csvWriter = new FileWriter(file, true);

        for(int i = 0; i < Tasks.size(); i++) {
            csvWriter.append("\"");
            csvWriter.append(Tasks.get(i).getTitle().replaceAll("\"","\"\""));
            csvWriter.append("\"");
            csvWriter.append(',');
            csvWriter.append("\"");
            csvWriter.append(Tasks.get(i).getDescription().replaceAll("\"","\"\""));
            csvWriter.append("\"");
            csvWriter.append(',');
            csvWriter.append("\"");
            csvWriter.append(Tasks.get(i).getDueDate().replaceAll("\"","\"\""));
            csvWriter.append("\"");
            csvWriter.append(',');
            csvWriter.append("\"");
            csvWriter.append((Tasks.get(i).getColumn()).toString().replaceAll("\"","\"\""));
            csvWriter.append("\"");

            if(i++ != Tasks.size()) {
                csvWriter.append(',');
            }
            csvWriter.append("\n"); 
        }
        csvWriter.close();
    }


    private static void Load() throws IOException {
        File file = new File("tasks.csv");
        file.createNewFile();
        
        BufferedReader csvReader  = new BufferedReader(new FileReader(file));
        String row;
        String[] data = new String[4];

        while ((row = csvReader.readLine()) != null) {
            String[] line = row.split(",");
            for (int i = 0; i < line.length; i++) {
                String removedQuotes = line[i].substring(1, line[i].length() - 1);
                data[i] = removedQuotes;
            }

            AddTask(data[0], data[1], data[2], Column.valueOf(data[3]));
        }   
        csvReader.close();
    }
}
