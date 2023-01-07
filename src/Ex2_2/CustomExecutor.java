package Ex2_2;

import java.util.concurrent.*;

public class CustomExecutor {

    /* Some constants to be used. */

    /**
     * Minimal pool size for the ThreadPoolExecutor object.
     * By default, half of the number of processors available to the Java virtual machine..
     */
    public static final int MINIMUM_POOLSIZE = (Runtime.getRuntime().availableProcessors()/2);

    /**
     * Maximal pool size for the ThreadPoolExecutor object.
     * By default, half of the number of processors available to the Java virtual machine.
     */
    public static final int MAXIMUM_POOLSIZE = (Runtime.getRuntime().availableProcessors()/2);

    /**
     * When the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait
     * for new tasks before terminating.
     * By default, it's 300ms.
     */
    public static final long KEEP_ALIVE_TIME = 300;

    /**
     * The time unit for the KEEP_ALIVE_TIME.
     * Uses the TimeUnit enum.
     * By default, it's in milliseconds.
     */
    public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    public static final int DEFAULT_INIT_SIZE = 11;

    /* Actual */

    /**
     * The ThreadPoolExecutor object that this class uses.
     */
    private final ThreadPoolExecutor poolOfThreads = new ThreadPoolExecutor(
                MINIMUM_POOLSIZE,
                MAXIMUM_POOLSIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new PriorityBlockingQueue<>(DEFAULT_INIT_SIZE, new PriorityTaskComparator())
    );

    /**
     * Holds the maximum priority task that the
     * ThreadPoolExecutor executed.
     */
    private int currentMaxPriority = Integer.MIN_VALUE;

    /**
     * Submits a task to the ThreadPoolExecutor.
     * @param taskToDo  A Task to execute.
     * @return a Future representing pending completion of the task.
     */
    public <V> Future<V> submit(Task<V> taskToDo) {
        this.currentMaxPriority = Math.max(currentMaxPriority, taskToDo.getPriority());
        return this.poolOfThreads.submit(taskToDo);
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
     * Shutdowns the ThreadPoolExecutor.
     */
    public void gracefullyTerminate() {
        this.poolOfThreads.shutdown();
    }

    /**
     * Returns the maximum priority task that the
     * ThreadPoolExecutor executed.
     * @return maximum priority task.
     */
    public int getCurrentMax() {
        return this.currentMaxPriority;
    }
}
