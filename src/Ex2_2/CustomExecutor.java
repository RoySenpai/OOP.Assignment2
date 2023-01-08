package Ex2_2;

import java.util.Comparator;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {

    /* Some constants to be used. */

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
    private static final int DEFAULT_PRIORITY = 10;

    /**
     * Used for the queue of the ThreadPoolExecutor.
     */
    private static PriorityBlockingQueue<Runnable> BlockingQueue = new PriorityBlockingQueue<>(
                                                                        MINIMUM_POOLSIZE,
                                                                        (o1, o2) -> ((Task)o1).compareTo((Task)o2)
                                                                    );

    /**
     * Holds the maximum priority task that the
     * ThreadPoolExecutor executed.
     */
    private int currentMaxPriority = DEFAULT_PRIORITY;

    /**
     * A constructor.
     */
    public CustomExecutor() {
        super(
                MINIMUM_POOLSIZE,
                MAXIMUM_POOLSIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                BlockingQueue
        );
    }

    /**
     * Submits a task to the ThreadPoolExecutor.
     * @param taskToDo  A Task to execute.
     * @return a Future representing pending completion of the task.
     * @throws NullPointerException if a null task is given
     */
    public <V> Future<V> submit(Task<V> taskToDo) {
        this.currentMaxPriority = Math.min(this.currentMaxPriority, taskToDo.getPriority());
        return super.submit(taskToDo);
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
        super.shutdown();
    }

    /*@Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r,t);

        if (BlockingQueue.isEmpty())
        {
            this.currentMaxPriority = DEFAULT_PRIORITY;
            return;
        }

        RunnableFuture rn = (RunnableFuture)r;

        Task task = (Task)rn;

        int nextPriority = ((Task)BlockingQueue.peek()).getPriority();

        if (task.getPriority() < nextPriority)
            this.currentMaxPriority = nextPriority;
    }*/

    /**
     * Returns the maximum priority task that the
     * ThreadPoolExecutor executed.
     * @return maximum priority task.
     */
    public int getCurrentMax() {
        /*Runnable runnableTask = BlockingQueue.peek();

        if (runnableTask == null)
            this.currentMaxPriority = DEFAULT_PRIORITY;

        else
            this.currentMaxPriority=((Task)runnableTask).getPriority();*/

        return this.currentMaxPriority;
    }
}
