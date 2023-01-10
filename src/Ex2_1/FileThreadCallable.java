package Ex2_1;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class FileThreadCallable implements Callable<Integer> {
    private final String FileName;

    /**
     * Constructor.
     * @param FileName a string representing the name of the file to be read.
     */
    public FileThreadCallable(String FileName)
    {
        super();
        this.FileName = FileName;
    }

    /**
     * Call method
     * @return total number of lines read from the file.
     * @throws Exception for errors
     */
    @Override
    public Integer call() throws Exception {
        int totalLines = 0;

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

        return totalLines;
    }
}
