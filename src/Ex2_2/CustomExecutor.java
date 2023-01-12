package Ex2_2;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * CustomExecutor is a custom implementation of the ThreadPoolExecutor class in Java.
 * It creates a thread pool with a minimum of half of the number of cores on the system minus 1 and a maximum of the number of cores minus 1.
 * It uses a PriorityBlockingQueue to hold the tasks that are submitted to it.
 */
public class CustomExecutor extends ThreadPoolExecutor {
    private static final int numOfCores = Runtime.getRuntime().availableProcessors() - 1;
    private static final int minThreads = (numOfCores / 2) - 1;
    private static final int maxThreads = numOfCores - 1;
    public static PriorityBlockingQueue<Runnable> tasks = new PriorityBlockingQueue<>();


    private int currMax;
    private ArrayList<Integer> listOfPriority;

    /**
     * Constructor for CustomExecutor
     */
    public CustomExecutor() {
        super(minThreads, maxThreads, 300, TimeUnit.MILLISECONDS, tasks);
        currMax = 100;
        this.listOfPriority = new ArrayList<>(10);
    }

    /**
     * submit method is overridden to accept tasks of the custom Task class,
     * which is defined in another class in the package.
     *
     * @param task the task to be submitted
     * @param <T>  the type of the task
     * @return the future object of the task
     */
    public <T> Future<T> submit(Task<T> task) {
        currMax(task.getType().getPriorityValue());
        System.out.println(task.getType() + "   " + task.getType().getPriorityValue());
        if (task == null) throw new NullPointerException();
        super.execute(task);
        return task;
    }

    /**
     * This method is used to submit a callable task
     *
     * @param task the task to be submitted
     * @param <T>  the type of the task
     * @return the future object of the task
     */
    public <T> Future<T> submit(Callable<T> task) {
        return submit(Task.createTask(task));
    }

    /**
     * This method is used to submit a callable task with a task type
     *
     * @param task the task to be submitted
     * @param type the type of the task
     * @param <T>  the type of the task
     * @return the future object of the task
     */
    public <T> Future<T> submit(Callable<T> task, TaskType type) {
        Task<T> task1 = Task.createTask(task, type);
        return submit(task1);
    }


    /**
     * This method is used to gracefully terminate the thread pool
     */
    public void gracefullyTerminate() {
        super.shutdown();
    }

    /**
     * This method returns the current max priority value
     *
     * @return the current max priority value
     */
    public int getCurrentMax() {
        return this.currMax;
    }

    /**
     * This method is used to set the current max priority value
     *
     * @param priority the new priority value
     */
    private void currMax(int priority) {
        if (priority <= this.currMax) {
            this.currMax = priority;
        } else
            return;

    }

    /**
     * This method returns the max number of threads in the thread pool
     *
     * @return the max number of threads
     */
    public int getMaxThreads() {
        return maxThreads;
    }

    /**
     * This method returns the generic type of the task
     *
     * @param task the task for which the generic type is to be returned
     * @param <T>  the type of the task
     * @return the class object of the task's generic type
     */
    public <T> Class<T> getTaskGenericType(Task<T> task) {
        T result = (T) task.getType();
        return (Class<T>) result.getClass();
    }

    /**
     * This method is called before the task is executed.
     *
     * @param thread the thread that will execute the task
     * @param r      the task to be executed
     */
    @Override
    protected void beforeExecute(Thread thread, Runnable r) {
        if (r instanceof Task) {
            Task task = (Task) r;
            Class type = getTaskGenericType(task);
            Task<?> task1 = (Task<?>) r;
            task1.getType().getPriorityValue();

        }
    }
    /**
     * This method returns the hash code of the object
     *
     * @return the hash code of the object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currMax;
        result = prime * result + ((listOfPriority == null) ? 0 : listOfPriority.hashCode());
        return result;
    }

    /**
     * This method compares the object with another object
     *
     * @param obj the object to be compared with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomExecutor other = (CustomExecutor) obj;
        if (currMax != other.currMax)
            return false;
        if (listOfPriority == null) {
            if (other.listOfPriority != null)
                return false;
        } else if (!listOfPriority.equals(other.listOfPriority))
            return false;
        return true;
    }

/**
 * This method returns the string
**/
 @Override
    public String toString() {
        return "CustomExecutor [currMax=" + currMax + ", listOfPriority=" + listOfPriority + "]";
    }

}