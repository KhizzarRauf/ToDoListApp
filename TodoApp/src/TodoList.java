import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main Class of the TodoApp
 * Display the user menu and enable to user to todolist operations
 */
public class TodoList {
    private ListAdt<Task> taskList = new ArrayList<>();

    // Status of TodoList items
    private static final String ALL_TASKS = "All";
    private static final String COMPLETED_TASKS = "Completed";
    private static final String REM_TASKS = "Incomplete";


    /**
     * Method to show Main Menu
     * @param totalTasks - number of total tasks
     * @param doneTasks - number of completed tasks
     */
    private void showMainMenu(int totalTasks, int doneTasks) {
        System.out.println();
        System.out.println("**************************************");
        System.out.println("              MAIN MENU               ");
        System.out.println("**************************************");
        System.out.format("Total Tasks [%d] | Completed Tasks[%d] | Rem Tasks[%d]\n", totalTasks, doneTasks,
                totalTasks-doneTasks);
        System.out.println("1. Add a New Task");
        System.out.println("2. Show All Tasks");
        System.out.println("3. Show All Completed Tasks");
        System.out.println("4. Show All Incomplete Tasks");
        System.out.println("5. Mark Task as Done");
        System.out.println("6. Remove a Task");
        System.out.println("7. Find a Task by Name");
        System.out.println("8. Check if Task Exits");
        System.out.println("9. Quit");
        System.out.print("Please choose an option[1-9]: ");
    }

    /**
     * Method to show task details for given task List and Task Type
     * @param tasks - List of tasks
     * @param taskType - Type of task to be displayed
     */
    private void showTasks(ListAdt<Task> tasks, String taskType) {
        System.out.format("--- Showing %s Tasks[%d] ---\n", taskType, tasks.size());
        if(tasks.size() > 0) {
            String format = "%-8s %-35s %-15s %-15s";
            System.out.println("-------------------------------------------------------------------");
            System.out.println(String.format(format, "Index", "Name", "Due Date", "Done"));
            System.out.println("-------------------------------------------------------------------");
            int index = 1;
            for (int i=0; i<tasks.size(); i++) {
                Task task = tasks.getItem(i);
                System.out.println(String.format(format, index, task.getName(), task.getDueDate(), task.isDone() ? "Yes" : "No"));
                index++;
            }
        } else {
            switch (taskType) {
                case ALL_TASKS : System.out.println("No Task added yet. Please add a task");break;
                case COMPLETED_TASKS: System.out.println("No Tasks Completed yet");break;
                case REM_TASKS: System.out.println("No Tasks Remaining");break;
            }
        }
    }

    /**
     * Show details of a given task
     * @param task
     */
    private void showTask(Task task) {
        System.out.println("--- Task Details ---");
        System.out.println(task);
    }

    /**
     * Method the validate task details
     * @param taskName - name of the task
     * @param taskDate - due date of the task
     * @throws DateTimeException - if invalid date
     * @throws NullPointerException - if invalid task name
     */
    private void validateTask(String taskName, LocalDate taskDate) throws DateTimeException, NullPointerException {
        if(taskDate.compareTo(LocalDate.now()) < 0)
            throw new DateTimeException("Older than current date is used");
        if(taskName.isBlank()) throw new NullPointerException("Name for the task is required field");
    }

    /**
     * Method to add new task to the list
     */
    private void addTask() {
        Scanner scanner = new Scanner(System.in);
        try {
            Task task = new Task();
            System.out.println("--- Enter Task Details ---");
            System.out.print("Enter Task Name: ");
            String taskName = scanner.nextLine();
            System.out.print("Enter Task Due Date[yyyy-mm-dd]: ");
            String dueDate = scanner.nextLine();
            LocalDate taskDate = LocalDate.parse(dueDate);
            validateTask(taskName, taskDate);
            task.setName(taskName);
            task.setDueDate(taskDate);
            taskList.addItem(task);
            System.out.println("SUCCESS: Task Added");
        } catch (DateTimeParseException dte) {
            System.out.format("ERROR: Invalid Due Date added for the task [%s]\n", dte.getMessage());
        } catch (Exception ex) {
            System.out.format("ERROR: Task Addition failed[%s]\n", ex.getMessage());
        }
    }

    /**
     * Method to remove a task from the list
     */
    private void removeTask() {
        Scanner scanner = new Scanner(System.in);
        if(!taskList.isEmpty()) {
            try {
                while (true) {
                    System.out.println("--- Remove a Task ---");
                    showTasks(taskList, ALL_TASKS);
                    System.out.format("%-8s %-35s\n", taskList.size() + 1, "Back to Main Menu");
                    int taskRange = taskList.size() + 1;
                    System.out.format("Please choose an option[%s]: ", "1-" + taskRange);
                    int selectedIndex = Integer.parseInt(scanner.nextLine());

                    // Return to main menu
                    if (selectedIndex == taskList.size() + 1) return;

                    if (selectedIndex <= 0 || selectedIndex > taskList.size()) {
                        throw new IndexOutOfBoundsException("Invalid Task Index Select to be removed");
                    }

                    System.out.print("Are you sure you want to remove (Y/N): ");
                    String confirmation = scanner.nextLine();
                    if ("Y".equalsIgnoreCase(confirmation)) {
                        taskList.removeItem(selectedIndex - 1);
                        System.out.println("SUCCESS: Task Removed");
                        break;
                    }
                }

            } catch (Exception ex) {
                System.out.format("ERROR: Task Removal failed[%s]", ex.getMessage());
            }
        } else {
            System.out.println("No Task added yet. Please add a task");
        }
    }

