package Ex2_1;

import java.util.*;
import java.io.*;

public class FileThread extends Thread {

    private final String FileName;
    private int totalLines;

    /**
     * Constructor.
     * @param FileName a string representing the name of the file to be read.
     */
    public FileThread(String FileName)
    {
        super();
        this.FileName = FileName;
        this.totalLines = 0;
    }

    /**
     * Returns the total number of lines read from the file.
     * @return number of lines.
     */
    public int getTotalLines() {
        return this.totalLines;
    }

    /**
     * Run method.
     */
    @Override
    public void run() {
        try
        {
            File myObj = new File(FileName);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine())
            {
                myReader.nextLine();
                ++totalLines;
            }

            myReader.close();
        }

        catch(FileNotFoundException | IllegalStateException | NoSuchElementException e)
        {
            e.printStackTrace();
        }
    }
}
