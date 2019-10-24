package parseDepartment;

import org.jsoup.nodes.Document;
import parseCity.City;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {

//    private Document doc ;
    private String url ; // WITHOUT suffix /villes/
    private String name ;
    private String number ;
    private ArrayList<String> urlsCities = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();

    public Department(
            Document doc, // is now useless (but flemme de changer l'instantiation)
            String url,
            String name,
            String number
    ) {
//        this.doc = doc;
        this.url = url;
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Department: " +
//                "doc=" + doc +
//                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", urlsCities=" + urlsCities +
                ", cities=" + cities;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<String> getUrlsCities() {
        return urlsCities;
    }

    public void setUrlsCities(ArrayList<String> urlsCities) {
        this.urlsCities = urlsCities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
