package application;

public class Task {

    public int id;
    private String title;
    private String description;
    //private String areaPath;
    private String dueDate;
    private Column column;

    public enum Column {
        NEW,
        IN_PROGRESS,
        ON_HOLD,
        DONE
    }

    public Task(int id, String title, String description, String dueDate, Column column) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        //setAreaPath(areaPath);
        setDueDate(dueDate);
        setColumn(column);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public void setAreaPath(String areaPath) {
    //     this.areaPath = areaPath;
    // }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setColumn(Column column) {

        if(column == Column.NEW) { 
            this.column = Column.NEW;
        } else if(column == Column.IN_PROGRESS) {
            this.column = Column.IN_PROGRESS;
        } else if(column == Column.ON_HOLD) {
            this.column = Column.ON_HOLD;
        } else if(column == Column.DONE) {
            this.column = Column.DONE;
        } 
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    // public String getAreaPath() {
    //     return this.areaPath;
    // }

    public String getDueDate() {
        return this.dueDate;
    }

    public Column getColumn() {
        return this.column;
    }
}