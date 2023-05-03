package application.controllers;

import application.Task;
import application.Task.*;
import application.TaskManager;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class TaskDialogController extends Dialog<Task> {

    @FXML DialogPane pane;

    @FXML BorderPane container;
    
    @FXML Label titleLabel;
    
    @FXML TextField titleField;

    @FXML Label stateLabel;

    @FXML ComboBox<String> stateOptions;
    
    // @FXML Label pathLabel;
    
    // @FXML ComboBox<String> pathOptions;
    
    @FXML GridPane header;
    
    @FXML Label dateLabel;

    @FXML DatePicker datePicker;
    
    @FXML Button saveButton;
    
    @FXML Button cancelButton;
    
    @FXML VBox footer;
    
    @FXML TextArea descriptionArea;

    @FXML ButtonType okButtonType;

    boolean editing;

    int taskId;


    public TaskDialogController(Window owner) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/application/fxml/TaskDialog.fxml"));
            loader.setController(this);
            DialogPane dialogPane = loader.load();
            dialogPane.setContent(container);
            stateOptions.getSelectionModel().select(0);

            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(true);
            setTitle("Add/Edit Task");
            setDialogPane(dialogPane);

            if(!editing){
                setDueDate(formatDate(LocalDate.now()));
            }

            setResultConverter(buttonType -> {

                if(!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return null;
                }

                Task task;

                if(editing) {
                    //Update existing task to reflect changes
                    task = TaskManager.GetTaskOfId(taskId);
                    TaskManager.UpdateTask(taskId,titleField.getText(), descriptionArea.getText(),formatDate(datePicker.getValue()), unformatState(stateOptions.getSelectionModel().getSelectedItem()));
                } else {
                    //Create a new task with the received values
                    int newId = TaskManager.AddTask(titleField.getText(), descriptionArea.getText(),formatDate(datePicker.getValue()), unformatState(stateOptions.getSelectionModel().getSelectedItem()));
                    task = TaskManager.GetTaskOfId(newId);
                }
                
                try {
                    TaskManager.Save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return task;
            });

            setOnShowing(dialogEvent -> Platform.runLater(() -> titleField.requestFocus()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatDate(LocalDate date) {
        //  2023-01-01 - > 01/01/2023 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate;
        if (date != null) {
            formattedDate = formatter.format(date);
        } else {
            formattedDate = "N/A";
        }
        return formattedDate;
    }

    private LocalDate unformatDate(String date) {
        //  01/01/2023 -> 2023-01-01
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        formatter.format(localDate);
        
        return localDate;
    }

    private Column unformatState(String state) {
        if(state.equals("To Do")) {
            return Column.TODO;
        } else if(state.equals("In Progress")) {
            return Column.IN_PROGRESS;
        } else if (state.equals("On Hold")) {
            return Column.ON_HOLD;
        } else if(state.equals("Done")) {
            return Column.DONE;
        } else {
            return null;
        }
    }

    public void setTitleField(String title) {
        this.titleField.setText(title);
    }

    public void setDescriptionArea(String description) {
        this.descriptionArea.setText(description);
    }

    public void setDueDate(String dueDate) {
        this.datePicker.setValue(unformatDate(dueDate));
    }

    public void setState(Column column) {
        if(column == Column.TODO) { 
            this.stateOptions.getSelectionModel().select(0);
        } else if(column == Column.IN_PROGRESS) {
            this.stateOptions.getSelectionModel().select(1);
        } else if(column == Column.ON_HOLD) {
            this.stateOptions.getSelectionModel().select(2);
        } else if(column == Column.DONE) {
            this.stateOptions.getSelectionModel().select(3);
        } else {
            System.out.println("Unable to set dialog state");
        }
    }
}
    