package io.sl3416.undercooked.tests.helper;

import helper.Util;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class UtilTests {
    @Test
    public void testDistancePoints() {
        float x1 = 0f, y1 = 0f, x2 = 3f, y2 = 4f;
        float expectedDistance = 5f; // sqrt((0-3)^2 + (0-4)^2) = = sqrt(9+16) = sqrt (25) = 5f
        float actualDistance = Util.distancePoints(x1, y1, x2, y2);
        assertEquals("Calculated distance point was incorrect", expectedDistance, actualDistance, 0.00f);
    }

    @Test
    public void testFormatTime() {
        // test 1
        int hours = 2, minutes = 5, seconds = 30;
        String expectedTimeString = "2:5:30";
        String actualTimeString = Util.formatTime(hours, minutes, seconds);
        assertEquals("Calculated format time was incorrect", expectedTimeString, actualTimeString);

        // test 2
        hours = 0;
        minutes = 45;
        seconds = 10;
        expectedTimeString = "45:10";
        actualTimeString = Util.formatTime(hours, minutes, seconds);
        assertEquals("Calculated format time was incorrect", expectedTimeString, actualTimeString);

        // test 3
        hours = 0;
        minutes = 0;
        seconds = 55;
        expectedTimeString = "0:55";
        actualTimeString = Util.formatTime(hours, minutes, seconds);
        assertEquals("Calculated format time was incorrect", expectedTimeString, actualTimeString);
    }
}

