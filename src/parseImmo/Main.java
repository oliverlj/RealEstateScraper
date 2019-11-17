package parseImmo;

import handleVPN.ChangeIP;
import handleVPN.ChangePool;
import handleVPN.Country;
import myJavaClasses.*;
import org.jsoup.UncheckedIOException;
import org.jsoup.nodes.Document;
import parseCity.City;
import parseCity.ExtractLocalInfos;
import parseCity.ParseCity;
import parseDepartment.Department;
import parseDepartment.ParseDepartment;

import java.io.*;
import java.util.ArrayList;

public class Main {
    private static final String projectName = "RealEstateScraper" ;

    // urls
    private static final String url_main = "https://www.meilleursagents.com" ;
    private static final String url_sub = "/prix-immobilier" ;

    // folder names
    private static final String save_folder = "data_saved/";
    private static final String output_folder = "data_output/";
    // paths
    private static final String root_path = "/Users/c/Documents/Local Code/";
    private static final String save_path = root_path + projectName + "/" + save_folder;
    private static final String output_path = root_path + projectName + "/" + output_folder;

    // file names
    private static final String filename_cities_urls = "urls";
    private static final String filename_cities_list = "cities";
    // file extensions
    private static final String extension_save = ".immo";
    private static final String extension_csv = ".csv";
    private static final String NO_DATA = "null";

    private static final int nb_max_trys = 5 ; // number of tries before changing pool
    private static final int max_pool_change = 52; // at 53, starts again at the beginning (Australia)

    //////////////////////////////////////////////////////////

    public static void main_(String[] args)
            throws Country.CountryNotFoundException, Country.PoolNotFoundInCountryException
    {

        Country.initAllVPNCountries(); // inits objects in static arraylist
        ChangePool.displayBeginningCountryAndPool();

        for (int i=0 ; i < max_pool_change ; i++) {
            new ChangePool();
        }
    }



    public static void main(String[] args)
            throws Country.CountryNotFoundException, Country.PoolNotFoundInCountryException
    {
        Disp.shortMsgLine("ParseImmo", false);

        // some quick fixes before anything
        double start = System.currentTimeMillis(); // start counter
        SaveManager.setSavePath(save_path);

        Country.initAllVPNCountries(); // inits objects in static arraylist
        ChangePool.displayBeginningCountryAndPool();

        // restart infinitely until everything is scraped
        try {
            ArrayList<City> allCities = scrapeAllFrenchCities(); // includes writing cities to output_data/filename.csv when all is finished

            // finally, write cities as .csv to be exported to Excel
            writeCitiesAsCSV(filename_cities_list + extension_csv , allCities , true);

        } catch (Exception | UncheckedIOException e1) {
            Disp.exc("Exception level 1 : [ " + e1 + " ]");
//            Disp.exc(e.getCause() + " | " + e.getMessage());
            e1.printStackTrace();
            Disp.star();

            // now let's start again and again and again ... FOREVAH ;)
            try {
                main(args);
            } catch (Exception | UncheckedIOException e2) {
                main(args);

                Disp.exc("Exception level 2 : [ " + e2 + " ]");
//                Disp.exc(e1.getCause() + " | " + e1.getMessage());
                e2.printStackTrace();
                Disp.star();
            }
        }

        double end = System.currentTimeMillis(); // end counter
        System.out.println(":D :D :D ------------ GOOD JOB ! Total time : " + (end-start) + " ms ------------ :D :D :D");
    }


