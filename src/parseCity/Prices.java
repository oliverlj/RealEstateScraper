package parseCity;

import java.io.Serializable;

import static myJavaClasses.Misc.jsonifyArrayIntoString;

public class Prices implements Serializable {

    // type : false == buy, true == rent
    private boolean rent;
    // type 2 : false == house, true == flat
    private boolean flat;
    // medium price
    private double mean;
    // upper and lower end of 95% items
    private double highest;
    private double lowest;

/*    @Override
    public String toString() {
        String[] arr = {
                String.valueOf(this.getHighest()) + " €",
                String.valueOf(this.getMean()) + " €",
                String.valueOf(this.getLowest() + " €"),
        };
        String out = "";
        // rent or buy ?
        if (this.isRent()) out += jsonifyArrayIntoString("RENT", arr, true);
        else out += jsonifyArrayIntoString("BUY", arr, true);
        // other attribs
        return out;
    }*/

    @Override
    public String toString() {
        String out = "Prices{";
        if (rent) out += "rent"; else out += "buy";
        if (flat) out += " flat"; else out += " house";
        out +=
                ": lowest=" + lowest +
                ", mean=" + mean +
                ", highest=" + highest +
                '}';
        return out;
    }

    public Prices(boolean rent, boolean flat, double mean, double highest, double lowest) {
        this.rent = rent;
        this.flat = flat;
        this.mean = mean;
        this.highest = highest;
        this.lowest = lowest;
    }

    public double getMean() {
        return this.mean;
    }

    public String getMeanAsAmount() {
        return String
                .valueOf(this.getMean())
                // quick fixes for csv export
                .replace(".0" , "")
//                .replace("." , ",") // this one would better be done later, directly in excel.
        ;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getHighest() {
        return this.highest;
    }

    public void setHighest(double highest) {
        this.highest = highest;
    }

    public double getLowest() {
        return this.lowest;
    }

    public void setLowest(double lowest) {
        this.lowest = lowest;
    }

    public boolean isRent() {
        return this.rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public boolean isFlat() {
        return this.flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }


}
