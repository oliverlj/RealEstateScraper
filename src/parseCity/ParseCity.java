package parseCity;

import myJavaClasses.Disp;
import myJavaClasses.ParseHtml;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.util.LinkedHashMap;

public class ParseCity {


    // main method for  city from its FULL url
    public static City parse(String url) throws Exception
    {
        Disp.anyType(">>> Scraping city at [ " + url + " ] ...");
        Document doc = ParseHtml.fetchHtmlAsDocumentFromUrl(url);

//        if (doc == null) throw new NullPointerException();

        // >>> name of the place + postal code
        String name, postalCode;
        String title = doc.title();
        title = title.replaceAll("Prix immobilier ","");
        title = title.replaceAll("\\)", "");
        String[] spl = title.split( " \\(");
        name = spl[0];
        postalCode = spl[1];

        // >>>  buy
        Prices pricesBuyAppt, pricesBuyHouse;
        try {
            pricesBuyAppt = ExtractPrices.extr(doc, false, true);
        } catch (NullPointerException e) { pricesBuyAppt = null; }
        try {
            pricesBuyHouse = ExtractPrices.extr(doc, false, false);
        } catch (NullPointerException e) { pricesBuyHouse = null; }

        // >>> rent
        Prices pricesRentAppt, pricesRentHouse;
        try {
            pricesRentAppt = ExtractPrices.extr(doc, true, true);
        } catch (NullPointerException e) { pricesRentAppt = null; }
        /*try { // rent houses : doesn't exist on website
            pricesRentHouse = ExtractPrices.extr(doc, true, false);
        } catch (NullPointerException e) { pricesRentHouse = null; }*/

        // >>> evolution for last years
        LinkedHashMap<String, String> trends = ExtractTrends.extr(doc);

        // >>> all local infos
        LinkedHashMap<String, String> allLocalInfos, localPop, localHomes, localRevenueEmpl;
        try {
            allLocalInfos = ExtractLocalInfos.all(doc);
            // select only intersting ones and sort them by category
            localPop = ExtractLocalInfos.localPop(allLocalInfos);
            localHomes = ExtractLocalInfos.localHomes(allLocalInfos);
            localRevenueEmpl = ExtractLocalInfos.localRevenueEmployment(allLocalInfos);

        } catch (NullPointerException npe) {
            // set every local info to null (case dpt parsed as city)
            allLocalInfos = null;
            localPop = null;
            localHomes = null;
            localRevenueEmpl = null;
        }


        // ### creation and debug
        City city = new City(
                doc, url, name,
                postalCode,
                pricesBuyAppt, pricesBuyHouse,
                pricesRentAppt, //pricesRentHouse,
                trends,
                allLocalInfos,
                localPop, localHomes, localRevenueEmpl
        );
//        Disp.anyTypeThenLine(city);
        return city;
    }

}
