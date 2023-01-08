package Ex2_1;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class Ex2_1 {

    public static final String filePrefix = "file_";
    public static final String fileSuffix = ".txt";

    public static String[] createTextFiles(int n, int seed, int bound){
        String[] fileNames = new String[n];

        Random rand = new Random(seed);

        int lines;

        System.out.println("Creating " + n +" files with seed " + seed + " and bound " + bound + "...");

        for (int i = 1; i <= n; ++i)
        {
            try
            {
                lines = rand.nextInt(bound);

                fileNames[i-1] = filePrefix + i + fileSuffix;

                FileWriter myWriter = new FileWriter(fileNames[i-1]);

                for (int j = 1; j <= lines; ++j)
                    myWriter.write("Line " + j + "/" + lines + "" + System.getProperty("line.separator"));

                myWriter.close();
            }

            catch (IOException | IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Files created successfully.");

        return fileNames;
    }

    public static int getNumOfLines(String[] fileNames) {
        int sum = 0;
        for (int i = 0; i < fileNames.length; ++i)
        {
            int counter = 0;

            try
            {
                File myObj = new File(fileNames[i]);
                Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine())
                {
                    myReader.nextLine();
                    ++counter;
                }

                myReader.close();
            }

            catch(FileNotFoundException | IllegalStateException | NoSuchElementException e)
            {
                e.printStackTrace();
            }

            sum += counter;
        }

        return sum;
    }

    public int getNumOfLinesThreads(String[] fileNames) {
        FileThread[] threads = new FileThread[fileNames.length];
        int sum = 0;

        for (int i = 0; i < fileNames.length; ++i)
        {
            threads[i] = new FileThread(fileNames[i]);
            threads[i].start();
        }

        for (FileThread thread : threads)
        {
            try
            {
                thread.join();
                sum += thread.getTotalLines();
            }

            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        return sum;
    }

    public int getNumOfLinesThreadPool(String[] fileNames) {
        ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        int sum = 0;

        for (int i = 0; i < fileNames.length; ++i)
        {
            Callable<Integer> callable = new FileThreadCallable(fileNames[i]);
            list.add(pool.submit(callable));
        }

        for (Future<Integer> fut : list)
        {
            try
            {
                sum += fut.get();
            }

            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }

        pool.shutdown();

        return sum;
    }
}
