package io.sl3416.undercooked.tests.game;

import food.FoodItem;
import food.FoodStack;
import game.StateOfGame;
import interactions.Interactions;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.PreparationStation;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class StateOfGameTests {
    public float[] testStationProgresses = new float[6];
    public float[] testStationBurns = new float[6];

    @Test
    public void testStateOfGameConstructor() {
        StateOfGame game = new StateOfGame();

        // Check that all instance variables are initialized to their default values
        assertArrayEquals("cooksFoodStacks was not loaded correctly", new FoodStack[3], game.cooksFoodStacks);
        assertArrayEquals("stationFoods was not loaded correctly", new FoodItem.FoodID[6], game.stationFoods);
        assertArrayEquals("stationStates was not loaded correctly", new PreparationStation.StationState[6], game.stationStates);

        for (int i = 0; i < game.stationProgresses.length-1; i++) {
            assertEquals("stationProgresses was not loaded correctly", testStationProgresses[i], game.stationProgresses[i], 0.001f);
        }

        for (int i = 0; i < game.stationUsage.length-1; i++) {
            assertFalse("stationUsage was not loaded correctly", game.stationUsage[i]);
        }

        for (int i = 0; i < game.stationBurns.length-1; i++) {
            assertEquals("stationBurns was not loaded correctly", testStationBurns[i], game.stationBurns[i], 0.001f);
        }

        assertArrayEquals("interactions was not loaded correctly", new Interactions.InteractionResult[6], game.interactions);

        for (int i = 0; i < game.powerups.length-1; i++) {
            assertFalse("powerups was not loaded correctly", game.powerups[i]);
        }

        assertArrayEquals("countersFoodStacks was not loaded correctly", new FoodStack[2], game.countersFoodStacks);
        assertEquals("money was not loaded correctly", 0, game.money);
        assertTrue("ovensLocked was not loaded correctly", game.ovensLocked);
        assertTrue("fryersLocked was not loaded correctly", game.fryersLocked);
        assertEquals("customersServed was not loaded correctly", 0, game.customersServedState);

        assertEquals("chefPositions was not loaded correctly", new ArrayList<>(), game.chefPositions);
        assertArrayEquals("customerRequests was not loaded correctly", new String[4], game.customerRequests);
        assertArrayEquals("customerPositions was not loaded correctly", new int[4], game.customerPositions);
        assertEquals("customersWaiting was not loaded correctly", 0, game.customersWaiting);
    }

    @Test
    public void testGetInstance() {
        // checks if it returns INSTANCE of StateOfGame and not null
        assertNotNull("Did not return instance of StateOfGame", StateOfGame.getInstance());
    }

    @Test
    public void testNewInstance() {
        StateOfGame stateOfGame1 = StateOfGame.getInstance();
        StateOfGame stateOfGame2 = StateOfGame.newInstance();
        assertNotEquals("New instance was not created properly", stateOfGame1, stateOfGame2);
    }
}
