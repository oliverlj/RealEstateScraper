package handleVPN;

import myJavaClasses.Disp;

import java.util.concurrent.TimeUnit;


public class ChangePool extends HandleVPN {

    // to customise at which country to begin if you want to start from something else than Auto country ;)
    private static final int start_country = Country.returnCountryIndexFromName(name_country); // if gives -1, error in name
    private static final int start_pool = Country.returnPoolIndexFromName(name_country, name_pool); // if gives -1, error in name
//    private static final int stop_country = Main.max_pool_change; // makes a perfect loop to the start ;)

    private static int index_country = start_country;
    private static int index_pool = start_pool;


    public ChangePool() throws Country.CountryNotFoundException, Country.PoolNotFoundInCountryException
    {
        Runtime runtime = Runtime.getRuntime();
//        Disp.anyType("counter: " + counter);

        // get and display current country | pool pair
        Disp.anyType("current country: " + getCurrentCountry());
        Disp.anyType("current pool: " + getCurrentPool());

        boolean goBackToTop = getCurrentPool().equals("US West");
//                && getCurrentCountry().getName().equals("United States")

        // ------------- make incrementations to go to the NEXT ( country | pool ) pair !!!

        // go to next country if ( || ) :
        // - current country contains no pool
        // - last pool from the country reached
        // else : go to next pool.
        if (goBackToTop) {
            // reset counters no ?
            index_country = 0;
            index_pool = 0;
        } else {
            if (getCurrentCountry().isHisOwnPool() || index_pool == getCurrentCountry().getPoolsNb() - 1) {
                index_country ++;
                index_pool = 0;
            } else
                index_pool ++;
        }

        // -----------------------------------------------------------

        // double arrow down push if ( && ) :
        // - current country has pools
        // - current pool index is 0
        boolean pushArrowDownTwice = !getCurrentCountry().isHisOwnPool() && index_pool == 0;
        if (pushArrowDownTwice) Disp.anyType("--> x2 push arrow down");

        // !!! THIS PART'S NOT WORKING NOW IS IGNORED FOR THE MOMENT !!!
        String[] excluded_list = {
                "Hong Kong"
        };
//        boolean goToBackArrow = false; // default : don't.
//        goToBackArrow = getCurrentCountry().isHisOwnPool() && Country.isInList(excluded_list, getCurrentCountry());
        boolean skipCountry = getCurrentCountry().getName().equals(excluded_list[0]);
//        boolean skipCountry = Country.isInList(excluded_list, getCurrentCountry());;
        if (skipCountry)
        {
            pushArrowDownTwice = true;
            Disp.anyType("--> skipping country [ " + getCurrentCountry().getName() + " ]");
            index_country ++;
            index_pool = 0;
        }

        try {
            Disp.shortMsgStar("Changing pool to [ " + getCurrentCountry().getName() + " | " + getCurrentPool() + " ]", true);
            Disp.line();

            runtime.exec(clickIcon);            TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressSpace);           TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);

            if (goBackToTop) {

                for (int i=0 ; i < Country.getTotalNbCountriesAndPools() ; i++) {
                    runtime.exec(pressArrowUp);       TimeUnit.MILLISECONDS.sleep(config[1]);
                }
                // to go back to Australia | AU Melbourne
                runtime.exec(pressArrowDown);       TimeUnit.MILLISECONDS.sleep(config[1]);
                runtime.exec(pressArrowDown);       TimeUnit.MILLISECONDS.sleep(config[1]);

            } else {

                runtime.exec(pressArrowDown);       TimeUnit.MILLISECONDS.sleep(config[1]);
                // does current country contain several pools ?
                if (pushArrowDownTwice) { // press arrow down one more to avoid folding country pools list
                    runtime.exec(pressArrowDown);       TimeUnit.MILLISECONDS.sleep(config[1]);
                }
                // !!! THIS PART'S NOT WORKING NOW IS IGNORED FOR THE MOMENT !!!
//                if (goToBackArrow) { // press tab once more to get to back arrow in case of skipping
//                    runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
//                }
            }

            // then press space button anyway, either to switch to chosen VPN or to go back to main menu
            runtime.exec(pressSpace);           TimeUnit.MILLISECONDS.sleep(config[1]); // these two...
            runtime.exec(clickIcon);            TimeUnit.MILLISECONDS.sleep(config[4]); // ...can be reversed :)

        } catch (Exception e) { e.printStackTrace(); }

    }


    public static Country getCurrentCountry()
            throws Country.CountryNotFoundException
    {
        // gets the country at counter
//        Disp.anyType("index country: " + index_country);
        Country c;

        if (index_country == -1) {
            throw new Country.CountryNotFoundException(name_country);
        } else
            c = Country.getCountries().get(index_country);

        return c;
    }

    public static String getCurrentPool()
            throws Country.CountryNotFoundException, Country.PoolNotFoundInCountryException
    {
        // gets its actual pool.
        // if no pool is present in country, it takes country name. (default)
//        Disp.anyType("index pool: " + index_pool);
        String p;

        if (index_pool == -1) {
            throw new Country.PoolNotFoundInCountryException(name_country, name_pool);
        } else {
            if (getCurrentCountry().isHisOwnPool()) // name of pool is itself ;)
                p = getCurrentCountry().getName();
            else
                p = getCurrentCountry().getPools()[index_pool]; // takes the first as default
        }

        return p;
    }

    public static void displayBeginningCountryAndPool()
            throws Country.CountryNotFoundException, Country.PoolNotFoundInCountryException
    {
        Disp.shortMsgStar(
                "Currently connected on [ " + getCurrentCountry().getName()
                + " | " + getCurrentPool() + " ]", true
        );
    }


}
