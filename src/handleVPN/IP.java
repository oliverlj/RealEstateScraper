package handleVPN;

import myJavaClasses.SaveManager;
import myJavaClasses.ShellWrapper;
import parseImmo.Main;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class IP implements Serializable {
    public static final String NO_IP = "Unknown";

    private String address;
    private int timesUsed = 0;

    public IP(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getAddress() + " (used " + this.getTimesUsed() + " times)";
    }


    public String getAddress() {
        return address;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public boolean isBlocked() {
        return this.timesUsed > 0;
    }

    ////////////////////////// STATIC //////////////////////////

    public static IP handleChange()
    {
        // 1) increment current ip counter
        getCurrent().setTimesUsed(getCurrent().getTimesUsed() + 1);
        // 2) on/off : gives a new ip
        ShellWrapper.execute("piactl disconnect");
        ShellWrapper.execute("piactl connect");
        // 3) new IP is ?
        // 4) does it already exist ?
        IP newIP = getCurrent();
        // now save
        SaveManager.objectSave(Main.filename_vpn_state + Main.extension_save, Region.getRegions());
        return newIP;
    }

    public static IP getCurrent()
    {
        String s = NO_IP;
        while (s.equals(NO_IP)) {
            s = ShellWrapper.execute("piactl get vpnip").get(0);
        }
//        Disp.anyType(s);
        // now determine if the IP already exists or not
        IP ip = createOrGet(s);
        return ip;
    }

    private static IP getFromString(String s)
    {
        for (Region region : Region.getRegions()) {
            for (IP ip : region.getIpAddresses()) {
                if (ip.getAddress().equals(s)) return ip;
            }
        }
        return null;
    }

    private static IP createOrGet(String s)
    {
        IP ip = getFromString(s);
        if (ip == null) { // create it
            ip = new IP(s);
            Region.getCurrent().getIpAddresses().add(ip);
        }
        return ip;
    }

}
