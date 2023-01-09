package Ex2_2;

import java.util.Comparator;
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
    private static final int DEFAULT_PRIORITY = 10;

    /**
     * Holds the maximum priority task that the
     * ThreadPoolExecutor executed.
     */
    public static int currentMaxPriority = DEFAULT_PRIORITY;

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
    }

    /**
     * Submits a task to the ThreadPoolExecutor.
     * @param taskToDo  A Task to execute.
     * @return a Future representing pending completion of the task.
     * @throws NullPointerException if a null task is given
     */
    public <V> Future<V> submit(Task<V> taskToDo) throws NullPointerException {
        if (taskToDo == null)
            throw new NullPointerException();

        this.currentMaxPriority = Math.min(this.currentMaxPriority, taskToDo.getPriority());
        execute(taskToDo);
        return taskToDo;
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
     * This method executed after a task have been executed.
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     * execution completed normally
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        if (super.getQueue().isEmpty())
        {
            this.currentMaxPriority = DEFAULT_PRIORITY;
            return;
        }

        Task task = (Task)super.getQueue().peek();
        this.currentMaxPriority = ((task != null) ? task.getPriority():DEFAULT_PRIORITY);
    }

    /**
     * Shutdowns the ThreadPoolExecutor.
     */
    public void gracefullyTerminate() {
        super.shutdown();
    }

    /**
     * Returns the maximum priority task that the
     * ThreadPoolExecutor executed.
     * @return maximum priority task.
     */
    public int getCurrentMax() {
        return this.currentMaxPriority;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@link
     *     #equals(Object) equals} method, then calling the {@code
     *     hashCode} method on each of the two objects must produce the
     *     same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link #equals(Object) equals} method, then
     *     calling the {@code hashCode} method on each of the two objects
     *     must produce distinct integer results.  However, the programmer
     *     should be aware that producing distinct integer results for
     *     unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     *
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     *
     * <p>
     * An equivalence relation partitions the elements it operates on
     * into <i>equivalence classes</i>; all the members of an
     * equivalence class are equal to each other. Members of an
     * equivalence class are substitutable for each other, at least
     * for some purposes.
     *
     * @param obj the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     *
     * @implSpec The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * In other words, under the reference equality equivalence
     * relation, each equivalence class only has a single element.
     * @apiNote It is generally necessary to override the {@link #hashCode() hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     * @see #hashCode()
     * @see HashMap
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
        return "Queue size: " + super.getQueue().size() + "; Current max priority: " + this.currentMaxPriority;
    }
}
