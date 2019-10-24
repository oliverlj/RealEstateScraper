package parseCity;

import myJavaClasses.Disp;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;

public class ExtractTrends {



    public static LinkedHashMap<String, String> extr(Document doc)
    {
        Elements pricesEvolTrends = doc
                .getElementsByClass("prices-evolution__trends margin-bottom-double")
                .first()
                .getElementsByAttributeValueContaining("class", "prices-evolution__trend evolution__trend--");

        LinkedHashMap<String, String> trends = new LinkedHashMap<>();
        for (Element el : pricesEvolTrends)
        {
            String trendPeriod_raw = el
                    .getElementsByClass("trend__period").first()
                    .text();

            String trendValue_raw = el
                    .getElementsByAttributeValueContaining("class", "trend__value trend__value--").first()
                    .text()
                    .replaceAll("," , ".") // quick fix for , to .
            ;

//            Disp.anyTypeThenLine(trendPeriod_raw + " | " + trendValue_raw);
            trends.put(trendPeriod_raw, trendValue_raw);
        }
        return trends;
    }
}
