import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class FileThreadCallable implements Callable<Integer> {
    private final String FileName;
    public FileThreadCallable(String FileName)
    {
        super();
        this.FileName = FileName;
    }

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
