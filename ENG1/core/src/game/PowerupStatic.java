package game;

import java.util.HashMap;

public class PowerupStatic {

    public static HashMap<String, Boolean> powerups;

    static {
        powerups = new HashMap<String, Boolean>();
        powerups.put("SpeedIncr", Boolean.FALSE);
        powerups.put("CookingSpeedIncr", Boolean.FALSE);
        powerups.put("MoneyIncr", Boolean.FALSE);
        powerups.put("CustomerTimerIncr", Boolean.FALSE);
        powerups.put("NewStationsCostDecr", Boolean.FALSE);
    }

    public static void resetPowerups(){
        powerups.put("SpeedIncr", Boolean.FALSE);
        powerups.put("CookingSpeedIncr", Boolean.FALSE);
        powerups.put("MoneyIncr", Boolean.FALSE);
        powerups.put("CustomerTimerIncr", Boolean.FALSE);
        powerups.put("NewStationsCostDecr", Boolean.FALSE);
    }

}
