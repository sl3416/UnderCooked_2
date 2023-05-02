package io.sl3416.undercooked.tests.game;

import game.PowerupStatic;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;

/**
 * Tests all methods in PowerupStatic class
 */
@RunWith(GdxTestRunner.class)
public class PowerupStaticTests {

    private HashMap<String, Boolean> powerups;

    @Before
    public void setUp() {
        powerups = new HashMap<String, Boolean>();
        powerups.put("SpeedIncr", Boolean.TRUE);
        powerups.put("CookingSpeedIncr", Boolean.TRUE);
        powerups.put("MoneyIncr", Boolean.TRUE);
        powerups.put("CustomerTimerIncr", Boolean.TRUE);
        powerups.put("NewStationsCostDecr", Boolean.TRUE);
        PowerupStatic.powerups = powerups;
    }

    /**
     * Test resetPowerups() in PowerupStatic class resets powerups to FALSE
     */
    @Test
    public void testResetPowerups() {
        PowerupStatic.resetPowerups();

        // Check every powerup in powerups has been reset to FALSE
        for (Boolean value : powerups.values()) {
            assertFalse(value);
        }
    }
}
