package io.sl3416.undercooked.tests.food;

import com.badlogic.gdx.utils.Array;
import food.FoodItem;
import food.FoodItem.FoodID;
import food.FoodStack;
import food.Recipe;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class RecipeTests {
    public String testRecipe;

    @Test
    public void testGenerateRecipes() {
        Recipe recipe = new Recipe();

        recipe.generateRecipes("Lettuce Tomato Salad", recipe.allCombos(FoodID.lettuceChop, FoodID.tomatoChop));
        recipe.generateRecipes("Lettuce Onion Tomato Salad", recipe.allCombos(FoodID.lettuceChop, FoodID.onionChop, FoodID.tomatoChop));

        Array<FoodStack> recipe1 = recipe.getRecipeCombos("Lettuce Tomato Salad");
        Array<FoodStack> recipe2 = recipe.getRecipeCombos("Lettuce Onion Tomato Salad");

        assertNotNull("Recipe was not generated correctly", recipe1);
        assertNotNull("Recipe was not generated correctly", recipe2);

        assertEquals("Recipe was not generated correctly", 2, recipe1.size);
        assertEquals("Recipe was not generated correctly", 6, recipe2.size);
    }

    @Test
    public void testMatchesRecipe() {
        // Create a new Recipe object
        Recipe recipe = new Recipe();

        // Test recipes
        recipe.generateRecipes("Margherita Pizza", recipe.allCombos(FoodID.c_pizza));
        recipe.generateRecipes("Lettuce Onion Tomato Salad", recipe.allCombos(FoodID.lettuceChop, FoodID.onionChop, FoodID.tomatoChop));

        // Create a new FoodStack with the same ingredients, in a different order
        FoodStack foodStack1 = new FoodStack(FoodID.c_pizza);
        FoodStack foodStack2 = new FoodStack(FoodID.lettuceChop, FoodID.onionChop, FoodID.tomatoChop);

        // Test case if recipe exists
        assertFalse("matchesRecipe() did not work as expected", recipe.matchesRecipe(foodStack1, testRecipe));
        assertFalse("matchesRecipe() did not work as expected", recipe.matchesRecipe(foodStack2, testRecipe));

        // Test FoodStack matches the recipe
        assertTrue("matchesRecipe() did not work as expected", recipe.matchesRecipe(foodStack1, "Margherita Pizza"));
        assertTrue("matchesRecipe() did not work as expected", recipe.matchesRecipe(foodStack2, "Lettuce Onion Tomato Salad"));
    }

    @Test
    public void testGetRecipeCombos() {
        Recipe recipe = new Recipe();

        // get the recipe combos
        Array<FoodStack> combos = recipe.getRecipeCombos("Lettuce Tomato Salad");

        // create an expected result
        Array<FoodStack> expected = new Array<>();
        expected.add(new FoodStack(FoodID.lettuceChop, FoodID.tomatoChop));
        expected.add(new FoodStack(FoodID.tomatoChop, FoodID.lettuceChop));

        // compare the expected result to the actual result
        assertEquals("Recipe combos getter does not get the expected recipe compos", expected.toString(), combos.toString());
    }

    @Test
    public void testRandomRecipe() {
        Recipe recipe = new Recipe();

        String randomRecipe = Recipe.randomRecipe();
        assertTrue("Recipe was not randomly generated", recipe.recipeNames.contains(randomRecipe, false));

        String randomRecipe2 = Recipe.randomRecipe();
        assertTrue("Recipe was not randomly generated", recipe.recipeNames.contains(randomRecipe2, false));
    }
}