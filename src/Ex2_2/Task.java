package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Task is a custom implementation of the FutureTask class in Java.
 * It accepts a callable object and a task type.
 * It also implements the Comparable interface, and the compareTo method compares tasks based on their priority values.
 *
 * @param <T> the type of the task
 */
public class Task<T> extends FutureTask<T> implements Comparable<Runnable> {
    private Callable <T> operation;
    private TaskType type;

    /**
     * Constructor for Task
     *
     * @param operation the callable object to be executed by the task
     * @param type      the type of the task
     */
    public Task(Callable<T> operation, TaskType type) {
        super(operation);
        this.operation = operation;
        this.type = type;
    }

    /**
     * This method returns the type of the task
     *
     * @return the type of the task
     */
    public TaskType getType() {
        return this.type;
    }

    /**
     * This is a factory method used to create a task with a callable object and a default task type
     *
     * @param operation the callable object to be executed by the task
     * @param <T>       the type of the task
     * @return the created task
     */
    public static <T> Task<T> createTask(Callable<T> operation) {
        Task task = new Task<T>(operation, TaskType.OTHER);
        return task;
    }
    /**
     * This is a factory method used to create a task with a callable object and a task type
     *
     * @param operation the callable object to be executed by the task
     * @param type      the type of the task
     * @param <T>       the type of the task
     * @return the created task
     */
    public static <T> Task<T> createTask(Callable<T> operation, TaskType type) {
        return new Task<T>(operation, type);
    }

    /**
     * This method returns the callable object associated with the task
     *
     * @return the callable object associated with the task
     */
    public Callable <T> getOperation() {
        return operation;
    }

    /**
     * This method compares the task with another task based on their priority values.
     *
     * @param o the task to be compared with
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     */
    @Override
    public int compareTo(Runnable o) {
        Task task1 = (Task) o;
        Task task2 = (Task) this;
        int priority1 = task1.getType().getPriorityValue();
        int priority2 = task2.getType().getPriorityValue();
        return Integer.compare(priority1, priority2);
    }

    /**
     * This method generates a hash code for the task object
     *
     * @return the hash code for the task object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * This method compares the task object with another object
     *
     * @param obj the object to be compared with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (operation == null) {
            if (other.operation != null)
                return false;
        } else if (!operation.equals(other.operation))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    /**
     * This method returns a string representation of the task object
     *
     * @return the string representation of the task object
     */
    @Override
    public String toString() {
        return "Task [operation=" + operation + ", type=" + type + "]";
    }

}