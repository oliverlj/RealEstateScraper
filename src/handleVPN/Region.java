package handleVPN;

import java.util.ArrayList;

public class Region {

    private String name;
    private int delayRange; // 0 = western € | 1 = eastern € | 2 = quite far | 3 = really far
    private ArrayList<IP> ipAddresses = new ArrayList<>();
    private boolean saturated = false; // are enough IP saturated ?

    public Region(String name, int delayRange) {
        this.name = name;
        this.delayRange = delayRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IP> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(ArrayList<IP> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public boolean isSaturated() {
        return saturated;
    }

    public void setSaturated(boolean saturated) {
        this.saturated = saturated;
    }

    ////////////////////////// STATIC //////////////////////////

    public static String getCurrentRegion() {
        return "";
    }

}
