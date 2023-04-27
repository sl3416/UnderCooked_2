package io.sl3416.undercooked.tests;

import interactions.InputKey;
import interactions.InputKey.InputTypes;
import org.junit.runner.RunWith;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class InputKeyTests {
    /**
     * Tests the constructor of the InputKey class
     * @result
     */
    @Test
    public void testInputKeyConstructor() {
        InputTypes inputType = InputTypes.START_GAME;
        int key = 32;

        InputKey inputKey = new InputKey(InputTypes.START_GAME, key);

        assertEquals(inputType, inputKey.getType());
        assertEquals(key, inputKey.getKey());
    }

    /**
     * Tests getType() method
     * @result returns the InputTypes enum constant that was passed to the constructor.
     */
    @Test
    public void testGetType() {
        InputTypes inputType = InputTypes.RESET_GAME;
        int key = 27;

        InputKey inputKey = new InputKey(inputType, key);

        assertEquals(inputType, inputKey.getType());
    }

    /**
     * Tests getKey() method
     * @result return the key that was passed to the constructor
     */
    @Test
    public void testGetKey() {
        InputTypes inputType = InputTypes.INSTRUCTIONS;
        int key = 73;

        InputKey inputKey = new InputKey(inputType, key);

        assertEquals(key, inputKey.getKey());
    }
}