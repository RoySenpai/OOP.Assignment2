package Ex2_2;

import java.util.concurrent.Callable;

public class Task<V> implements Callable<V>, Comparable<Task<V>> {

    /**
     * A constant to be used for default priority.
     */
    private static final int DEFAULT_PRIORITY = 5;

    private Callable<V> taskToDo;
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
        TaskType t = TaskType.OTHER;
        t.setPriority(DEFAULT_PRIORITY);
        return new Task(taskToDo, t);
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
    public int compareTo(Task<V> o) {
        return (o.priority - this.priority);
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
}
