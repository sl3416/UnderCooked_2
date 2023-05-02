package io.sl3416.undercooked.tests.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import food.FoodItem.FoodID;
import food.FoodStack;
import game.*;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FileInteractorTests {
    public float[] testStationProgresses = new float[6];
    public float[] testStationBurns = new float[6];

    @Test
    public void testGetInstance() {
        // checks if it returns INSTANCE of FileInteractor and not null
        assertNotNull("Did not return instance of FileInteractor", FileInteractor.getInstance());
    }

    @Test
    public void testNewInstance() {
        // create state of test game
        StateOfGame testGame = new StateOfGame();
        testGame.cooksFoodStacks[0] = new FoodStack(FoodID.cheese);
        testGame.money = 1;
        testGame.ovensLocked = false;
        testGame.fryersLocked = true;
        testGame.customersServedState = 2;
        testGame.customersWaiting = 3;

        // Get the current instance of FileInteractor
        FileInteractor oldInstance = FileInteractor.getInstance();

        // Create a new instance of FileInteractor using newInstance
        FileInteractor.newInstance();
        FileInteractor newInstance = FileInteractor.getInstance();

        // Check that the new instance is not null
        assertNotNull(newInstance);

        // Check that the new instance is different from the old instance
        assertNotEquals(oldInstance, newInstance);

        // Check that the new instance is an instance of FileInteractor
        assertTrue(newInstance instanceof FileInteractor);
    }

    @Test
    public void testSaveToJSON() {
        // create state of test game
        StateOfGame testGame = new StateOfGame();
        testGame.cooksFoodStacks[0] = new FoodStack(FoodID.cheese);
        testGame.money = 1;
        testGame.ovensLocked = false;
        testGame.fryersLocked = true;
        testGame.customersServedState = 2;
        testGame.customersWaiting = 3;

        // save file
        FileInteractor fileInteractor = FileInteractor.getInstance();
        fileInteractor.saveToJSON();

        // Check if the save file was created
        FileHandle file = Gdx.files.local("save.txt");
        assertTrue(file.exists());
    }
}
