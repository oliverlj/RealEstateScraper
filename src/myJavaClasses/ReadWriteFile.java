package myJavaClasses;


import java.io.*;
import java.util.ArrayList;

public class ReadWriteFile
{
    public static ArrayList<File> getFolderContentsAsFileArray(String full_path)
    {
        File folder = new File(full_path);
        File[] files = folder.listFiles();
        ArrayList<?> out = Misc.arrayToArrayList(files);
        return (ArrayList<File>)out ;
    }


    public static ArrayList<String> getFileContentAsStringArray(String full_path)
    {
        try {
            BufferedReader br = ReadWriteFile.outputReader(full_path);

            ArrayList<String> out = new ArrayList<>();
            String line;

            while((line = br.readLine()) != null){
                out.add(line);
            }
            return out;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static BufferedWriter outputWriter(String path)
    {
        try
        {
            File f = new File(path);

            if (!f.exists())
            {
                f.createNewFile();
            }

            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);

            return bw ;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null ;
        }
    }

    
    public static BufferedReader outputReader(String path) throws FileNotFoundException
    {
        File f = new File(path);

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        return br ;
    }
    

}
