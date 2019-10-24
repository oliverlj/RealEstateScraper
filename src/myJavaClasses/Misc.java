package myJavaClasses;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


public class Misc {


    public static String removeNonNumericalCharsFromString(String to_treat) {
        String out = to_treat
                .replaceAll("[^\\d.]", ""); // remove all non-numeric characters ("de" & "à")
//        Disp.anyType(out);
        return out;
    }

    public static String valueNormalise(String to_format) {
        String out = to_format
                .replaceAll("\\s", "") // removes breaking whitespaces
                .replaceAll("\\p{Z}", "") // removes non-breaking whitespaces
                .replaceAll("," , ".") // quick fix for , and .
                ;
//        Disp.anyType(out);
        return out;
    }

    public static String[] jsonifyLinkedHashMapIntoStringArray(LinkedHashMap<String, String> map) {
        try {
            int nbEntries = map.keySet().size();
            String[] entries = new String[nbEntries];
            int i=0;
            for (String entryKey : map.keySet()) {
                String entryValue = map.get(entryKey);
                String pair = entryKey + " : " + entryValue;
                entries[i] = pair;
                i++;
            }
            return entries;
        } catch (NullPointerException npe) {
            return null;
        }
    }

    public static Object[] arrayListToArray(ArrayList<?> al) {
        Object[] out = new String[al.size()];
        for (int i=0 ; i<al.size() ; i++) {
            out[i] = al.get(i);
        }
        return out;
    }

