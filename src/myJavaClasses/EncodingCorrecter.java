package myJavaClasses;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EncodingCorrecter {

/////// 2018-08-25 : now supports u00 decoding !
	
	private static String url_percentEncoding = "http://www.degraeve.com/reference/urlencoding.php" ;
	
	private static HashMap<String, Character> mapping_percentEncoding = new HashMap<String, Character>() ;
	private static HashMap<String, Character> mapping_htmlEncoding = new HashMap<String, Character>() ;
	private static HashMap<String, Character> mapping_u00 = new HashMap<String, Character>() ;



	public static void refreshEncodingAtStartup(String force_encoding) throws Exception
	{
//		"UTF-8"

		System.setProperty("file.encoding",force_encoding);
		Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null,null);
	}


	private static void fetchPercentEncodingTable() throws Exception
	{
		String html = ParseHtml.fetchHtmlAsDocumentFromUrl(url_percentEncoding).html();
		ArrayList<String> fetched = Misc.splitStringIntoLinesArrayList(html);
		
		
		List<String> extracted = Misc.extractOnlyNeededLinesFromStringArrayList(fetched, "<pre>", "</pre>");
		
		
		for (String s : extracted)
		{
			String key ;
			char cha ;
			
			String[] spl = s.split("%");
			
			try
			{
				String corresp = spl[0].trim() ;
				char[] ch = corresp.toCharArray();
				cha = ch[0];
				key = "%" + spl[1].trim() ;
			}
			catch (Exception e)
			{
				cha = '%' ;
				key = "%25";
			}
			
			//System.out.println(key + " correspond à : " + cha);
			
			mapping_percentEncoding.put(key, cha);
		}
		
	}
	
	
	private static void fetchHtmlEncodingTable()
	{
		mapping_htmlEncoding.put("&amp;", '&');
		mapping_htmlEncoding.put("&lt;", '<');
		mapping_htmlEncoding.put("&gt;", '>');
		mapping_htmlEncoding.put("&quot;", '"');
		mapping_htmlEncoding.put("&apos;", ' ');
		
	}
	
	
	private static void fetchU00EncodingTable()
	{
		mapping_u00.put("\\u00c0" , 'À' ) ;
		mapping_u00.put("\\u00c1" , 'Á' ) ;
		mapping_u00.put("\\u00c2" , 'Â' ) ;
		mapping_u00.put("\\u00c3" , 'Ã' ) ;
		mapping_u00.put("\\u00c4" , 'Ä' ) ;
		mapping_u00.put("\\u00c5" , 'Å' ) ;
		mapping_u00.put("\\u00c6" , 'Æ' ) ;
		mapping_u00.put("\\u00c7" , 'Ç' ) ;
		mapping_u00.put("\\u00c8" , 'È' ) ;
		mapping_u00.put("\\u00c9" , 'É' ) ;
		mapping_u00.put("\\u00ca" , 'Ê' ) ;
		mapping_u00.put("\\u00cb" , 'Ë' ) ;
		mapping_u00.put("\\u00cc" , 'Ì' ) ;
		mapping_u00.put("\\u00cd" , 'Í' ) ;
		mapping_u00.put("\\u00ce" , 'Î' ) ;
		mapping_u00.put("\\u00cf" , 'Ï' ) ;
		mapping_u00.put("\\u00d1" , 'Ñ' ) ;
		mapping_u00.put("\\u00d2" , 'Ò' ) ;
		mapping_u00.put("\\u00d3" , 'Ó' ) ;
		mapping_u00.put("\\u00d4" , 'Ô' ) ;
		mapping_u00.put("\\u00d5" , 'Õ' ) ;
		mapping_u00.put("\\u00d6" , 'Ö' ) ;
		mapping_u00.put("\\u00d8" , 'Ø' ) ;
		mapping_u00.put("\\u00d9" , 'Ù' ) ;
		mapping_u00.put("\\u00da" , 'Ú' ) ;
		mapping_u00.put("\\u00db" , 'Û' ) ;
		mapping_u00.put("\\u00dc" , 'Ü' ) ;
		mapping_u00.put("\\u00dd" , 'Ý' ) ;
		mapping_u00.put("\\u00df" , 'ß' ) ;
		mapping_u00.put("\\u00e0" , 'à' ) ;
		mapping_u00.put("\\u00e1" , 'á' ) ;
		mapping_u00.put("\\u00e2" , 'â' ) ;
		mapping_u00.put("\\u00e3" , 'ã' ) ;
		mapping_u00.put("\\u00e4" , 'ä' ) ;
		mapping_u00.put("\\u00e5" , 'å' ) ;
		mapping_u00.put("\\u00e6" , 'æ' ) ;
		mapping_u00.put("\\u00e7" , 'ç' ) ;
		mapping_u00.put("\\u00e8" , 'è' ) ;
		mapping_u00.put("\\u00e9" , 'é' ) ;
		mapping_u00.put("\\u00ea" , 'ê' ) ;
		mapping_u00.put("\\u00eb" , 'ë' ) ;
		mapping_u00.put("\\u00ec" , 'ì' ) ;
		mapping_u00.put("\\u00ed" , 'í' ) ;
		mapping_u00.put("\\u00ee" , 'î' ) ;
		mapping_u00.put("\\u00ef" , 'ï' ) ;
		mapping_u00.put("\\u00f0" , 'ð' ) ;
		mapping_u00.put("\\u00f1" , 'ñ' ) ;
		mapping_u00.put("\\u00f2" , 'ò' ) ;
		mapping_u00.put("\\u00f3" , 'ó' ) ;
		mapping_u00.put("\\u00f4" , 'ô' ) ;
		mapping_u00.put("\\u00f5" , 'õ' ) ;
		mapping_u00.put("\\u00f6" , 'ö' ) ;
		mapping_u00.put("\\u00f8" , 'ø' ) ;
		mapping_u00.put("\\u00f9" , 'ù' ) ;
		mapping_u00.put("\\u00fa" , 'ú' ) ;
		mapping_u00.put("\\u00fb" , 'û' ) ;
		mapping_u00.put("\\u00fc" , 'ü' ) ;
		mapping_u00.put("\\u00fd" , 'ý' ) ;
		mapping_u00.put("\\u00ff" , 'ÿ' ) ;

	}
	
	
	public static String convertFromPercentEncoding(String str) throws Exception
	{
		// convertit en ISO-Latin.
		fetchPercentEncodingTable();
		
		char[] ch = str.toCharArray();
		String perc_enc = "" ;
		String out = "" ;
		int compt = 0 ;
		boolean http_post = false ;
		
		for (char c : ch)
		{
			// pour examiner si on va commencer une seq de http post
			if (c == '%')
			{
				perc_enc = "" ;
				http_post = true ;
				compt = 3 ; 
			}
			// traite le caractère en fonction de la séquence.
			if (http_post)
			{
				perc_enc += c ;
				compt -- ;
				
				if (compt == 0)
				{
					out += mapping_percentEncoding.get(perc_enc);
					http_post = false ;
				}
			}
			else
			{
				perc_enc = "" ;
				out += c ;
			}
			// debug
			//System.out.println(c + " | " + compt + " | perc_enc = " + perc_enc + " | ok? " + http_post  + " | out = " + out);
		}
		
		return out ;
	}
	
	public static String convertFromHtmlCharacterRef(String str)
	{
		// convertit en ISO-Latin.
		fetchHtmlEncodingTable();
		
		char[] ch = str.toCharArray();
		String perc_enc = "" ;
		String out = "" ;
		boolean cut = false ;
		
		for (char c : ch)
		{
			// pour examiner si on va commencer une seq de &xxxx;
			if (c == '&')
			{
				perc_enc = "" ;
				cut = true ;
			}
			// traite le caractère en fonction de la séquence.
			if (cut)
			{
				perc_enc += c ;
			}
			else
			{
				perc_enc = "" ;
				out += c ;
			}
			// FIN ?
			if (c == ';')
			{
				cut = false ;
				out += mapping_htmlEncoding.get(perc_enc);
			}
			
			// debug
			//System.out.println(c + " | perc_enc = " + perc_enc + " | ok? " + cut  + " | out = " + out);
		}
		
		return out ;
	}

	
	
	

	public static String convertFromU00(String str)
	{
		fetchU00EncodingTable();
		
		char[] ch = str.toCharArray();
		String u00_enc = "" ;
		String out = "" ;
		int compt = 0 ;
		boolean u00 = false ;
		
		for (char c : ch)
		{
			// pour examiner si on va commencer une seq de u00
			if (c == '\\')
			{
				u00_enc = "" ;
				u00 = true ;
				compt = 6 ;  // l'encodeur /u00xx fait 6c
			}
			// traite le caractère en fonction de la séquence.
			if (u00)
			{
				u00_enc += c ;
				compt -- ;
				
				if (compt == 0)
				{					
					out += mapping_u00.get(u00_enc);
					u00 = false ;
				}
			}
			else
			{
				u00_enc = "" ;
				out += c ;
			}
			// debug
			//System.out.println(c + " | " + compt + " | u00_enc = " + u00_enc + " | ok? " + u00  + " | out = " + out);
		}
		
		return out ;
	}


	public static String normaliseForUrl(String str)
	{
		String str_out = "" ;
		str = str.toLowerCase(); // maybe changer de place
		char[] ch = str.toCharArray();
		String s = "" ;
		
		for (char c : ch)
		{		
			s += c ;
			
			// removes spaces and other shits
			if (s.equals(" ") || s.equals("'") 
					|| s.equals("(") || s.equals(")")
					|| s.equals(",") || s.equals(";")
				)
			{
				s = "-" ; // c = '_' ; 
			}
			
			str_out += s ;

			s = "" ;
		}		
		// check anti double "-"
		if (str_out.contains("--"))
		{
			str_out = str_out.replaceAll("--", "-");
		}
		
		return str_out ;
	}
	
	
	public static String normaliseAzertySpecialLetters(String str)
	{
		// !!!!! only works with all the letters typables with French (AZERTY) keyboards.
		// (even if they don't exist in French)
		
		String str_out = "" ;
		str = str.toLowerCase(); // maybe changer de place
		char[] ch = str.toCharArray();
		String s = "" ;
		
		for (char c : ch)
		{	
			s += c ;
			
			// voyelles
			if (s.equals("à") || s.equals("á") 
					|| s.equals("â") || s.equals("ä") 
					|| s.equals("ã"))
			{
				s = "a" ;
			}
			else if (s.equals("è") || s.equals("é") 
					|| s.equals("ê") || s.equals("ë") )
			{
				s = "e" ;
			}
			else if (s.equals("ì") || s.equals("í")
					|| s.equals("î") || s.equals("ï"))
			{
				s = "i" ;
			}
			else if (s.equals("ò") || s.equals("ó") 
					|| s.equals("ô") || s.equals("ö"))
			{
				s = "o" ;
			}
			else if (s.equals("ù") || s.equals("ú")
					|| s.equals("û") || s.equals("ü"))
			{
				s = "u" ;
			}
			else if (s.equals("ÿ"))
			{
				s = "y" ;
			}
			// e dans l'o
			else if (s.equals("œ"))
			{
				s = "oe" ;
			}
			
			// consonnes
			else if (s.equals("ç"))
			{
				s = "c" ;
			}
			else if (s.equals("ñ"))
			{
				s = "n" ;
			}
			
			str_out += s ;
			
			s = "" ;
		}
		return str_out ;
	}



}
