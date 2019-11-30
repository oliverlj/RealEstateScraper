package handleVPN;

import myJavaClasses.Disp;
import myJavaClasses.SaveManager;
import myJavaClasses.ShellWrapper;
import parseImmo.Main;

import java.io.Serializable;
import java.util.ArrayList;

public class Region implements Serializable {
    // above which region is considered completely drained
    private static final int nb_max_blocked_addresses = 4;
    // ireland : 9
    // belgium : 6
    // luxembourg : 5
    // spain : 4
    // all others are above the smallest.

    private static ArrayList<Region> regions = new ArrayList<>();

    private String name;
    private int delayRange; // 0 = western € | 1 = eastern € | 2 = quite far | 3 = really far
    private ArrayList<IP> ipAddresses = new ArrayList<>();

    public Region(String name, int delayRange) {
        this.name = name;
        this.delayRange = delayRange;
    }

    @Override
    public String toString() {
        return this.getName() + " (delay : " + this.getDelayRange() + ")";
    }

    public String getName() {
        return name;
    }

    public int getDelayRange() {
        return delayRange;
    }

    public ArrayList<IP> getIpAddresses() {
        return ipAddresses;
    }

    public boolean isSaturated()
    {
        int blockedCounter = 0;
        for (IP ip : this.getIpAddresses()) {
            if (ip.isBlocked()) blockedCounter ++;
        }

        // different ways of saying a region is saturated
//        int ip_saturation_limit = 2 + this.getIpAddresses().size() / 3; // with / 2
//        int ip_saturation_limit = 2 + this.getIpAddresses().size() / 4; // without / 2
        int ip_saturation_limit = 3 + this.getIpAddresses().size() / 6; // ???

        Disp.progress("IP saturation rate of " + this.getName(), blockedCounter, this.getIpAddresses().size());
        Disp.progress("IP saturation limit of " + this.getName(), blockedCounter, ip_saturation_limit);
        Disp.star();

//        boolean saturated = this.getIpAddresses().size() >= 0 && blockedCounter > (ip_saturation_limit) / 2;
        boolean saturated = this.getIpAddresses().size() >= 0 && blockedCounter > (ip_saturation_limit);
//        boolean saturated = this.getIpAddresses().size() >= 0 && blockedCounter > this.getIpAddresses().size() / 6;
//        boolean saturated = this.getIpAddresses().size() >= 0 && blockedCounter > nb_max_blocked_addresses;
//        boolean saturated = this.getIpAddresses().size() >= Region.nb_max_blocked_addresses;
//        boolean saturated = this.allAIPsSaturated();

//        Disp.anyType("saturated: " + saturated);
        return saturated;
    }

    public int getGlobalSaturationIndicator()
    {
        int indicator = 0;
        for (IP ip : this.getIpAddresses()) {
            indicator += ip.getTimesUsed();
        }
        return indicator;
    }

    public static ArrayList<Region> getRegions() {
        return regions;
    }

    public static void setRegions(ArrayList<Region> regions) {
        Region.regions = regions;
    }

    ////////////////////////// STATIC //////////////////////////

    public static Region handleChange()
    {
        Disp.anyType(">>> Handling region change...");

        Region nextRegion = getClosestUnsaturatedRegions().get(0);
        ShellWrapper.execute("piactl set region " + nextRegion.getName());
        Disp.anyType("verif: " + getCurrent());
        // now save : not ne
//        SaveManager.objectSave(Main.filename_vpn_state + Main.extension_save, Region.getRegions());
        return nextRegion;
    }

    public static Region getCurrent()
    {
        // seems not necesary to wait for region
        String currentRegion = ShellWrapper.execute("piactl get region").get(0);
//        Disp.anyType(currentRegion);
        return getFromString(currentRegion);
    }

    private static Region getFromString(String s)
    {
        for (Region region : regions) {
            if (region.getName().equals(s)) return region;
        }
        return null;
    }

    private static ArrayList<Region> getClosestUnsaturatedRegions()
    {
        // first browse all the 0 then all the 1 all the 2 then all the 3
        for (int i=0 ; i<4; i++) {
            ArrayList<Region> out = new ArrayList<>();
            for (Region region : getRegionsByDelayRange(i)) {
                if (!region.isSaturated()) {
                    out.add(region);
//                    Disp.anyType("unsaturated "+i+": " + region);
                }
            }
            if (!out.isEmpty()) return out;
        }
        return null;
    }

    private static ArrayList<Region> getRegionsByDelayRange(int delayRange)
    {
        ArrayList<Region> out = new ArrayList<>();
        for (Region region : regions) {
            if (region.getDelayRange() == delayRange) {
                out.add(region);
            }
        }
        return out;
    }

}