    public static ArrayList<?> arrayToArrayList(Object[] arr) {
        ArrayList<Object> out = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            out.add(arr[i]);
        }
        return out;
    }


    public static String jsonifyArrayIntoString(String node_name, Object[] arr, boolean linebreak) {
        try {
            String out = "";
            out += node_name + " : [ ";
            if (linebreak) out += "\n  ";

            for (int i=0 ; i<arr.length ; i++) {
                Object obj = arr[i];
                out += obj;
                if (i < arr.length - 1) out += " , ";

                if (linebreak) out += "\n  ";
            }
            out += " ]";
            return out;
        } catch (NullPointerException npe) {
            return null;
        }

    }
    
    public static String separateArrayStringWithBars(String[] arr) {
        String out = "";
        for (String s : arr) {
            String s2 = s + " | ";
            out += s2;
        }
        return out;
    }

    public static String shortifyTitle(String title, int max)
    {
        int initial_length = title.length() ;

        if (initial_length > max) {
            title = title.substring(0, max) + "...";
        }

        return title ;
    }

    public static String fetchStringFromFile(String file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separatorLine");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }


    public static String[] splitStringIntoLinesArray(String str)
    {
        String[] split = str.split("\\r?\\n");
        // pour output une al plutot qu'un str[], réactiver ce morceau :
		/*
		ArrayList<String> al = new ArrayList<String>();
		for (String s : split)
		{
			al.add(s);
		}
		*/
        return split ;
    }

    public static ArrayList<String> splitStringIntoLinesArrayList(String str)
    {
        String[] split = str.split("\\r?\\n");
        // pour output une al plutot qu'un str[], réactiver ce morceau :


        return convertArrayIntoAL(split);
    }


    public static String patchStringArray(String[] str_arr)
    {
        String str_unified = "" ;

        int i=0;
        for (String s : str_arr) {
            i++;
            str_unified += s ;
            if (i < str_arr.length) str_unified += "\r\n";
        }

        return str_unified ;
    }


    public static String patchStringArrayList(ArrayList<String> str_al)
    {
        String str_unified = "" ;

        for (String s : str_al) {
            str_unified += s + "\r\n" ;
        }

        return str_unified ;
    }



    public static ArrayList<String> extractBetweenDelimitersInList
            (String to_be_extracted, String delim_beg, String delim_end)
    {
        ArrayList<String> out = new ArrayList<>();
        int index_beg ;
        int index_end ;

        // ne marche qu'avec des delim differents.
        if (delim_beg.equals(delim_end)) {
            System.out.println("!!! delimiters must not be identical !!!");
            return null ;
        }

        // si l'un des deux est vide, on considère le début ou la fin comme delim
        if (delim_beg.isEmpty())
        {
            index_beg = 0 ;
        }
        if (delim_end.isEmpty())
        {
            index_end = to_be_extracted.length();
        }

        while (to_be_extracted.contains(delim_beg) && to_be_extracted.contains(delim_end))
        {
            index_beg = to_be_extracted.indexOf(delim_beg);
            index_end = to_be_extracted.indexOf(delim_end);

            // correction si jamais ils s'inversent
            if (index_beg > index_end)
            {
                int tmp = index_beg ;
                index_beg = index_end ;
                index_end = tmp ;
            }

            //System.out.println(to_be_extracted);
            //System.out.println(index_beg + " | " + index_end);

            String extracted = to_be_extracted.substring(index_beg + 1, index_end);
            out.add(extracted);

            // recree le string avec juste la partie extraite en moins
            String new_str_head = to_be_extracted.substring(0, index_beg) ;
            String new_str_tail = to_be_extracted.substring(index_end + 1) ;

            to_be_extracted = new_str_head + new_str_tail ;
        }

        return out ;
    }


    public static ArrayList<String> extractOnlyNeededLinesFromStringArrayList
            (ArrayList<String> lines, String delim_top, String delim_bottom)
    {
        // NB1 : delim_top est inclus dans extracted, delim_bottom ne l'est pas
        // NB2 :  +++ l'input lines est directement interchangeable entre str[] et al<str>

        boolean cut = false ;
        ArrayList<String> extracted = new ArrayList<String>() ;

        for (String line : lines)
        {
            if (line.contains(delim_top))
            {
                cut = true ;
                extracted.add(line);
            }
            else if (line.contains(delim_bottom))
            {
                cut = false ;
            }
            else
            {
                if (cut)
                {
                    extracted.add(line);
                }
            }
        }
        return extracted ;
    }


    public static ArrayList<String> extractOnlyFiltered
            (
                    ArrayList<String> lines
                    //	String[] lines
                    ,	String filter)
    {
        // NB2 :  +++ l'input lines est directement interchangeable entre str[] et al<str>

        ArrayList<String> extracted = new ArrayList<String>() ;

        for (String line : lines)
        {
            if (line.contains(filter))
            {
                extracted.add(line);
            }
        }
        return extracted ;
    }


    public static String removeSpecifiedCharFromString(String str, char c)
    {
        str = str.replace(c , '¤').replaceAll("¤" , "");
        return str ;
    }


    public static String firstLetterUpperCase(String inputval)
    {
        try
        {
            String[] spl = inputval.split(" ");
            String result = "" ;

            for (String s : spl)
            {
                String interm = s.substring(0,1).toUpperCase()
                        + s.substring(1).toLowerCase() + " " ;

                result += interm ;
            }

            return result ;
        }
        catch (StringIndexOutOfBoundsException e)
        {
            // si exception, ca veut dire que inputval == ""
            return inputval ;
        }
    }


    public static String cutAndPatchUpString(String to_cut, String to_be_removed, String separator)
    {
        String beginning  ;
        String end  ;

        while (to_cut.contains(to_be_removed))
        {
            int ind = to_cut.indexOf(to_be_removed);
            beginning = to_cut.substring(0, ind);
            end = to_cut.substring(ind + to_be_removed.length());

            to_cut = beginning + separator + end ;
        }

        return to_cut ;
    }


    public static ArrayList<String> returnListOfStringPieces(String to_split, String to_be_removed)
    {
        String separator = "¤" ;

        String to_spl = cutAndPatchUpString(to_split, to_be_removed, separator);
        String[] split = to_spl.split(separator);
        ArrayList<String> out = new ArrayList<>();

        for (String s : split)
        {
            out.add(s);
        }

        return out ;
    }


    public static String formatDate(Date date, String pattern) {
		/*
		"yyyy.MM.dd G 'at' HH:mm:ss z" ---- 2001.07.04 AD at 12:08:56 PDT
		"hh 'o''clock' a, zzzz" ----------- 12 o'clock PM, Pacific Daylight Time
		"EEE, d MMM yyyy HH:mm:ss Z"------- Wed, 4 Jul 2001 12:08:56 -0700
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ"------- 2001-07-04T12:08:56.235-0700
		"yyMMddHHmmssZ"-------------------- 010704120856-0700
		"K:mm a, z" ----------------------- 0:08 PM, PDT
		"h:mm a" -------------------------- 12:08 PM
		"EEE, MMM d, ''yy" ---------------- Wed, Jul 4, '01
		 */

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static String makeFormattedDateHumanReadable(String formatted_date)
    {
        return formatted_date
                .replace("_", " at ")
                .replace("-", "/");
    }


    public static String formatLocalDateTime(LocalDateTime ldt)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        return ldt.format(formatter);
    }

    public static String formatLocalTime(LocalTime lt)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return lt.format(formatter);
    }


    private static ArrayList<String> convertArrayIntoAL(String[] to_conv)
    {
        ArrayList<String> al = new ArrayList<String>();
        for (String s : to_conv)
        {
            al.add(s);
        }

        return al ;
    }


    public static ArrayList<Integer> getAllIndexesOfCharacter(String word, char ch)
    {
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int index = word.indexOf(ch);
             index >= 0;
             index = word.indexOf(ch, index + 1)
        )
        {
            indexes.add(index);
        }

        return indexes ;
    }




}
