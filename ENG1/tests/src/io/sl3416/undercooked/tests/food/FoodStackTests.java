package io.sl3416.undercooked.tests.food;

import food.FoodStack;
import food.FoodItem;
import com.badlogic.gdx.utils.Array;
import food.FoodItem.FoodID;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class FoodStackTests {

    private FoodStack foodStack;

    @Before
    public void setUp() {
        foodStack = new FoodStack();
    }


    @Test
    public void testAddAndPop() {
        FoodItem foodItem = new FoodItem();
        foodStack.addStack(FoodID.bun);
        assertEquals(foodItem, foodStack.popStack());
    }

    @Test
    public void testPeek() {
        FoodItem foodItem = new FoodItem();
        foodStack.addStack(FoodID.bun);
        assertEquals(foodItem, foodStack.peekStack());
        assertEquals(1, foodStack.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, foodStack.size());
        foodStack.addStack(FoodID.bun);
        foodStack.addStack(FoodID.bun);
        assertEquals(2, foodStack.size());
    }
}