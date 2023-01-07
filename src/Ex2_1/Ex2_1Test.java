package Ex2_1;

import org.junit.jupiter.api.*;

class Ex2_1Test {
    String[] fileNames = Ex2_1.createTextFiles(1000,(int)(Math.random()*100),99999);
    @BeforeAll
    public static void runOnceBefore() {
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
        Ex2_1.getNumOfLines(fileNames);
    }

    @org.junit.jupiter.api.Test
    void getNumOfLinesThreads() {
        Ex2_1.getNumOfLinesThreads(fileNames);
    }

    @org.junit.jupiter.api.Test
    void getNumOfLinesThreadPool() {
        Ex2_1.getNumOfLinesThreadPool(fileNames);
    }
}