package handleVPN;

import myJavaClasses.Disp;
import myJavaClasses.SaveManager;
import myJavaClasses.ShellWrapper;
import parseImmo.Main;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IP implements Serializable {
    // above which number of failures each IP is considered blocked
    public static final int nb_max_each_ip = 2;

    public static final String NO_IP = "Unknown";
    public static final String NO_DATE = "°";

    private String address;
    private int timesUsed = 0;
    private LocalDateTime lastTry;

    public IP(String address) {
        this.address = address;
        this.lastTry = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String lastTry_raw;
        try {
             lastTry_raw = this.getLastTry().format(DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm.ss"));
        } catch (NullPointerException npEx) {
             lastTry_raw = NO_DATE;
        }
        return this.getAddress() +
                " (used " + this.getTimesUsed() +
                " times — last try was on " + lastTry_raw +
                ")";
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

    public LocalDateTime getLastTry() {
        return lastTry;
    }

    public void setLastTry(LocalDateTime lastTry) {
        this.lastTry = lastTry;
    }

    public boolean isBlocked() {
        return this.timesUsed >= nb_max_each_ip;
    }

    ////////////////////////// STATIC //////////////////////////

    public static IP handleChange()
    {
        Disp.anyType(">>> Handling IP change on same region...");

        // the original order is indicated by n) :
        // 2) on/off : gives a new ip
        ShellWrapper.execute("piactl disconnect");
        ShellWrapper.execute("piactl connect");
        // 3) new IP is ?
        IP newIP = getCurrent();
        // 1) increment current ip counter
        getCurrent().setTimesUsed(getCurrent().getTimesUsed() + 1);
        getCurrent().setLastTry(LocalDateTime.now());
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
        return createOrGet(s);
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
