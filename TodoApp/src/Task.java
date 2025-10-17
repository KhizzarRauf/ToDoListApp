import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class for TodoList Task Items
 */
public class Task {

    /**
     * Name or title of the task
     */
    private String name;

    /**
     * Flag to check if task is done or not
     */
    private boolean done;

    /**
     * Due date of the task
     */
    private LocalDate dueDate;

    /**
     * Default constructor
     */
    public Task() {
    }

    /**
     * Create a task object with given name and due date
     * @param name
     * @param dueDate
     */
    public Task(String name, LocalDate dueDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.done = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dueDate = LocalDate.parse(dueDate.format(dateTimeFormatter));
    }

    @Override
    public String toString() {
        return String.format("Name: %s" +
                "\nDue Date: %s" +
                "\nDone: %s\n", name, dueDate.toString(), done ? "Yes" : "No");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return name.equalsIgnoreCase(task.name);
    }
}
