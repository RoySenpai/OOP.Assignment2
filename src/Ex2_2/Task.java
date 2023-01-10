package Ex2_2;

import java.util.concurrent.*;

public class Task<V> implements Callable<V>, Comparable<Task<V>> {

    /**
     * A constant to be used for default priority.
     */
    private static final TaskType DEFAULT_PRIORITY = TaskType.OTHER;

    /**
     * Task to do.
     */
    private Callable<V> taskToDo;

    /**
     * The priority of the task.
     */
    private int priority;

    /**
     * A constructor (PRIVATE).
     * @param taskToDo  Callable method of task to do.
     * @param typeOfTheTask Task priority.
     */
    private Task(Callable<V> taskToDo, TaskType typeOfTheTask) {
        this.taskToDo = taskToDo;
        this.priority = typeOfTheTask.getPriorityValue();
    }

    /**
     * Creates a new task
     * @param taskToDo  Callable method of task to do.
     * @param typeOfTheTask Task priority.
     * @return a Task object.
     */
    public static Task createTask(Callable taskToDo, TaskType typeOfTheTask) {
        return new Task(taskToDo, typeOfTheTask);
    }

    /**
     * Creates a new task, with default priority.
     * @param taskToDo  Callable method of task to do.
     * @return a Task object.
     */
    public static Task createTask(Callable taskToDo) {
        return new Task(taskToDo, DEFAULT_PRIORITY);
    }

    /**
     * Returns the current priority of the task.
     * @return  current priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Compares the priority of this task to other task's priority.  Returns a
     * negative integer, zero, or a positive integer as the priority of
     * this task is less than, equal to, or greater than the specified priority
     * of other task's.
     *
     * @param o the other task to be compared.
     *
     * @return a negative integer, zero, or a positive integer as the priority of
     * this task is less than, equal to, or greater than the specified priority
     * of other task's.
     *
     * @throws NullPointerException if the specified object is null
     */
    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.priority, o.priority);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     *
     * @throws Exception if unable to compute a result
     */
    @Override
    public V call() throws Exception {
        return this.taskToDo.call();
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    public String toString() {
        return "(Priority=" + this.priority + ")";
    }
}
