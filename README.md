# Object-Oriented Programming Course Ex2
### For Computer Science B.Sc. Ariel University

**By Roy Simanovich and Avichay Mezin**

## Description
### Threads and ThreadPool.
In this assignment we learned about Threads and ThreadPool.  
a thread is a flow of execution within a program that can be managed independently by the operating system or hardware. Threads are useful for parallelizing work and improving program performance, as well as for performing tasks asynchronously and improving the responsiveness of a program. They are a common tool for concurrent programming and are supported by many programming languages and platforms.  
ThreadPool is a collection of worker threads that are used to perform tasks concurrently. It helps to reduce the overhead of creating and destroying threads and can improve the performance of a program by allowing it to execute multiple tasks concurrently.  

![UML](https://user-images.githubusercontent.com/101700010/211231885-4b659f23-b223-4248-828d-fb966da55cdc.png)

### about this assignment
This assignment includes two parts. the first one is about text files and threads and also ThreadPool.
The second part is about generate an object which represent an asynchronic task with a priority and also a kind of ThreadPool which support in tasks with a priority.

## The first part - Ex2_1:
the first part is about making a number of text files and sum the number of lines in this files.
we gonna do this 3 times in 3 different methods. one will be without threads, the second will be with using threads, and the third will be with the using of ThreadPool.
for doing this we gonna use 4 functions:  
1) **createTextFiles** --> this function create n text files ,the number of lines in each text in random. the method return an array with the names of the files.
2) **getNumOfLines** --> this function get an array with the names of the files and return the sum of all the lines in the texts.
3) **getNumOfLinesThreads** --> this function get an array with the names of the files and return the number of all the lines in the text files using threads.
4) **getNumOfLinesThreadPool** -->this function get an array with the names of the files and return the number of all the lines in the text files using ThreadPool.  

### FileThread  
an object which represent a Thread that calculate the sum of lines in text file.

### FileThreadCallable  
an object which represent a thread for the "getNumOfLinesThreadPool" function in Ex2_1 and that thread is sum the number of lines in the text.

### Tests
Tests that we made.

## The second part -Ex2_2:
In java we cannot give a priority to a asynchronic task, java give the programmer the option to give a priority to thread,  but not to the task in the thread. 
That's why we need to make an object which represent asynchronic task with a priority and also a kind of ThreadPool which support in tasks with priority.

### Task
an object which represent tasks with priority.

### CustomExcecutor
a class that represent kind of ThreadPool which support a queue of tasks.

### TaskType
a enum with a kind of task. computational is defined as 1, IO is defined as 2 and other defined as 3.
Inside this enum we have set and get for the priority.
