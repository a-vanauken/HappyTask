package application.models;

public class Task {

    private String title;
    private String description;
    private String areaPath;
    private String dueDate;

    public Task(String title, String description, String areaPath, String dueDate) {
        setTitle(title);
        setDescription(description);
        setAreaPath(areaPath);
        setDueDate(dueDate);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAreaPath() {
        return this.areaPath;
    }

    public String getDueDate() {
        return this.dueDate;
    }
}
