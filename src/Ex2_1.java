import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class Ex2_1 {

    static final String filePrefix = "file_";
    static final String fileSuffix = ".txt";

    public static void main(String[] args) {
        String[] fileNames = createTextFiles(10,(int)(Math.random()*100),999);
        int x;
        long start = System.currentTimeMillis();
        x = getNumOfLines(fileNames);
        System.out.println("[NORMAL] Total lines " + x + ". Time: " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        x = getNumOfLinesThreads(fileNames);
        System.out.println("[THREAD] Total lines " + x + ". Time: " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        x = getNumOfLinesThreadPool(fileNames);
        System.out.println("[THREAD POOL] Total lines " + x + ". Time: " + (System.currentTimeMillis() - start) + "ms");
    }
    public static String[] createTextFiles(int n, int seed, int bound){
        String[] fileNames = new String[n];

        Random rand = new Random(seed);

        int lines;

        for (int i = 1; i <= n; ++i)
        {
            lines = rand.nextInt(bound);

            fileNames[i-1] = filePrefix + i + fileSuffix;

            try
            {
               FileWriter myWriter = new FileWriter(fileNames[i-1]);

               for (int j = 1; j <= lines; ++j)
                   myWriter.write("Line " + j + "/" + lines + "" + System.getProperty("line.separator"));

               myWriter.close();

               System.out.println("Created file " + fileNames[i-1] + " with " + lines + " lines.");
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

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
    public static int getNumOfLinesThreads(String[] fileNames) {
        FileThread[] threads = new FileThread[fileNames.length];
        int sum = 0;

        for (int i = 0; i < fileNames.length; ++i)
            threads[i] = new FileThread(fileNames[i]);

        for (int i = 0; i < fileNames.length; ++i)
            threads[i].run();

        for (FileThread thread : threads) {
            try
            {
                thread.join();
            }

            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        for (FileThread thread : threads) {
            sum += thread.getTotalLines();
        }

        return sum;
    }

    public static int getNumOfLinesThreadPool(String[] fileNames) {
        ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        int sum = 0;

        for (int i = 0; i < fileNames.length; ++i)
        {
            Callable<Integer> callable = new FileThreadCallable(fileNames[i]);
            Future<Integer> future = pool.submit(callable);
            list.add(pool.submit(callable));
        }

        for(Future<Integer> fut : list){
            try {
                sum += fut.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();

        return sum;
    }
}
