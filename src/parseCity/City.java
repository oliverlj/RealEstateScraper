package parseCity;

import myJavaClasses.Misc;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class City implements Serializable {

//    private Document doc;
    private String url, name, postalCode;
    private Prices pricesBuyAppt, pricesBuyHouse, pricesRentAppt/*, pricesRentHouse */;
    private LinkedHashMap<String, String> trends, allLocalInfos, localPop, localHomes, localRevenueEmpl;

    // construc
    public City(
            Document doc, // is now useless (but flemme de changer l'instantiation)
            String url,
            String name,
            String postalCode,
            Prices pricesBuyAppt,
            Prices pricesBuyHouse,
            Prices pricesRentAppt,
//            Prices pricesRentHouse,
            LinkedHashMap<String, String> trends,
            LinkedHashMap<String, String> allLocalInfos,
            LinkedHashMap<String, String> localPop,
            LinkedHashMap<String, String> localHomes,
            LinkedHashMap<String, String> localRevenueEmpl
    ) {
//        this.doc = doc;
        this.url = url;
        this.name = name;
        this.postalCode = postalCode;
        this.pricesBuyAppt = pricesBuyAppt;
        this.pricesBuyHouse = pricesBuyHouse;
        this.pricesRentAppt = pricesRentAppt;
//        this.pricesRentHouse = pricesRentHouse;
        this.trends = trends;
        this.allLocalInfos = allLocalInfos;
        this.localPop = localPop;
        this.localHomes = localHomes;
        this.localRevenueEmpl = localRevenueEmpl;
    }

    @Override
    public String toString() {
        return "City: " +
//                "doc=" + doc +
//                "   url='" + url + '\'' +
                "   postalCode='" + postalCode + '\'' +
                "   name='" + name + '\'' + "\n" +
                "   pricesBuyAppt=" + pricesBuyAppt + "\n" +
                "   pricesBuyHouse=" + pricesBuyHouse + "\n" +
                "   pricesRentAppt=" + pricesRentAppt + "\n" +
//                "   pricesRentHouse=" + pricesRentHouse + "\n" +
                "   trends=" + trends + "\n" +
//                 "  allLocalInfos=" + allLocalInfos +
                "   localPop=" + localPop + "\n" +
                "   localHomes=" + localHomes + "\n" +
                "   localRevenueEmpl=" + localRevenueEmpl;
    }

    public static boolean exists(String url, ArrayList<City> cities) {
//        if (cities != null) {
            for (City city : cities) {
                String url_city = city.getUrl();
                if (url_city.equals(url)) return true;
            }
            return false;
//        } else {
//            return false;
//        }
    }

    /*// display
        @Override
        public String toString() {
            String[] arr = {
                    // main attributes
    //                this.getUrl(),
    //                this.getName(),
    //                this.getPostalCode(),
                    // prices
                    String.valueOf(this.getPricesBuyAppt()),
                    String.valueOf(this.getPricesBuyHouse()),
                    String.valueOf(this.getPricesRentAppt()),
                    String.valueOf(this.getPricesRentHouse()),
                    // trends
                    String.valueOf(Misc.jsonifyArrayIntoString("TRENDS",
                            Misc.jsonifyLinkedHashMapIntoStringArray(this.getTrends()),
                            true
                    )),
                    // all the local infos
                    *//* String.valueOf(Misc.jsonifyArrayIntoString("ALL_LOCAL_INFOS",
                         Misc.jsonifyLinkedHashMapIntoStringArray(this.getAllLocalInfos()),
                         true
                 )),*//*
                // population
                String.valueOf(Misc.jsonifyArrayIntoString("POPULATION",
                        Misc.jsonifyLinkedHashMapIntoStringArray(this.localPop),
                        true
                )),
                // homes (logements)
                String.valueOf(Misc.jsonifyArrayIntoString("HOMES",
                        Misc.jsonifyLinkedHashMapIntoStringArray(this.localHomes),
                        true
                )),
                // revenue / employment
                String.valueOf(Misc.jsonifyArrayIntoString("REVENUE_EMPLOYMENT",
                        Misc.jsonifyLinkedHashMapIntoStringArray(this.localRevenueEmpl),
                        true
                ))
        };

        String node_name = this.getName() + " (" + this.getPostalCode() + ")";
        return Misc.jsonifyArrayIntoString(node_name, arr, true);
    }
*/
    // getters and setters
//    public Document getDoc() {
//        return doc;
//    }

//    public void setDoc(Document doc) {
//        this.doc = doc;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Prices getPricesBuyAppt() {
        return pricesBuyAppt;
    }

    public void setPricesBuyAppt(Prices pricesBuyAppt) {
        this.pricesBuyAppt = pricesBuyAppt;
    }

    public Prices getPricesBuyHouse() {
        return pricesBuyHouse;
    }

    public void setPricesBuyHouse(Prices pricesBuyHouse) {
        this.pricesBuyHouse = pricesBuyHouse;
    }

    public Prices getPricesRentAppt() {
        return pricesRentAppt;
    }

    public void setPricesRentAppt(Prices pricesRentAppt) {
        this.pricesRentAppt = pricesRentAppt;
    }

//    public Prices getPricesRentHouse() {
//        return pricesRentHouse;
//    }

//    public void setPricesRentHouse(Prices pricesRentHouse) {
//        this.pricesRentHouse = pricesRentHouse;
//    }

    public LinkedHashMap<String, String> getLocalPop() {
        return localPop;
    }

    public void setLocalPop(LinkedHashMap<String, String> localPop) {
        this.localPop = localPop;
    }

    public LinkedHashMap<String, String> getLocalHomes() {
        return localHomes;
    }

    public void setLocalHomes(LinkedHashMap<String, String> localHomes) {
        this.localHomes = localHomes;
    }

    public LinkedHashMap<String, String> getLocalRevenueEmpl() {
        return localRevenueEmpl;
    }

    public void setLocalRevenueEmpl(LinkedHashMap<String, String> localRevenueEmpl) {
        this.localRevenueEmpl = localRevenueEmpl;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPostalCodeAsDptNumber() {
        return postalCode.substring(0, 2);
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LinkedHashMap<String, String> getTrends() {
        return trends;
    }

    public void setTrends(LinkedHashMap<String, String> trends) {
        this.trends = trends;
    }

    public LinkedHashMap<String, String> getAllLocalInfos() {
        return allLocalInfos;
    }

    public void setAllLocalInfos(LinkedHashMap<String, String> allLocalInfos) {
        this.allLocalInfos = allLocalInfos;
    }
}
