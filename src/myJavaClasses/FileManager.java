package myJavaClasses;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileManager
{
    public static File[] getListFilesInFolder(String save_path)
    {
        File folder = new File(save_path);
        File[] files = folder.listFiles();
        return files;
    }


    public static void moveFileToLocation(File fileToBeMoved, String pathToMoveTo)
    {
        File destination = new File(pathToMoveTo);

        //if (destination.exists())
        {
            fileToBeMoved.renameTo(destination);
        }
        //else
        {
            //System.out.println("!!! File could not be moved. Make sure that destination exists");
        }

        System.out.println(">>> The file [" + fileToBeMoved.toString()
                + "] has successfully been moved to : " + destination);
    }


    public static String trimExtensionFromFilename(String filename)
    {
        int pos = filename.lastIndexOf(".");
        String absFileName = pos > 0 ? filename.substring(0, pos) : filename;

        return absFileName ;
    }


    public static ArrayList<Path> returnListPaths(File folder_to_list)
    {
        ArrayList<Path> list_paths = new ArrayList<Path>();

        for (final File file : folder_to_list.listFiles())
        {
            list_paths.add(file.toPath());
        }

        return list_paths ;
    }


}
