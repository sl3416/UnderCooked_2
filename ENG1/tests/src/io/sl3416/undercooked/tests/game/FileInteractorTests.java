package io.sl3416.undercooked.tests.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;
import food.FoodItem;
import food.FoodItem.FoodID;
import food.FoodStack;
import game.*;
import helper.Constants;
import interactions.Interactions;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.PreparationStation;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FileInteractorTests {
    public float[] testStationProgresses = new float[6];
    public float[] testStationBurns = new float[6];
//    @Before
//    public void testSave() {
//        // Create a test save file
//        StateOfGame testGame = new StateOfGame();
//        Json json = new Json();
//        String write = json.prettyPrint(testGame);
//        FileHandle file = Gdx.files.local("testSave.txt");
//        file.writeString(write, false);
//
//        testGame.cooksFoodStacks[0] = new FoodStack(FoodID.cheese);
//        testGame.money = 1;
//        testGame.ovensLocked = false;
//        testGame.fryersLocked = true;
//        testGame.customersServedState = 2;
//        testGame.customersWaiting = 3;
//    }

//    @After
//    public void deleteTestSave() {
//        // Delete the test save file
//        FileHandle file = Gdx.files.local("testSave.txt");
//        file.delete();
//    }

    @Test
    public void testGetInstance() {
        // checks if it returns INSTANCE of FileInteractor and not null
        assertNotNull("Did not return instance of FileInteractor", FileInteractor.getInstance());
    }

    @Test
    public void testNewInstance() {

    }

    @Test
    public void testSaveToJSON() {
        StateOfGame testGame = new StateOfGame();
        testGame.cooksFoodStacks[0] = new FoodStack(FoodID.cheese);
        testGame.money = 1;
        testGame.ovensLocked = false;
        testGame.fryersLocked = true;
        testGame.customersServedState = 2;
        testGame.customersWaiting = 3;

        FileInteractor fileInteractor = FileInteractor.getInstance();
        fileInteractor.saveToJSON();

        // Check if the save file was created
        FileHandle file = Gdx.files.local("save.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testLoadToJSON() {
//        // boot game
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setForegroundFPS(60);
//        config.setTitle("UnderCooked");
//        config.setWindowedMode(Constants.V_Width,Constants.V_Height);
//        Boot boot = new Boot();
//        new Lwjgl3Application(boot, config);

//        OrthographicCamera camera = new OrthographicCamera();
//        ScreenController screenController = new ScreenController(boot, camera);
//        GameScreen gameScreen = new GameScreen(screenController, camera);
//
//        FileInteractor fileInteractor = FileInteractor.getInstance();
//        fileInteractor.loadFromJSON(gameScreen);
//
//        // Check if the game variables were loaded correctly
//        StateOfGame game = StateOfGame.getInstance();
//        assertEquals("cooksFoodStacks was not loaded correctly", FoodID.cheese, game.cooksFoodStacks[0]);
//        assertArrayEquals("stationFoods was not loaded correctly", new FoodItem.FoodID[6], game.stationFoods);
//        assertArrayEquals("stationStates was not loaded correctly", new PreparationStation.StationState[6], game.stationStates);
//
//        for (int i = 0; i < game.stationProgresses.length-1; i++) {
//            assertEquals("stationProgresses was not loaded correctly", testStationProgresses[i], game.stationProgresses[i], 0.001f);
//        }
//
//        for (int i = 0; i < game.stationUsage.length-1; i++) {
//            assertFalse("stationUsage was not loaded correctly", game.stationUsage[i]);
//        }
//
//        for (int i = 0; i < game.stationBurns.length-1; i++) {
//            assertEquals("stationBurns was not loaded correctly", testStationBurns[i], game.stationBurns[i], 0.001f);
//        }
//
//        assertArrayEquals("interactions was not loaded correctly", new Interactions.InteractionResult[6], game.interactions);
//
//        for (int i = 0; i < game.powerups.length-1; i++) {
//            assertFalse("powerups was not loaded correctly", game.powerups[i]);
//        }
//
//        assertArrayEquals("countersFoodStacks was not loaded correctly", new FoodStack[2], game.countersFoodStacks);
//        assertEquals("money was not loaded correctly", 1, game.money);
//        assertFalse("ovensLocked was not loaded correctly", game.ovensLocked);
//        assertTrue("fryersLocked was not loaded correctly", game.fryersLocked);
//        assertEquals("customersServed was not loaded correctly", 2, game.customersServedState);
//
//        assertEquals("chefPositions was not loaded correctly", new ArrayList<>(), game.chefPositions);
//        assertArrayEquals("customerRequests was not loaded correctly", new String[4], game.customerRequests);
//        assertArrayEquals("customerPositions was not loaded correctly", new int[4], game.customerPositions);
//        assertEquals("customersWaiting was not loaded correctly", 3, game.customersWaiting);
    }
}
