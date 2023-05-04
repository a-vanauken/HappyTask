package application;

import application.Task.*;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public final class TaskManager {

    private static ArrayList<Task> tasks = new ArrayList<>();

    private static int nextId = 0;
    
    public static int getNumberOfTasks(){
        return tasks.size();
    }

    public static Task getTaskAtIndex(int index){
        return tasks.get(index);
    }

    public static void init() {
        tasks = new ArrayList<>(tasks);

        try {
            load();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteTask(int Id){
        Task taskToDelete = getTaskOfId(Id);
        tasks.remove(taskToDelete);

        try {
            save();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateTask(int id, String title, String description, String dueDate, Column column) {

        Task task = getTaskOfId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setColumn(column);

        try {
            save();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static int addTask(String title, String description, String dueDate, Column column){
        Task task = new Task(nextId++, title, description, dueDate, column);
        tasks.add(task);

        return task.id;
    }


    public static Task getTaskOfId(int id){

        for (int i = 0; i < getNumberOfTasks(); i++) {
            if(tasks.get(i).getId() == id) {
                return tasks.get(i);
            }
        }
        return null;
    }


    public static void save() throws IOException {
        
        File file = new File("tasks.csv");
        file.delete();
        file.createNewFile();
        FileWriter csvWriter = new FileWriter(file, true);
        for(int i = 0; i < getNumberOfTasks(); i++) {
            csvWriter.append("\"");
            csvWriter.append(tasks.get(i).getTitle().replaceAll("\"","\"\""));
            csvWriter.append("\"");
            csvWriter.append(',');
            csvWriter.append("\"");
            csvWriter.append(tasks.get(i).getDescription().replaceAll("\"","\"\""));
            csvWriter.append("\"");
            csvWriter.append(',');
            csvWriter.append("\"");
            csvWriter.append(tasks.get(i).getDueDate().replaceAll("\"","\"\""));
            csvWriter.append("\"");
            csvWriter.append(',');
            csvWriter.append("\"");
            csvWriter.append((tasks.get(i).getColumn()).toString().replaceAll("\"","\"\""));
            csvWriter.append("\"");

            if(i != tasks.size()) {
                csvWriter.append(',');
            }
            csvWriter.append("\n"); 
        }
        csvWriter.close();
    }


    private static void load() throws IOException {
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

            addTask(data[0], data[1], data[2], Column.valueOf(data[3]));
        }   
        csvReader.close();
    }
}
