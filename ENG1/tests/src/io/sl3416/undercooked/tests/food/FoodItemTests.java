package io.sl3416.undercooked.tests.food;

import food.FoodItem;
import food.FoodItem.FoodID;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class FoodItemTests {
    @Test
    public void testFoodHeights() {
        HashMap<FoodItem.FoodID, Float> expectedHeights = new HashMap<>();
        expectedHeights.put(FoodID.lettuce, 20F);
        expectedHeights.put(FoodID.lettuceChop, 4F);
        expectedHeights.put(FoodID.tomato, 20F);
        expectedHeights.put(FoodID.tomatoChop, 5.8F);
        expectedHeights.put(FoodID.onion, 20F);
        expectedHeights.put(FoodID.onionChop, 5.8F);
        expectedHeights.put(FoodID.meat, 8F);
        expectedHeights.put(FoodID.meatCook, 8F);
        expectedHeights.put(FoodID.bun, 20F);
        expectedHeights.put(FoodID.bottomBun, 10F);
        expectedHeights.put(FoodID.topBun, 12F);
        expectedHeights.put(FoodID.dough, 5F);
        expectedHeights.put(FoodID.cheese, 5F);
        expectedHeights.put(FoodID.uc_pizza, 5F);
        expectedHeights.put(FoodID.c_pizza, 5F);
        expectedHeights.put(FoodID.rawPotato, 5F);
        expectedHeights.put(FoodID.jacketPotato, 5F);
        expectedHeights.put(FoodID.rawJacketPotato, 5F);
        expectedHeights.put(FoodID.burntPatty, 5F);

        for (FoodID foodId : expectedHeights.keySet()) {
            assertEquals("The foodHeight was incorrect.", expectedHeights.get(foodId), FoodItem.foodHeights.get(foodId));
        }
    }
}
