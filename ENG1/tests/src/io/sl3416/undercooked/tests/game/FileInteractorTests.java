package io.sl3416.undercooked.tests.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;
import food.FoodItem.FoodID;
import food.FoodItem;
import food.FoodStack;
import game.*;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.PreparationStation;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FileInteractorTests {

    @Before
    public void testSave() throws Exception {
        // Create a test save file
        StateOfGame testGame = new StateOfGame();
        Json json = new Json();
        String write = json.prettyPrint(testGame);
        FileHandle file = Gdx.files.local("testSave.txt");
        file.writeString(write, false);

        testGame.cooksFoodStacks[0] = new FoodStack(FoodID.cheese);
        testGame.money = 1;
        testGame.ovensLocked = false;
        testGame.fryersLocked = true;
        testGame.customersServed = 2;
    }

    @After
    public void deleteTestSave() throws Exception {
        // Delete the test save file
        FileHandle file = Gdx.files.local("testSave.txt");
        file.delete();
    }

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
        testGame.customersServed = 2;

        FileInteractor fileInteractor = FileInteractor.getInstance();
        fileInteractor.saveToJSON();

        // Check if the save file was created
        FileHandle file = Gdx.files.local("save.txt");
        assertTrue(file.exists());
    }

//    @Test
//    public void testLoadToJSON() {
//        Boot boot = new Boot();
//        OrthographicCamera camera = new OrthographicCamera();
//
//        ScreenController screenController = new ScreenController(boot, camera);
//        GameScreen gameScreen = new GameScreen(screenController, camera);
//        FileInteractor fileInteractor = FileInteractor.getInstance();
//        fileInteractor.loadFromJSON(gameScreen);
//
//        // Check if the game variables were loaded correctly
//        StateOfGame game = StateOfGame.getInstance();
//        assertEquals("cooksFoodStacks was not loaded correctly", FoodID.cheese, game.cooksFoodStacks[0]);
//        assertArrayEquals("stationFoods was not loaded correctly", new FoodItem.FoodID[6], game.stationFoods);
//        assertArrayEquals("stationStates was not loaded correctly", new PreparationStation.StationState[6], game.stationStates);
//        assertEquals("stationProgresses was not loaded correctly", new float[6], game.stationProgresses);
//        assertEquals("powerups was not loaded correctly", new boolean[5], game.powerups);
//        assertArrayEquals("countersFoodStacks was not loaded correctly", new FoodStack[2], game.countersFoodStacks);
//        assertEquals("money was not loaded correctly", 1, game.money);
//        assertFalse("ovensLocked was not loaded correctly", game.ovensLocked);
//        assertTrue("fryersLocked was not loaded correctly", game.fryersLocked);
//        assertEquals("customersServed was not loaded correctly", 1, game.customersServed);
//    }
}
