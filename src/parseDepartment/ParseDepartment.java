package parseDepartment;

import myJavaClasses.Disp;
import myJavaClasses.ParseHtml;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParseDepartment {


    public static ArrayList<String> getUrls(Document doc)
    {
        ArrayList<String> url_out = new ArrayList<>();

        Element dptsContainer = doc
                .getElementById("subregion_price_table");
        Elements dpts = dptsContainer
                .getElementsByTag("tbody").first()
                .getElementsByTag("a");

        for (Element dpt : dpts) {
            String url = dpt
                    .attr("href");
            // quick fix for paris-75000 --> 75 (must be turned in dpt number)
            url = url.replaceAll("000", "");

            System.out.println(url);
            url_out.add(url);
        }
        Disp.line();

        return url_out ;
    }


    // main method for  dpt from its FULL url
    public static Department parseCities(String url) throws Exception
    {
        final String url_to_parse = url + "villes/";

        Disp.anyTypeThenLine("*** Scraping urls of cities from [" + url_to_parse + "] ...");
        Document doc = ParseHtml.fetchHtmlAsDocumentFromUrl(url_to_parse);

//        if (doc == null) throw new Exception();

        // >>> name of the department
        String title = doc.title();
        title = title.replaceAll("Liste des villes","");
        title = title.replaceAll(" : ", "");
        String name = title;

        // >>> department number
        String number = getDptNumberFromUrl(url);

        // >>> cities
        ArrayList<String> citiesUrls = new ArrayList<>();

        Elements citiesUrlsContainers = doc.getElementsByClass("container--row container--wrap")
                .first()
                .getElementsByClass("list-link");

        for (Element el : citiesUrlsContainers)
        {
            Elements cities_links = el
                    .getElementsByTag("a");

            for (Element city_link : cities_links)
            {
                String city_url = city_link.attr("href");
//                Disp.anyTypeThenLine(city_url);

//            String full_city_url = url_main + city_url;

                citiesUrls.add(city_url);
            }
        }

        // ### creation and debug
        Department dpt = new Department(doc, url, name, number);
        dpt.setUrlsCities(citiesUrls);
        // add citiesUrls to the dpt for further parsing
        //////
//        Disp.anyTypeThenLine(dpt);
        return dpt;
    }


    private static String getDptNumberFromUrl(String url)
    {
        String[] url_spl = url.split("/");
        String name_and_nb = url_spl[url_spl.length - 1];
        String[] name_and_nb_spl = name_and_nb.split("-");
        String number = name_and_nb_spl[name_and_nb_spl.length - 1];
        return number;
    }
}
