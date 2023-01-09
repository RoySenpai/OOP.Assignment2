package Ex2_2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {

    /**
     * Minimal pool size for the ThreadPoolExecutor object.
     * By default, half of the number of processors available to the Java virtual machine..
     */
    private static final int MINIMUM_POOLSIZE = (Runtime.getRuntime().availableProcessors()/2);

    /**
     * Maximal pool size for the ThreadPoolExecutor object.
     * By default, half of the number of processors available to the Java virtual machine.
     */
    private static final int MAXIMUM_POOLSIZE = (Runtime.getRuntime().availableProcessors()-1);

    /**
     * When the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait
     * for new tasks before terminating.
     * By default, it's 300ms.
     */
    private static final long KEEP_ALIVE_TIME = 300;

    /**
     * The time unit for the KEEP_ALIVE_TIME.
     * Uses the TimeUnit enum.
     * By default, it's in milliseconds.
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    /**
     * A constant to be used for default priority.
     */
    private static final int MIN_PRIORITY = 10;

    /**
     * Holds the priorities list of all the queued threads.
     * Used for ThreadPoolExecutor.
     */
    public int[] prioritiesList;


    /**
     * An empty constructor that initialize a ThreadPoolExecutor.
     */
    public CustomExecutor() {
        super(
                MINIMUM_POOLSIZE,
                MAXIMUM_POOLSIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new PriorityBlockingQueue<>()
        );

        prioritiesList = new int[MIN_PRIORITY];
    }

    /**
     * Submits a task to the ThreadPoolExecutor.
     * @param taskToDo  A Task to execute.
     * @return a Future representing pending completion of the task.
     * @throws NullPointerException if a null task is given
     */
    public <V> Future<V> submit(Task<V> taskToDo) {
        if (taskToDo == null)
            throw new NullPointerException();

        ++prioritiesList[taskToDo.getPriority() - 1];
        TaskAdapter<V> taskAdapter = new TaskAdapter<>(taskToDo, taskToDo.getPriority());
        super.execute(taskAdapter);
        return taskAdapter;
    }

    /**
     * Submits a task to the ThreadPoolExecutor, with given priority.
     * @param taskToDo  A Task to execute.
     * @param typeOfTheTask TaskType object that represents the priority of the task.
     * @return a Future representing pending completion of the task.
     */
    public <V> Future<V> submit(Callable<V> taskToDo, TaskType typeOfTheTask) {
        return this.submit(Task.createTask(taskToDo, typeOfTheTask));
    }

    /**
     * Submits a task to the ThreadPoolExecutor, without priority.
     * @param taskToDo A Task to execute.
     * @return a Future representing pending completion of the task.
     */
    public <V> Future<V> submit(Callable<V> taskToDo) {
        return this.submit(Task.createTask(taskToDo));
    }

    /**
     * This method executed before a task have been executed.
     * @param r the runnable
     * @param t the thread itself
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        --prioritiesList[((TaskAdapter) r).getPriority()-1];
    }

    /**
     * Shutdowns the ThreadPoolExecutor.
     */
    public void gracefullyTerminate() {
        try
        {
            super.shutdown();
            while (!super.awaitTermination(10, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the maximum priority task that the
     * ThreadPoolExecutor executed.
     * @return maximum priority task.
     */
    public int getCurrentMax() {
        for (int i = 0; i < MIN_PRIORITY; ++i)
        {
            if (this.prioritiesList[i] > 0)
                return i+1;
        }

        return MIN_PRIORITY;
    }

    /**
     * Calculates hashcode for this object.
     * @return  a hashcode.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Checks if the given object is equal to this object.
     * @param obj given object.
     * @return true if yes, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Returns the number of tasks that are waiting to be executed.
     * @return number of tasks that are waiting to be executed.
     */
    @Override
    public String toString() {
        return "Queue size: " + super.getQueue().size() + "; Queue: " + super.getQueue().toString() + "; Current max priority: " + getCurrentMax() + "; " + Arrays.toString(prioritiesList);
    }
}
