package handleVPN;

import myJavaClasses.Misc;

class HandleVPN {
    // ----- ALL IMPORTANT VARIABLES AND PARAMETERS ARE HERE -------
    // !!! before launching program, make sure :
    // - VPN is ON
    // - VPN window is CLOSED
    // - VPN countries must be sorted ALPHABETICALLY
    // !!! you might succeed into doing something else while it's on the background...
    // !!! but DO NOT EVER GO INTO FULLSCREEN (else VPN can't be clicked on and unpredictable things may happen.

    // --> names of countries and pools at the beginning of program.
    // name_country must be filled exactly.
    static final String name_country = "Romania";
    // name_pool must be filled correctly if country contains >= 2 pools.
    // else (which means : if country is its own pool) value is ignored.
    static final String name_pool = "DE Frankfurt";

    // different config speeds for AppleScript
    private static final int[] config_fast = {100, 200, 1000, 2000, 3000}; // way too fast. buttons override each other
    private static final int[] config_medium = {100, 250, 1000, 2500, 4000};
    private static final int[] config_slow = {100, 300, 1000, 2500, 5000};
    // which config do you choose ?
    static int[] config = config_medium;

    // --> clicks and keystrokes in AppleScript
    // static click and its possible parameters
    private static final int[] x_appIconValues = {980, 1010, 1030};
    private static final int x_appIconIndex = 2;
    // ----- ALL IMPORTANT VARIABLES AND PARAMETERS WERE THERE -------
    static final String[] clickIcon = generateArgsForClick(x_appIconValues[x_appIconIndex], 1);
    // static keystrokes
    static final String[] pressTab = generateArgsForSimpleKeystroke("tab", false);
    static final String[] pressSpace = generateArgsForSimpleKeystroke("space", false);
    static final String[] pressArrowDown = generateArgsForSimpleKeystroke("125", true);
    static final String[] pressArrowUp = generateArgsForSimpleKeystroke("126", true);


    static String[] generateArgsForSimpleKeystroke(String key, boolean raw)
    {
        String command; if (raw) command = "key code"; else command = "keystroke";
        String commandForKeystroke = command + " " + key;
        return wrapCommandForSystemEvents(commandForKeystroke);
    }


    static String[] generateArgsForClick(int x, int y)
    {
        String commandForClick = "click at {" + x + ", " + y + "}";
        return wrapCommandForSystemEvents(commandForClick);
    }


    private static String[] wrapCommandForSystemEvents(String command)
    {
        String[] splitCommand = {
                "tell application \"System Events\"",
                command,
                "end tell"
        };
//        Disp.anyTypeThenLine(splitCommand);

        String[] args = {
                "osascript",
                "-e",
                Misc.patchStringArray(splitCommand)
        };
        return args;
    }

}
