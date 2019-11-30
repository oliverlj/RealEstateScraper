package myJavaClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShellWrapper {

    public static ArrayList<String> execute(String command)
    {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec(command);

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            ArrayList<String> returns = new ArrayList<>();
            while ((line = br.readLine()) != null) {
//                Disp.anyType(line);
                returns.add(line);
            }

            if (! returns.isEmpty()) return returns;
            else return null;

        } catch (IOException ioEx) {
            return null;
        }
    }
}
