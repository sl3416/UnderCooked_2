package io.sl3416.undercooked.tests.interactions;

import static com.badlogic.gdx.Gdx.input;
import static interactions.Interactions.*;
import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;
import food.FoodItem.FoodID;
import food.FoodItem;
import interactions.InputKey;
import interactions.InputKey.InputTypes;
import interactions.Interactions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.Station.StationID;

import java.util.HashMap;

/**
 * Tests for all methods in the Interactions class
 */
@RunWith(GdxTestRunner.class)
public class InteractionsTests {
    /**
     * The constant emptyArray.
     */
    public static Array<InputKey.InputTypes> emptyArray = new Array<>();
    /**
     * The Test.
     */
    public InputTypes TEST;

    /**
     * Tests InteractionResult() constructor constructs the interaction result correctly in InteractionResult class
     */
    @Test
    public void testInteractionResultConstructor() {
        FoodID testResult = FoodID.lettuce;
        float[] testSteps = {25,50,75};
        float testSpeed = -1;

        InteractionResult interactionResult = new InteractionResult(testResult, testSteps, testSpeed);

        // Tests result, steps and speed of interaction result is correct
        assertEquals("The interaction FoodID does not equal the expected FoodID", testResult, interactionResult.result);
        for (int i = 0; i < interactionResult.steps.length - 1; i++) {
            assertEquals("The getter did not output correct Steps", testSteps[i], interactionResult.steps[i], 0.001f);
        }
        assertEquals("The interaction speed does not equal the expected steps", testSpeed, interactionResult.speed, 0.001f);
    }

    /**
     * Test getResults() method in InteractionResults class returns the correct FoodID
     */
    @Test
    public void testGetResults() {
        FoodID testResult = FoodID.rawJacketPotato;
        float[] testSteps = {};
        float testSpeed = 13f;

        InteractionResult interactionResult = new InteractionResult(testResult, testSteps, testSpeed);

        assertEquals("The getter did not output correct FoodID", testResult, interactionResult.getResult());

    }

    /**
     * Test getSteps() method in InteractionResults class returns correct steps for the interaction result.
     */
    @Test
    public void testGetSteps() {
        FoodID testResult = FoodID.onion;
        float[] testSteps = {25, 50, 75};
        float testSpeed = -1;

        InteractionResult interactionResult = new InteractionResult(testResult, testSteps, testSpeed);

        for (int i = 0; i < interactionResult.steps.length - 1; i++) {
            assertEquals("The getter did not output correct Steps", testSteps[i], interactionResult.getSteps()[i], 0.001f);
        }
    }

    /**
     * Test getSpeed() method in InteractionResults class returns correct speed for the interaction result.
     */
    @Test
    public void testGetSpeed() {
        FoodID testResult = FoodID.uc_pizza;
        float[] testSteps = {};
        float testSpeed = 13f;

        InteractionResult interactionResult = new InteractionResult(testResult, testSteps, testSpeed);

        assertEquals("The getter did not output correct Speed", testSpeed, interactionResult.getSpeed(), 0.001f);
    }

    /**
     * Test setSpeed() method in InteractionResults class updates speed to new speed for the interaction result.
     */
    @Test
    public void testSetSpeed() {
        float newSpeed = 7f;

        InteractionResult interactionResult = new InteractionResult(FoodID.uc_pizza, new float[] {}, 13f);
        interactionResult.setSpeed(newSpeed);

        assertEquals("Speed of interaction was not updated", newSpeed, interactionResult.getSpeed(), 0.001f);
    }

    /**
     * Test setResult() method in InteractionResults class updates FoodID to new FoodID for the interaction result.
     */
    @Test
    public void testSetResult() {
        FoodID newResult = FoodID.onion;

        InteractionResult interactionResult = new InteractionResult(FoodID.uc_pizza, new float[] {}, 13f);
        interactionResult.setResult(newResult);

        assertEquals("FoodID of interaction was not updated", newResult, interactionResult.getResult());
    }

