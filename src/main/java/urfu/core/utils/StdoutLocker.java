package urfu.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StdoutLocker
{
    public static String lockStdout(Runnable runnable)
    {
        // Redirect stdout to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream originalOut = System.out;
        System.setOut(ps);

        // Execute the runnable
        runnable.run();

        // Restore stdout and get the stdout output as a string
        System.setOut(originalOut);

        return baos.toString();
    }
}