package Ex2_2;

import java.util.concurrent.*;

/**
 * A class to use for adapting Task to FutureTask class & RunnableFuture interface.
 */
public class TaskAdapter<V> extends FutureTask<V> implements Comparable<TaskAdapter<V>>{

    /**
     * The priority of the task.
     */
    private int priority;

    /**
     * Creates a custom FutureTask that will, upon running, execute the given Callable.
     * @param callable the callable task
     * @param priority the priority of the task
     */
    public TaskAdapter(Callable<V> callable, int priority) {
        super(callable);
        this.priority = priority;
    }

    /**
     * Returns the priority of the task.
     * @return the priority
     */
    public int getPriority() {
        return this.priority;
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
     * Compares this object with the specified object for order.
     * @param o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(TaskAdapter o) {
       return Integer.compare(this.priority, o.priority);
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "" + this.priority;
    }
}
