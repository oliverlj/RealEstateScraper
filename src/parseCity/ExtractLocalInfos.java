package parseCity;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;

public class ExtractLocalInfos {

    // might change on the website...
    // !!! IMPORTANT TO KEEP THIS VALUE UPDATED
    // !!! MUST TAKE FORMAT : "(YYYY-YYYY)"
    private final static String datesInterval = "(2006-2011)";
    // might probably not change on the website.
    // !!! MUST BE KEPT AS IS
    // population
    public final static String label_population = "Population";
    public final static String label_popDensity = "Densité de la population (nombre d'habitants au km²)";
    public final static String label_medianAge = "Age médian";
    public final static String label_rateDemographicGrowth = "Croissance démographique" + " " + datesInterval;
    // homes
    public final static String label_totalNbHomes = "Nombre total de logements";
    public final static String label_rateVacantHomes = "Part des logements vacants";
    public final static String label_rateMainHomeRenting = "Part des ménages locataires de leur résidence principale";
    // revenue & employment
    public final static String label_medianAnnualRevenue = "Revenu annuel médian par ménage";
    public final static String label_unemploymentRate15To64 = "Taux de chômage des 15 à 64 ans";
    public final static String label_unemploymentRateEvol = "Evolution du taux de chômage" + " " + datesInterval;
    public final static String label_employmentRateEvol = "Evolution du taux d'activité" + " " + datesInterval;


    public static LinkedHashMap<String, String> all(Document doc)
    {
        Element mainLocalContainer = doc
                .getElementById("local_stats");
//        Disp.anyTypeThenLine(mainLocalContainer);

        Elements localContainerSections = mainLocalContainer
                .getElementsByClass("accordion-panel container--row accordion-panel--price");
//        Disp.anyTypeThenLine(localContainerSections);

        LinkedHashMap<String, String> localInfos = new LinkedHashMap<>();
        for (Element el : localContainerSections) {
//            Disp.anyTypeThenLine(el);

            Element section = el.getElementsByTag("tbody").first();
//            Disp.anyTypeThenLine(section);

            Elements pairs = section.getElementsByTag("tr");

            // @TO-DO finish ;)
            for (Element info : pairs) {
//                Disp.anyTypeThenLine(info);

                Elements infoCells = info.getElementsByTag("td");
//                Disp.anyTypeThenLine(infoCells);

                String infoName_raw = infoCells.get(0).text().trim();
                String infoValue_raw = infoCells.get(1).text().trim();
//                Disp.anyTypeThenLine(infoName_raw + " | " + infoValue_raw);
//
                infoValue_raw = infoValue_raw.replaceAll("," , "."); // quick fix for , and .
                localInfos.put(infoName_raw, infoValue_raw);
            }
        }
        return localInfos;
    }


    private static LinkedHashMap<String, String> extractSelectedLabelsFromLocalInfos(
            LinkedHashMap<String, String> allLocalInfos,
            String[] labels
    ) {
        LinkedHashMap<String, String> infos = new LinkedHashMap<>();
        for (int i=0 ; i<labels.length ; i++) {
            String label = labels[i];
            String value = allLocalInfos.get(label);

            // quick fixes
            /*
            if (label.equals(label_population)) value = value.replace(" habitants", "");
            else if (label.equals(label_medianAge)) value = value.replace(" ans", "");
            else if (label.equals(label_popDensity)) value = value.replace(" hab. / km²", "");
            else if (label.equals(label_totalNbHomes)) value = value.replace(" logements²", "");
            else if (label.equals(label_medianAnnualRevenue)) value = value.replace(" €", "");
            else if (label.equals(label_employmentRateEvol)) value = value.replace(" pt.", "");
            else if (label.equals(label_unemploymentRateEvol)) value = value.replace(" pt.", "");
            */

//            value = value.replaceAll("," , "."); // quick fix for , and .
            infos.put(label, value);
        }
        return infos;
    }


    protected static LinkedHashMap<String, String> localPop(LinkedHashMap<String, String> allLocalInfos)
    {
        String[] labels_localPop = {
                label_population,
                label_popDensity,
                label_medianAge,
                label_rateDemographicGrowth
        };
        LinkedHashMap<String, String> localPop = extractSelectedLabelsFromLocalInfos(allLocalInfos, labels_localPop);
        return localPop;
    }

    protected static LinkedHashMap<String, String> localHomes(LinkedHashMap<String, String> allLocalInfos)
    {
        String[] labels_localHomes = {
                label_totalNbHomes,
                label_rateVacantHomes,
                label_rateMainHomeRenting
        };
        LinkedHashMap<String, String> localHomes = extractSelectedLabelsFromLocalInfos(allLocalInfos, labels_localHomes);
        return localHomes;
    }

    protected static LinkedHashMap<String, String> localRevenueEmployment(LinkedHashMap<String, String> allLocalInfos)
    {
        String[] labels_localRevenueEmpl = {
                label_medianAnnualRevenue,
                label_unemploymentRate15To64,
                label_unemploymentRateEvol,
                label_employmentRateEvol
        };
        LinkedHashMap<String, String> localRevenueEmpl = extractSelectedLabelsFromLocalInfos(allLocalInfos, labels_localRevenueEmpl);
        return localRevenueEmpl;
    }


}
