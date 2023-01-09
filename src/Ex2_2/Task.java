package Ex2_2;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<V> extends FutureTask<V> implements Callable<V>, Comparable<Task> {

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
        super(taskToDo);
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
        return Integer.compare(this.priority, o.getPriority());
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
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    public String toString() {
        return "(Priority=" + this.priority + ")";
    }
}