    private static ArrayList<City> scrapeAllFrenchCities() throws Exception
    {
        // little fix for encoding problems
//        EncodingCorrecter.refreshEncodingAtStartup("UTF-8");

        // 1. get cities urls from disk or from the net
        ArrayList<String> urls_cities;
        Disp.anyTypeThenLine(">>> Getting all cities urls from saved content.");

        // load list cities urls
        urls_cities = (ArrayList<String>) SaveManager.objectLoad
                (filename_cities_urls + extension_save , true);

        if (urls_cities == null) { // which means : if save file does not exist
            Disp.anyTypeThenStar(">>> Now scraping all cities urls from all departments.");

            // get urls of all 96 departments
            try {
                Document main_page = ParseHtml.fetchHtmlAsDocumentFromUrl(url_main + url_sub);
                ArrayList<String> urls_dpts = ParseDepartment.getUrls(main_page);

                // add all cities urls for each dpt in main list
                urls_cities = new ArrayList<>();
                for (String url_dpt : urls_dpts) {
                    String url_to_parse = url_main + url_dpt; // assemble partial url with main part
                    Department dpt = ParseDepartment.parseCities(url_to_parse);

                    urls_cities.addAll(dpt.getUrlsCities());
//                    Disp.anyTypeThenStar(dpt.getUrlsCities().size()); // just to check we never get null
                }
            } catch (Exception e) {
                Disp.exc("Can't download departments list containing urls to cities... Program can't start.");
            }

            // now write it to a file so we can recover it later
            SaveManager.objectSave(filename_cities_urls + extension_save , urls_cities);
        }


        // 2. parse cities from disk or from the net
        // parse main city list from urls
        ArrayList<City> cities;
        Disp.anyTypeThenLine(">>> Getting all cities from saved content.");

        // load list cities for doing only the ones that haven't been already done
        cities = (ArrayList<City>) SaveManager.objectLoad
                (filename_cities_list + extension_save , true);

        if (cities == null) { // which means : if save does not exist
            cities = new ArrayList<>();
            Disp.anyType(">>> Now scraping all cities from all departments.");
        } else {
            int actual_progress = cities.size();
            int total_cities = urls_cities.size();
            int remaining_cities = total_cities - actual_progress;
            Disp.anyType(">>> " + actual_progress + " / " + total_cities + " cities are already parsed.");
            Disp.anyType(">>> Will now parse " + remaining_cities + " remaining cities.");
        }

        Disp.htag(); Disp.htag(); Disp.htag();
        int nbTrys = 0;
        int nbDone = 0;
        int nbIPChanges = 0;
        boolean needsSave = false;

        // the loop ;)
        for (int index_city=0 ; index_city < urls_cities.size() ; index_city ++)
        {
            String url_city = urls_cities.get(index_city);
            String url_to_parse = url_main + url_city; // assemble partial url with main part

            if ( !City.exists(url_to_parse, cities) ) { // try to download it until it's done.

                try {
                    City city = ParseCity.parse(url_to_parse);
//                    Disp.anyType(city);
                    Disp.anyType(
                            ">>> City n°" + index_city + " : [ "
                                    + city.getName() + " ("
                                    + city.getPostalCodeAsDptNumber() + ") ] ——> done."
                    );
                    cities.add(city);

                    // success : resets try counter and increments done counter
                    nbTrys = 0;
                    nbDone++;
                    // puts the trigger on after a city has been scraped since last save
                    needsSave = true;

                    // then, display progress for cities BUT ONLY IF IT HAS NOT BEEN ALREADY PARSEDgit
                    Disp.progress("cities", nbDone, urls_cities.size());

                } catch (Exception | UncheckedIOException e) {
                    // here is da list of Exceptions that happened :
                    /*
                    - ArrayIndexOutOfBoundsException
                    - SocketTimeoutException
                    - SSLHandshakeException
                    - ConnectionException
                    - ConnectException
                    - UncheckedIOException
                    */

                    // failure : increments try counter
                    nbTrys ++;

                    // disp err msg
                    Disp.exc("Quota maximum reached for this IP -- Let's try again with another one ;)");
//                    Disp.exc(e.getCause() + " | " + e.getMessage());
                    e.printStackTrace();

                    // save actual progress only first time, only if changes have been made
                    if (nbTrys == 1 && needsSave)
                    {
                        Disp.star();
                        SaveManager.objectSave(filename_cities_list + extension_save, cities);
                        nbIPChanges ++; // increment for further count

                        // puts the trigger off until a new city gets scraped
                        needsSave = false;
                    }

                    // after too many request failures...
                    if (nb_max_trys >= nbTrys)
                        // first try to fix the problem by changing IP in the same pool.
                        new ChangeIP(nbIPChanges, nbTrys);
                    else {
                        // then try to switch to the next pool
                        new ChangePool();
                        // resets counters
                        nbTrys = 0;
                        nbIPChanges = 0;
                    }

                    index_city--; // stay on that level. Must be parsed
                }

//                Disp.separatorStar();
//                Disp.separatorLine();
                Disp.line();

            } else {
                // success : resets try counter and increment done counter
                nbTrys = 0;
                nbDone++;
            }

        } // end of main for loop

        // save actual progress when download has finished too.
//        if (cities.size() == urls_cities.size() - 2)
        if (needsSave)
            SaveManager.objectSave(filename_cities_list + extension_save, cities);

        return cities;
    }

