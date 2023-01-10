# Object-Oriented Programming Course Ex2
### For Computer Science B.Sc. Ariel University

**By Roy Simanovich and Avichay Mezin**

## Description
### Threads and ThreadPool
In this assignment we learned about Threads and ThreadPool.

a thread is a flow of execution within a program that can be
managed independently by the operating system or hardware.
Threads are useful for parallelize work and improving program
performance, as well as for performing tasks asynchronously and
improving the responsiveness of a program. They are a common tool
for concurrent programming and are supported by many
programming languages and platforms. ThreadPool is a collection
of worker threads that are used to perform tasks concurrently.
It helps to reduce the overhead of creating and destroying threads
and can improve the performance of a program by allowing it to
execute multiple tasks concurrently.  

### About this assignment
This assignment includes two parts. the first one is about text
files and threads and also ThreadPool. The second part is about
generate an object which represent an asynchronous task with a
priority and also a kind of ThreadPool which support in tasks with
a priority.

## The first part – Ex2_1:
the first part is about making a number of text files and sum the
number of lines in these files.  We're going to do this 3 times
in 3 different methods. one will be without threads, the second
will be with using threads, and the third will be with the using
of ThreadPool. To do this we use 4 functions:  
1) **createTextFiles** – This function create n text files, the 
number of lines in each text in random. The method return an array
with the names of the files.
2) **getNumOfLines** – This function get an array with the names 
of the files and return the sum of all the lines in the texts.
3) **getNumOfLinesThreads** – This function get an array with the 
names of the files and return the number of all the lines in the text
files using threads.
4) **getNumOfLinesThreadPool** – This function get an array with the
names of the files and return the number of all the lines in the text
files using ThreadPool.  

![UML.png](src%2FEx2_1%2FUML.png)

### FileThread  
an object which represent a Thread that calculate the sum of lines
in text file.

### FileThreadCallable  
an object which represent a thread for the "getNumOfLinesThreadPool"
function in Ex2_1 and that thread is sum the number of lines in the text.

### Tests
Tests that we made. We concluded based on the testes that using threads is faster
than using ThreadPool and using ThreadPool is faster than not using threads at all.

## The second part – Ex2_2:
In java, we cannot give a priority to an asynchronous task, java give the
programmer the option to give a priority to thread,  but not to the task
in the thread. That's why we need to make an object which represent
asynchronous task with a priority and also a kind of ThreadPool which
support in tasks with priority.

![UML.png](src%2FEx2_2%2FUML.png)

### Task
An object which represent tasks with priority. This class implements
the Comparable interface and the Callable interface. This class also
uses the Factory design pattern, which mean the constructor is invisible
to others and the only way to create an object of this class is by using
the createTask method which can get a Callable object and priority
(via TaskType enum) or just a Callable object (and this just use the
default priority of TaskType.OTHER by default).


### CustomExcecutor
This class represents our ThreadPoolExecutor, as it extends this class
and uses its methods to create a custom thread pool. It uses its own
parameters (min and max number of threads, thread timeout, etc.) and
also holds the max priority of the tasks in the queue. Our executor uses
priorities to determine which task to execute next from the queue - the highest
one is the first one to be executed.

### TaskType
An enum with a kind of task. computational is defined as 1, IO is
defined as 2 and other defined as 3. Inside this enum we have set 
and get for the priority.

### TaskAdapter
Since by default the ThreadPoolExecutor class passes a RunnableFuture object
to the pool, we need to create a class that will adapt the Task object, so we
can use it in the pool. This class extends the FutureTask class which itself
implements the RunnableFuture interface. For its constructor, we pass a Task
object (Callable object) and a priority. The class also implements the Comparable
interface, so we can compare between tasks by their priority, and the comparison is
used by the priority queue itself that's passed to the ThreadPoolExecutor object.

### Tests
Tests that we made.
