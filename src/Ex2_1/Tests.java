package Ex2_1;

import org.junit.jupiter.api.*;

class Tests {
    static String[] fileNames;
    static Ex2_1 test = new Ex2_1();
    @BeforeAll
    public static void runOnceBefore() {
        fileNames = Ex2_1.createTextFiles(1000,(int)(Math.random()*100),99999);
        System.out.println("Starting testes...");
    }

    @AfterAll
    public static void runOnceAfter() {
        System.out.println("\nTestes ended!");
    }

    @BeforeEach
    public void runBeforeEach() {
        System.out.println("----------------------------------------");
        System.out.println("Running new test...");
    }

    @AfterEach
    public void runAfterEach() {
        System.out.println("Finished test.\n");
    }

    @org.junit.jupiter.api.Test
    void getNumOfLines() {
        long start = System.currentTimeMillis();
        int x = Ex2_1.getNumOfLines(fileNames);
        System.out.println("[NORMAL] Total lines " + x + ". Time: " + (System.currentTimeMillis() - start) + "ms");
    }

    @org.junit.jupiter.api.Test
    void getNumOfLinesThreads() {
        long start = System.currentTimeMillis();
        int x = test.getNumOfLinesThreads(fileNames);
        System.out.println("[THREAD] Total lines " + x + ". Time: " + (System.currentTimeMillis() - start) + "ms");
    }

    @org.junit.jupiter.api.Test
    void getNumOfLinesThreadPool() {
        long start = System.currentTimeMillis();
        int x = test.getNumOfLines(fileNames);
        System.out.println("[THREAD POOL] Total lines " + x + ". Time: " + (System.currentTimeMillis() - start) + "ms");
    }
}