    /**
     * Method to mark a task as Done or completed
     */
    private void completeTask() {
        Scanner scanner = new Scanner(System.in);
        ListAdt<Task> incompleteTaskList = getIncompleteTaskList();
        if(!incompleteTaskList.isEmpty()) {
            try {
                while (true) {
                    System.out.println("--- Complete a Task ---");
                    showTasks(incompleteTaskList, REM_TASKS);
                    System.out.format("%-8s %-35s\n", incompleteTaskList.size() + 1, "Back to Main Menu");
                    int taskRange = incompleteTaskList.size() + 1;
                    System.out.format("Please choose an option[%s]: ", "1-" + taskRange);
                    int selectedIndex = Integer.parseInt(scanner.nextLine());

                    // Return to main menu
                    if (selectedIndex == incompleteTaskList.size() + 1) return;

                    if (selectedIndex <= 0 || selectedIndex > incompleteTaskList.size()) {
                        throw new IndexOutOfBoundsException("Invalid Task Index Select to be completed");
                    }

                    System.out.print("Are you sure you want to mark this task done (Y/N): ");
                    String confirmation = scanner.nextLine();
                    if ("Y".equalsIgnoreCase(confirmation)) {
                        Task task = incompleteTaskList.getItem(selectedIndex - 1);
                        task.setDone(true);
                        System.out.println("SUCCESS: Task Marked Done");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.format("ERROR: Task Completion Failed[%s]\n", ex.getMessage());
            }
        } else {
            System.out.println(taskList.size() > 0 ? "All Tasks are already Marked as Done" : "No Task added yet. " +
                    "Please add a task");
        }
    }

    /**
     * Method to find a task by a name
     */
    private void findTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Find a Task by Name ---");
        System.out.print("-Please enter the task name: ");
        String searchName = scanner.nextLine();
        for (int i=0; i< taskList.size(); i++) {
            Task task = taskList.getItem(i);
            if(searchName.equalsIgnoreCase(task.getName())) {
                showTask(task);
                return;
            }
        }
        System.out.format("No task found by this name[%s]\n", searchName);
    }

    /**
     * Method to check of the task exist or not
     */
    private void checkIfTaskExits() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Find a Task by Name ---");
        System.out.print("-Please enter the task name: ");
        String searchName = scanner.nextLine();
        if(taskList.contains(new Task(searchName, LocalDate.now()))) {
            System.out.format("Task with this name[%s] exists\n", searchName);
        } else {
            System.out.format("No task found by this name[%s]\n", searchName);
        }
    }

    /**
     * Method to return the list of completed items
     * @return List of compeleted items
     */
    private ListAdt<Task> getCompletedTasks() {
        ListAdt<Task> completedTasks = new ArrayList<>();
        for (int i=0; i< taskList.size(); i++) {
            Task task = taskList.getItem(i);
            if(task.isDone()) {
                completedTasks.addItem(task);
            }
        }
        return completedTasks;
    }

    /**
     * Method to get a list of all incomplete task items
     * @return incomplete list items
     */
    private ListAdt<Task> getIncompleteTaskList() {
        ListAdt<Task> incompleteTasks = new ArrayList<>();
        for (int i=0; i< taskList.size(); i++) {
            Task task = taskList.getItem(i);
            if(!task.isDone()) {
                incompleteTasks.addItem(task);
            }
        }
        return incompleteTasks;
    }

    /**
     * Driver function of the class to enable user interact with the application
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO TODO APP");
        try {
            while (true) {
                showMainMenu(taskList.size(), getCompletedTasks().size());
                String option = scanner.nextLine();
                System.out.println();

                switch (option) {
                    case "1": addTask(); break;
                    case "2": showTasks(taskList, ALL_TASKS); break;
                    case "3": showTasks(getCompletedTasks(), COMPLETED_TASKS);break;
                    case "4": showTasks(getIncompleteTaskList(), REM_TASKS);break;
                    case "5": completeTask();break;
                    case "6": removeTask();break;
                    case "7": findTask();break;
                    case "8": checkIfTaskExits();break;
                    case "9": System.out.println("Exiting Application...");System.exit(0);break;
                    default: System.out.println("Invalid Option Selected. Please try again");
                }
            }
        } catch (Exception ex) {
            System.out.format("ERROR: Issue in the TODO APP [%s]", ex.getMessage());
        }
    }

    /**
     * Main function of the class
     * @param args
     */
    public static void main(String args[]) {
        TodoList todoList = new TodoList();
        todoList.run();
    }
}
