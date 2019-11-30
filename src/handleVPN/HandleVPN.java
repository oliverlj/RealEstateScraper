package handleVPN;

import myJavaClasses.Disp;
import myJavaClasses.SaveManager;
import parseImmo.Main;

import java.util.ArrayList;

public class HandleVPN { // TODO rewrite
    // this class will contain all methods and logic to change region | IP
    
    public static void initAllRegions() throws Exception
    {
        // first try to load
        ArrayList<Region> initRegions = (ArrayList<Region>) SaveManager.objectLoad(Main.filename_vpn_state, true);
        if (initRegions == null) initRegions = new ArrayList<>();
        Region.setRegions(initRegions);

        // else build from scratch
        if (Region.getRegions().isEmpty()) {
            // 0 = really close (western Europe)
            Region.getRegions().add(new Region("france", 0));
            Region.getRegions().add(new Region("de-berlin", 0));
            Region.getRegions().add(new Region("de-frankfurt", 0));
            Region.getRegions().add(new Region("uk-manchester", 0));
            Region.getRegions().add(new Region("uk-southampton", 0));
            Region.getRegions().add(new Region("uk-london", 0));
            Region.getRegions().add(new Region("ireland", 0));
            Region.getRegions().add(new Region("netherlands", 0));
            Region.getRegions().add(new Region("belgium", 0));
            Region.getRegions().add(new Region("switzerland", 0));
            Region.getRegions().add(new Region("luxembourg", 0));
            Region.getRegions().add(new Region("spain", 0));
            Region.getRegions().add(new Region("italy", 0));
            // 1 = quite close (central-eastern Europe)
            Region.getRegions().add(new Region("czech-republic", 1));
            Region.getRegions().add(new Region("denmark", 1));
            Region.getRegions().add(new Region("sweden", 1));
            Region.getRegions().add(new Region("norway", 1));
            Region.getRegions().add(new Region("finland", 1));
            Region.getRegions().add(new Region("austria", 1));
            Region.getRegions().add(new Region("poland", 1));
            Region.getRegions().add(new Region("hungary", 1));
            Region.getRegions().add(new Region("romania", 1));
            // 2 = quite far (out of Europe)
            Region.getRegions().add(new Region("israel", 2));
            Region.getRegions().add(new Region("ca-montreal", 2));
            Region.getRegions().add(new Region("ca-toronto", 2));
            Region.getRegions().add(new Region("ca-vancouver", 2));
            Region.getRegions().add(new Region("us-east", 2));
            Region.getRegions().add(new Region("us-new-york-city", 2));
            Region.getRegions().add(new Region("us-washington-dc", 2));
            Region.getRegions().add(new Region("us-chicago", 2));
            Region.getRegions().add(new Region("us-atlanta", 2));
            Region.getRegions().add(new Region("us-las-vegas", 2));
            Region.getRegions().add(new Region("us-texas", 2));
            Region.getRegions().add(new Region("us-houston", 2));
            Region.getRegions().add(new Region("us-florida", 2));
            Region.getRegions().add(new Region("us-denver", 2));
            Region.getRegions().add(new Region("us-silicon-valley", 2));
            Region.getRegions().add(new Region("us-west", 2));
            Region.getRegions().add(new Region("us-california", 2));
            Region.getRegions().add(new Region("us-seattle", 2));
            // 3 = really far
            Region.getRegions().add(new Region("uae", 3));
            Region.getRegions().add(new Region("india", 3));
            Region.getRegions().add(new Region("mexico", 3));
            Region.getRegions().add(new Region("au-perth", 3));
            Region.getRegions().add(new Region("au-sydney", 3));
            Region.getRegions().add(new Region("au-melbourne", 3));
            Region.getRegions().add(new Region("singapore", 3));
            Region.getRegions().add(new Region("hong-kong", 3));
            Region.getRegions().add(new Region("japan", 3));
            Region.getRegions().add(new Region("new-zealand", 3));
        } else {
            Disp.anyType(">>> Regions have already been initiated :");
            HandleVPN.displayGlobalState();
        }
    }
    
    public static void displayCurrentRegionAndIP()
    {
        // get current region and ip
        Disp.shortMsgStar(
                "Currently connected on [ " + Region.getCurrent()
                        + " | " + IP.getCurrent() + " ]", true
        );
    }

    public static void displayGlobalState()
    {
        for (Region region : Region.getRegions()) {
            Disp.anyType(region);
            for (IP ip : region.getIpAddresses()) {
                Disp.anyType("- " + ip);
            }
        }
        Disp.star();
    }

}
