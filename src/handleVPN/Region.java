package handleVPN;

import myJavaClasses.Disp;
import myJavaClasses.SaveManager;
import myJavaClasses.ShellWrapper;
import parseImmo.Main;

import java.io.Serializable;
import java.util.ArrayList;

public class Region implements Serializable {
    private static final int nb_max_blocked_addresses = 5 ; // above which region is considered

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
        if (this.getIpAddresses().size() == blockedCounter) return true;
        else return false;
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
        Region nextRegion = getClosestUnsaturatedRegions().get(0);
        if (nextRegion == null)
        ShellWrapper.execute("piactl set region " + nextRegion.getName());
        Disp.anyType("verif: " + getCurrent());
        // now save
        SaveManager.objectSave(Main.filename_vpn_state + Main.extension_save, Region.getRegions());
        return nextRegion;
    }

    public static Region getCurrent()
    {
        // seems not necesary to wait for region
        String currentRegion = ShellWrapper.execute("piactl get region").get(0);
//        Disp.anyType(currentRegion);
        Region region = getFromString(currentRegion);
        return region;
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
        ArrayList<Region> out = new ArrayList<>();
        // first browse all the 0 then all the 1 all the 2 then all the 3
        for (int i=0 ; i<4; i++) {
            for (Region region : getRegionsByDelayRange(i)) {
                if (!region.isSaturated()) out.add(region);
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