    /**
     * Test getInputKeys() method in Interactions class gets the correct input key assigned to the enum constant inputID
     */
    @Test
    public void testGetInputKeys() {
        // Initialize input keys
        HashMap<InputID, Array<InputKey>> inputs = new HashMap<>();
        inputs.put(InputID.MENU, new Array<>(new InputKey[]{
                new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I),
                new InputKey(InputKey.InputTypes.RESET_GAME, Input.Keys.R),
                new InputKey(InputKey.InputTypes.STARTE_GAME, Input.Keys.E),
                new InputKey(InputKey.InputTypes.STARTS_GAME, Input.Keys.ENTER),
                new InputKey(InputKey.InputTypes.PAUSE, Input.Keys.ESCAPE),
                new InputKey(InputKey.InputTypes.UNPAUSE, Input.Keys.ESCAPE),
                new InputKey(InputKey.InputTypes.CREDITS, Input.Keys.C),
                new InputKey(InputKey.InputTypes.QUIT, Input.Keys.Q),
                new InputKey(InputKey.InputTypes.SAVE_GAME, Input.Keys.SEMICOLON),
                new InputKey(InputKey.InputTypes.LOAD_GAME, Input.Keys.APOSTROPHE)
        }));
        inputs.put(InputID.COOK_MOVEMENT, new Array<>(new InputKey[] {
                new InputKey(InputKey.InputTypes.COOK_UP,Input.Keys.W),
                new InputKey(InputKey.InputTypes.COOK_LEFT,Input.Keys.A),
                new InputKey(InputKey.InputTypes.COOK_DOWN,Input.Keys.S),
                new InputKey(InputKey.InputTypes.COOK_RIGHT,Input.Keys.D),

                new InputKey(InputKey.InputTypes.COOK_UP,Input.Keys.UP),
                new InputKey(InputKey.InputTypes.COOK_LEFT,Input.Keys.LEFT),
                new InputKey(InputKey.InputTypes.COOK_DOWN,Input.Keys.DOWN),
                new InputKey(InputKey.InputTypes.COOK_RIGHT,Input.Keys.RIGHT)
        }));

        Array<InputKey.InputTypes> expectedResults = new Array<>(new InputKey.InputTypes[]{
                InputKey.InputTypes.INSTRUCTIONS,
                InputKey.InputTypes.RESET_GAME,
                InputKey.InputTypes.STARTE_GAME,
                InputKey.InputTypes.STARTS_GAME,
                InputKey.InputTypes.PAUSE,
                InputKey.InputTypes.UNPAUSE,
                InputKey.InputTypes.CREDITS,
                InputKey.InputTypes.QUIT,
                InputKey.InputTypes.SAVE_GAME,
                InputKey.InputTypes.LOAD_GAME
        });

        Array<InputKey.InputTypes> actualResults = new Array<>();
        Array<InputKey> inputKeys = getInputKeys(InputID.MENU);
        // Checks each input key in the array
        for (InputKey inputKey : inputKeys) {
            actualResults.add(inputKey.getType());
        }

