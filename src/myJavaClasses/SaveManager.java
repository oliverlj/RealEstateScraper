package myJavaClasses;

import java.io.*;

public class SaveManager {
	
	private static String root_path ;


	public static String getSavePath()
    {
        return root_path ;
    }

	
	public static void setSavePath(String path)
	{
		root_path = path ;
		
		File file = new File(root_path);
		
		if (!file.exists())
		{
			file.mkdirs();
			
			Disp.anyType(">>> Folder [ " + path + " ] created successfully :)");
		}
	}


    public static void objectSave(String filename, Object to_save)
	{
	    try {

            Disp.anyType(">>> Saving to [ " + filename + " ] ... !!!!!! DO NOT STOP PROGRAM NOW OR YOU'LL LOSE THE WHOLE SAVE !!!!!! ... You'd better be patient, wouldn't you ?");

            FileOutputStream fos = new FileOutputStream(root_path + filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(to_save);
            oos.close();

            Disp.shortMsgStar("SAVED [ " + filename + " ] SUCCESSFULLY :)", true);

        } catch (NullPointerException np_ex) {

	        Disp.exc("CANNOT FIND SAVE PATH [" + root_path + filename + "]");

        } catch (IOException io_ex) {

            Disp.exc(io_ex);
        }

	}


	public static Object objectLoad(String filename, boolean showFullSavePath)
	{
		try {

            if (showFullSavePath)
                System.out.println(">>> Full Save Path is : " + root_path + filename);

            System.out.println(">>> Loading [ " + filename + " ] ... Please wait");

            FileInputStream fis = new FileInputStream(root_path + filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = (Object) ois.readObject();
            ois.close();

            System.out.println(">>> Loading [ " + filename + " ] complete. :)");

            return obj;

        } catch (ClassNotFoundException cnf_ex) {

		    return null;

        } catch (IOException io_ex) {

			Disp.exc("CANNOT FIND SAVE PATH [ " + root_path + filename + " ]");
            Disp.exc(io_ex);
            return null;
        }


	}

}
