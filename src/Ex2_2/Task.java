package Ex2_2;

import java.util.concurrent.Callable;

public class Task<V> implements Callable<V>, Comparable<Task<V>> {

    private Callable<V> taskToDo;
    private int priority;
    private Task(Callable<V> taskToDo, TaskType typeOfTheTask) {
        this.taskToDo = taskToDo;
        this.priority = typeOfTheTask.getPriorityValue();
    }
    private Task(Callable<V> taskToDo) {
        this.taskToDo = taskToDo;
        this.priority = 0;
    }

    public static Task createTask(Callable taskToDo, TaskType typeOfTheTask) {
        return new Task(taskToDo, typeOfTheTask);
    }

    public static Task createTask(Callable taskToDo) {
        return new Task(taskToDo);
    }

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
        if (this.priority == o.priority)
            return 0;

        return ((this.priority < o.priority) ? -1:1);
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