        // Returns True if inputKeys of inputID is returned correctly
        assertEquals("The input key does not match the expected input key", expectedResults, actualResults);
    }

    /**
     * Tests resetKeys() in Interactions class clears keys pressed and keys just pressed
     */
    @Test
    public void testResetKeys() {
        Interactions interactions = new Interactions();
        interactions.keysPressed.add(InputKey.InputTypes.USE);
        interactions.keysPressed.add(InputKey.InputTypes.COOK_UP);
        interactions.keysJustPressed.add(InputKey.InputTypes.USE);
        interactions.keysPressed.add(InputKey.InputTypes.COOK_DOWN);

        resetKeys();

        // Returns True if keysPressed and keysJustPressed are cleared
        assertEquals("keysPressed was not cleared", emptyArray, interactions.keysPressed);
        assertEquals("keysJustPressed was not cleared", emptyArray, interactions.keysJustPressed);
    }

    /**
     * Tests updateKeys() in Interactions class updates correctly what keys were pressed
     */
    @Test
    public void testUpdateKeys() {
        // Initialize input keys
        HashMap<InputID, Array<InputKey>> inputs = new HashMap<>();
        inputs.put(InputID.MENU, new Array<>(new InputKey[]{
                new InputKey(InputTypes.INSTRUCTIONS, Input.Keys.I),
                new InputKey(InputTypes.RESET_GAME, Input.Keys.R),
                new InputKey(InputTypes.STARTE_GAME, Input.Keys.E),
                new InputKey(InputTypes.STARTS_GAME, Input.Keys.ENTER),
                new InputKey(InputTypes.PAUSE, Input.Keys.ESCAPE),
                new InputKey(InputTypes.UNPAUSE, Input.Keys.ESCAPE),
                new InputKey(InputTypes.CREDITS, Input.Keys.C),
                new InputKey(InputTypes.QUIT, Input.Keys.Q),
                new InputKey(InputTypes.SAVE_GAME, Input.Keys.SEMICOLON),
                new InputKey(InputTypes.LOAD_GAME, Input.Keys.APOSTROPHE)
        }));
        inputs.put(InputID.COOK_MOVEMENT, new Array<>(new InputKey[]{
                new InputKey(InputTypes.COOK_UP, Input.Keys.W),
                new InputKey(InputTypes.COOK_LEFT, Input.Keys.A),
                new InputKey(InputTypes.COOK_DOWN, Input.Keys.S),
                new InputKey(InputTypes.COOK_RIGHT, Input.Keys.D),

                new InputKey(InputTypes.COOK_UP, Input.Keys.UP),
                new InputKey(InputTypes.COOK_LEFT, Input.Keys.LEFT),
                new InputKey(InputTypes.COOK_DOWN, Input.Keys.DOWN),
                new InputKey(InputTypes.COOK_RIGHT, Input.Keys.RIGHT)
        }));

        Interactions interactions = new Interactions();

        // create mock Gdx.input object
        Gdx.input = mock(Input.class);

        // set up mock input values for testing
        when(Gdx.input.isKeyPressed(Input.Keys.UP)).thenReturn(true);
        when(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)).thenReturn(true);

        updateKeys();

        // check that keysPressed and keysJustPressed contain the expected InputTypes values
        assertTrue("keysPressed does not contain the key pressed", keysPressed.contains(InputTypes.COOK_UP, true));
        assertTrue("keysJustPressed does not contain the key just pressed", keysJustPressed.contains(InputTypes.COOK_SWAP, true));
    }

    /**
     * Test get key strings.
     */
    @Test
    public void testGetKeyStrings() {
        Interactions interactions = new Interactions();

        // Call getKeyStrings with the inputType that matches the InputKey objects added to the array
        Array<String> validKeys = interactions.getKeyStrings(InputKey.InputTypes.COOK_UP);
        // Return True if returned array contains the expected key strings in enum constant
        assertTrue("Input type (assigned to the enum constant) is not valid.", validKeys.contains("W", true));
        assertTrue("Input type (assigned to the enum constant) is not valid.", validKeys.contains("Up", true));
    }

    /**
     * Test get key string input type.
     */
    @Test
    public void testGetKeyStringInputType() {
        assertEquals("Input type (assigned to the enum constant) is not valid.", "Escape", getKeyString(InputKey.InputTypes.UNPAUSE));
        assertEquals("Input type (assigned to the enum constant) is not valid.", "undefined", getKeyString(TEST));
        assertEquals("Input type (assigned to the enum constant) is not valid.", "[W or Up]", getKeyString(InputKey.InputTypes.COOK_UP));
    }

    /**
     * Test get key string input key.
     */
    @Test
    public void testGetKeyStringInputKey() {
        InputKey key1 = new InputKey(InputKey.InputTypes.COOK_SWAP, Input.Keys.SPACE);
        assertEquals("Input key is not valid.", "Space", getKeyString(key1));

        InputKey key2 = new InputKey(InputKey.InputTypes.CREDITS, Input.Keys.C);
        assertEquals("Input key is not valid.", "C", getKeyString(key2));

        InputKey key3 = new InputKey(InputKey.InputTypes.SAVE_GAME, Input.Keys.SEMICOLON);
        assertEquals("Input key is not valid.", ";", getKeyString(key3));
    }

    /**
     * Test is pressed.
     */
    @Test
    public void testIsPressed() {
        keysPressed.add(InputKey.InputTypes.PICK_UP);

        // Test for a key that was pressed
        assertTrue(isPressed(InputKey.InputTypes.PICK_UP));
        // Test for a key that was not pressed
        assertFalse(isPressed(InputKey.InputTypes.COOK_SWAP));
    }

    /**
     * Test is just pressed.
     */
    @Test
    public void testIsJustPressed() {
        Interactions interactions = new Interactions();

        keysJustPressed.add(InputKey.InputTypes.COOK_UP);

        // Test for a key that was just pressed
        assertTrue(isJustPressed(InputKey.InputTypes.COOK_UP));
        // Test for a key that was not just pressed
        assertFalse(isJustPressed(InputKey.InputTypes.COOK_DOWN));
    }

    /**
     * Test interaction.
     */
    @Test
    public void testInteraction() {
        InteractionResult expectedResult = new InteractionResult(FoodID.tomatoChop, new float[] {25, 50, 75}, -1);
        InteractionResult actualResult = interaction(FoodID.tomato, StationID.cut);

        // Return True if it returns expected FoodID, Steps and Speed of InteractionResult of interaction between FoodID and StationID
        assertEquals("Does not return expected FoodID of InteractionResult of interaction between FoodID and StationID", expectedResult.getResult(), actualResult.getResult());
        for (int i = 0 ; i < actualResult.steps.length-1 ; i++) {
            assertEquals("Does not return expected Steps of InteractionResult of interaction between FoodID and StationID", expectedResult.getSteps()[i], actualResult.getSteps()[i], 0.001f);
        }
        assertEquals("Does not return expected Speed of InteractionResult of interaction between FoodID and StationID", expectedResult.getSpeed(), actualResult.getSpeed(), 0.001f);
    }

    /**
     * Test interaction key.
     */
    @Test
    public void testInteractionKey() {
        String expectedKey = "0-1";

        // Call InteractionKey with example input values
        String actualKey = InteractionKey(FoodID.lettuce, StationID.cut);

        // Return True if it returns the ordinal of FoodID and ordinal of StationID in format "%s-%s"
        assertEquals("Does not return expected ordinal of FoodID and ordinal of StationID in format \"%s-%s\"", expectedKey, actualKey);
    }


}