    //////////////////////////////////////////////////////////

    private static void writeCitiesAsCSV(String filename, ArrayList<City> citiesToWrite, boolean with_headers)
    { /// #### ATTENTION : version spécifique en attendant.
//
        String save_to = output_path + filename ;
        System.out.println(save_to);

        try {
            BufferedWriter bw = ReadWriteFile.outputWriter(save_to);

            // I) makes header first
            if (with_headers) {
                String[] headers = {
                        // main infos
//                        "url",
                        /*"postal code"*/"dpt number",
                        "city",
                        // prices
                        "prix m2 moyen appt",
                        "loyer mensuel m2 moyen",
                        "evolution prix immo 1 an",
                        "evolution prix immo 2 ans",
                        "evolution prix immo 5 ans",
                        // local infos
                        "population",
                        "croissance demo",
                        "age median",
                        "densite pop",
                        "nombre total logements",
                        "part logements vacants",
                        "part menages locataires residence principale",
                        "revenu annuel median par menage",
                        "taux chomage 15-64 ans",
                        "evol taux chomage",
                        "evol taux activite"
                };
                writeLine(bw, headers, true);

//                for (String header : headers) { bw.write(header + ","); }
//                bw.newLine();
            }

            // II) then writes data cell by cell
            for (City city : citiesToWrite)
            {
//                Disp.anyTypeThenLine(city);

                String[] main_attrs = {
                        // main infos
//                        city.getUrl(),
                        city.getPostalCodeAsDptNumber(),
                        city.getName(),
                };

                String mediumPrice, mediumMonthlyRent, trend1y, trend2y, trend5y;
                try { mediumPrice = city.getPricesBuyAppt().getMeanAsAmount(); } catch (NullPointerException e) { mediumPrice = null; }
                try { mediumMonthlyRent = city.getPricesRentAppt().getMeanAsAmount(); } catch (NullPointerException e) { mediumMonthlyRent = null; }
                try { trend1y = Misc.valueNormalise(city.getTrends().get("1 an")); } catch (NullPointerException e) { trend1y = null; }
                try { trend2y = Misc.valueNormalise(city.getTrends().get("2 ans")); } catch (NullPointerException e) { trend2y = null; }
                try { trend5y = Misc.valueNormalise(city.getTrends().get("5 ans")); } catch (NullPointerException e) { trend5y = null; } // (: (: NOW I KNOW WHY :) :)
                String[] prices_attrs = { mediumPrice, mediumMonthlyRent, trend1y, trend2y, trend5y };

                // --> gets values
                // population
                String population = city.getLocalPop().get(ExtractLocalInfos.label_population);
                String rateDemograhicGrowth = city.getLocalPop().get(ExtractLocalInfos.label_rateDemographicGrowth);
                String medianAge = city.getLocalPop().get(ExtractLocalInfos.label_medianAge);
                String popDensity = city.getLocalPop().get(ExtractLocalInfos.label_popDensity);
                // homes
                String totalNbHomes = city.getLocalHomes().get(ExtractLocalInfos.label_totalNbHomes);
                String rateVacantHomes = city.getLocalHomes().get(ExtractLocalInfos.label_rateVacantHomes);
                String rateMainHomeRenting = city.getLocalHomes().get(ExtractLocalInfos.label_rateMainHomeRenting);
                // revenue / employment
                String medianAnnualRevenue = city.getLocalRevenueEmpl().get(ExtractLocalInfos.label_medianAnnualRevenue);
                String unemploymentRate15To64 = city.getLocalRevenueEmpl().get(ExtractLocalInfos.label_unemploymentRate15To64);
                String unemploymentRateEvol = city.getLocalRevenueEmpl().get(ExtractLocalInfos.label_unemploymentRateEvol);
                String employmentRateEvol = city.getLocalRevenueEmpl().get(ExtractLocalInfos.label_employmentRateEvol);

                // --> treats values that need lil formatting before exporting to excel
                // population
                try { population = Misc.valueNormalise(population.replace(" habitants", "")); } catch (NullPointerException e) { population = null; }
                try { rateDemograhicGrowth = Misc.valueNormalise(rateDemograhicGrowth); } catch (NullPointerException e) { rateDemograhicGrowth = null; }
                try { medianAge = medianAge.replace(" ans", ""); } catch (NullPointerException e) { medianAge = null; }
                try { popDensity = Misc.valueNormalise(popDensity.replace(" hab. / km²", "")); } catch (NullPointerException e) { popDensity = null; }
                // homes
                try { totalNbHomes = Misc.valueNormalise(totalNbHomes.replace(" logements", "")); } catch (NullPointerException e) { totalNbHomes = null; }
                try { rateVacantHomes = Misc.valueNormalise(rateVacantHomes); } catch (NullPointerException e) { rateVacantHomes = null; }
                try { rateMainHomeRenting = Misc.valueNormalise(rateMainHomeRenting); } catch (NullPointerException e) { rateMainHomeRenting = null; }
                // revenue / employment
                try { medianAnnualRevenue = Misc.valueNormalise(Misc.removeNonNumericalCharsFromString(medianAnnualRevenue)); } catch (NullPointerException e) { medianAnnualRevenue = null; }
                try { unemploymentRate15To64 = Misc.valueNormalise(unemploymentRate15To64); } catch (NullPointerException e) { medianAnnualRevenue = null; }
                try { unemploymentRateEvol = unemploymentRateEvol.replace(" pt.", ""); } catch (NullPointerException e) { unemploymentRateEvol = null; }
                try { employmentRateEvol = employmentRateEvol.replace(" pt.", ""); } catch (NullPointerException e) { employmentRateEvol = null; }

                // --> inserts values into array for exporting
                String[] local_attrs = {
                        // population
                        population,
                        rateDemograhicGrowth,
                        medianAge,
                        popDensity,
                        // homes
                        totalNbHomes,
                        rateVacantHomes,
                        rateMainHomeRenting,
                        // revenue / employment
                        medianAnnualRevenue,
                        unemploymentRate15To64,
                        unemploymentRateEvol,
                        employmentRateEvol
                };

                writeLine(bw, main_attrs, false);
                writeLine(bw, prices_attrs, false);
                writeLine(bw, local_attrs, true);

//                for (String attr : main_attrs) { bw.write(attr + ","); }
//                for (String attr : prices_attrs) { bw.write(attr + ","); }
//                for (String attr : local_attrs) { bw.write(attr + ","); }
//                bw.newLine();
            }

            bw.close();
            Disp.shortMsgStar("WRITING TO .csv FILE FINISHED", true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeLine(BufferedWriter bw, String[] attrsToWrite, boolean endOfLine) {
        try
        {
            int attr_index = 0;
            for (String attr : attrsToWrite)
            {
                if (attr != null) bw.write(attr);
                else bw.write(NO_DATA);

                attr_index ++;
                if (attr_index < attrsToWrite.length) {
                    bw.write(",");
                } else if (! endOfLine) {
                    bw.write(",");
                }
            }
            if (endOfLine) bw.newLine();

        } catch (IOException e) { Disp.exc(e); }
    }

}
