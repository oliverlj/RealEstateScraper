package handleVPN;

import myJavaClasses.Disp;

import java.util.ArrayList;
import java.util.Arrays;


public class Country {

    private static ArrayList<Country> countries = new ArrayList<>();

    private String name;
    private String[] pools;
    private boolean hisOwnPool;

    private Country(String name, String[] pools) {
        this.name = name;
        this.pools = pools;

        this.hisOwnPool = this.pools.length == 0;

//        countries.add(this);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", pools=" + Arrays.toString(pools) +
                '}';
    }


    public static int returnCountryIndexFromName(String name_country) {
        for (int i = 0; i < getCountries().size() ; i++) {
            Country c = getCountries().get(i);

            if (c.getName().equals(name_country)) return i;
        }
        return -1; // if -1 is obtained, exception is thrown (country not found)
    }


    public static int returnPoolIndexFromName(String name_country, String name_pool) {
        Country c;
        try {
            c = getCountries().get(returnCountryIndexFromName(name_country));
        } catch (Exception e) {
            return -1;
        }

        if (c.hisOwnPool) return 0; // if there is no pool, give 0 to show it's oké
        else {
            for (int i=0 ; i < c.getPoolsNb() ; i++) {
                String p = c.getPools()[i];

                if (p.equals(name_pool)) return i;
            }
            return -1; // pool not found in country
        }
    }


    public static int getTotalNbCountriesAndPools()
    {
        int total_nb = 0 ;
        for (Country c : getCountries())
        {
            total_nb ++;
            total_nb += c.getPoolsNb();
        }
        return total_nb;
    }


    public static ArrayList<Country> getCountries() {
        return countries;
    }

    public String getName() {
        return name;
    }

    public String[] getPools() {
        return pools;
    }

    public int getPoolsNb() {
        return pools.length;
    }

    public boolean isHisOwnPool() {
        return hisOwnPool;
    }


    public static boolean isInList(String[] list, Country to_scan)
    {
        for (String c_name : list) {
            if (c_name.equals(to_scan)) return true;
        }
        return false;
    }


    public static void initAllVPNCountries()
    {
//        String[] pools_auto = {
//        }; countries.add(new Country("Automatic", pools_auto));

        String[] pools_AU = {
                "AU Melbourne",
                "AU Perth",
                "AU Sydney"
        }; countries.add(new Country("Australia", pools_AU));
        String[] pools_AT = {
        }; countries.add(new Country("Austria", pools_AT));
        String[] pools_BE = {
        }; countries.add(new Country("Belgium", pools_BE));
        String[] pools_BR = {
        }; countries.add(new Country("Brazil", pools_BR));
        String[] pools_CA = {
                "CA Montreal",
                "CA Toronto",
                "CA Vancouver"
        }; countries.add(new Country("Canada", pools_CA));
        String[] pools_CZ = {
        }; countries.add(new Country("Czech Republic", pools_CZ));
        String[] pools_DK = {
        }; countries.add(new Country("Denmark", pools_DK));
        String[] pools_FI = {
        }; countries.add(new Country("Finland", pools_FI));
        String[] pools_FR = {
        }; countries.add(new Country("France", pools_FR));
        String[] pools_DE = {
                "DE Berlin",
                "DE Frankfurt"
        }; countries.add(new Country("Germany", pools_DE));
        String[] pools_HK = {
        }; countries.add(new Country("Hong Kong", pools_HK));
        String[] pools_HU = {
        }; countries.add(new Country("Hungary", pools_HU));
        String[] pools_IN = {
        }; countries.add(new Country("India", pools_IN));
        String[] pools_IE = {
        }; countries.add(new Country("Ireland", pools_IE));
        String[] pools_IL = {
        }; countries.add(new Country("Israel", pools_IL));
        String[] pools_IT = {
        }; countries.add(new Country("Italy", pools_IT));
        String[] pools_JP = {
        }; countries.add(new Country("Japan", pools_JP));
        String[] pools_LU = {
        }; countries.add(new Country("Luxembourg", pools_LU));
        String[] pools_MX = {
        }; countries.add(new Country("Mexico", pools_MX));
        String[] pools_NL = {
        }; countries.add(new Country("Netherlands", pools_NL));
        String[] pools_NZ = {
        }; countries.add(new Country("New Zealand", pools_NZ));
        String[] pools_NO = {
        }; countries.add(new Country("Norway", pools_NO));
        String[] pools_PL = {
        }; countries.add(new Country("Poland", pools_PL));
        String[] pools_RO = {
        }; countries.add(new Country("Romania", pools_RO));
        String[] pools_SG = {
        }; countries.add(new Country("Singapore", pools_SG));
        String[] pools_ZA = {
        }; countries.add(new Country("South Africa", pools_ZA));
        String[] pools_ES = {
        }; countries.add(new Country("Spain", pools_ES));
        String[] pools_SE = {
        }; countries.add(new Country("Sweden", pools_SE));
        String[] pools_CH = {
        }; countries.add(new Country("Switzerland", pools_CH));
        String[] pools_AE = {
        }; countries.add(new Country("UAE", pools_AE));
        String[] pools_UK = {
                "UK London",
                "UK Manchester",
                "UK Southampton"
        }; countries.add(new Country("United Kingdom", pools_UK));
        String[] pools_US = {
                "US Atlanta",
                "US California",
                "US Chicago",
                "US Denver",
                "US East",
                "US Florida",
                "US Houston",
                "US Las Vegas",
                "US New York City",
                "US Seattle",
                "US Silicon Valley",
                "US Texas",
                "US Washington DC",
                "US West"
        }; countries.add(new Country("United States", pools_US));
    }


    public static class CountryNotFoundException extends Exception {
        CountryNotFoundException(String name_country) {
            Disp.exc("Country [ " + name_country + " ] does not exist, please put a valid one ;)");
        }
    }

    public static class PoolNotFoundInCountryException extends Exception {
        PoolNotFoundInCountryException(String name_country, String name_pool) {
            Disp.exc("Pool [ " + name_pool + " ] does not exist in country [ " + name_country + " ], please put a valid one ;)");
        }
    }

}
