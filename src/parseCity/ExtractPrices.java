package parseCity;

import myJavaClasses.Misc;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExtractPrices {

    public static Prices extr(Document doc, boolean rent, boolean flat)
    {
        // first determine if it's prices for rent or for buy
        Element mainPriceContainer, priceRangeContainer;

        if (rent) { // rent
            mainPriceContainer = doc
                    .getElementsByClass("prices-summary__rent-prices").first();

            // only appartments : price container is same as main
            priceRangeContainer = mainPriceContainer;

        } else { // buy
            mainPriceContainer = doc
                    .getElementsByClass("prices-summary__sell-prices").first();

            // appts or houses : which one ?
            if (flat) // flat
                priceRangeContainer = mainPriceContainer
                        .getElementsByClass("prices-summary__apartment-prices").first();
            else // house
                priceRangeContainer = mainPriceContainer
                        .getElementsByClass("prices-summary__house-prices").first();
        }

//        Disp.anyTypeThenLine(mainPriceContainer);
//        Disp.anyTypeThenLine(priceRangeContainer);

        // >>> mean price
        String meanPrice_raw = priceRangeContainer
                .getElementsByClass("big-number").first()
                .text();
//        Disp.anyTypeThenLine(meanPrice_raw);
        String meanPrice_str = Misc.valueNormalise(
                meanPrice_raw.substring(0, meanPrice_raw.indexOf("€"))
        );
//        Disp.anyTypeThenLine(meanPrice_str);
        double meanPrice = Double.parseDouble(meanPrice_str);


        // >>> price range for 95% items
        // delimiters to parseCities prices range
        final String delimiter_lowest = "de";
        final String delimiter_highest = "à";
        // container with interval prices range
        String pricesRange = priceRangeContainer
                .getElementsContainingOwnText(delimiter_highest).first()
                .getElementsContainingOwnText(delimiter_lowest).first()
                .text();
//        Disp.anyTypeThenLine(pricesRange);
        String lowestPrice_raw = pricesRange.split("€")[0];
        String highestPrice_raw = pricesRange.split("€")[1];
//        Disp.anyTypeThenLine(lowestPrice_raw);
//        Disp.anyTypeThenLine(highestPrice_raw);
        String lowestPrice_str = Misc.removeNonNumericalCharsFromString(
                Misc.valueNormalise(lowestPrice_raw)
        );
        String highestPrice_str = Misc.removeNonNumericalCharsFromString(
                Misc.valueNormalise(highestPrice_raw)
        );
//        Disp.anyTypeThenLine(lowestPrice_str);
//        Disp.anyTypeThenLine(highestPrice_str);

        double lowestPrice = Double.parseDouble(lowestPrice_str);
        double highestPrice = Double.parseDouble(highestPrice_str);

        return new Prices(rent, flat, meanPrice, highestPrice, lowestPrice);
    }

}
