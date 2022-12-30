import java.util.*;
import java.io.*;

public class FileThread extends Thread {

    private final String FileName;
    private int totalLines;
    public FileThread(String FileName)
    {
        super();
        this.FileName = FileName;
        this.totalLines = 0;
    }

    public int getTotalLines() {
        return this.totalLines;
    }

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
