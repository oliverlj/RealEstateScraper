package handleVPN;

import myJavaClasses.Disp;

import static handleVPN.IP.*;
import static handleVPN.Region.*;

public class HandleVPN { // TODO rewrite
    // this class will contain all methods and logic to change region | IP

    public static void displayBeginningCountryAndPool()
    {
        Disp.shortMsgStar(
                "Currently connected on [ " + getCurrentRegion()
                        + " | " + getCurrentFinalIP() + " ]", true
        );
    }

}
