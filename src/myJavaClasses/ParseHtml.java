package myJavaClasses;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ParseHtml
{
	//////////////////////////////////////////////////////////////////

	public static Document fetchHtmlAsDocumentFromLocalFile(String full_path) throws IOException
	{
		// *** A TESTER
		File in = new File(full_path);
		Document doc = Jsoup.parse(in, null);
		return doc ;
	}

	public static Document fetchHtmlAsDocumentFromUrl(String url) throws Exception
	{
//		try {
			Document doc = Jsoup.connect(url).maxBodySize(6500000).get();
			// sets to 6 500 000 bytes = 6.5 MB
			if (doc == null)
				throw new Exception("!!! ERROR WHILE FETCHING FILE AT [ " + url + " ] !!!");
			else
				return doc;
//		} catch (Exception e) {
//			Disp.exc(e);
//			return null ;
//		}
	}
}
