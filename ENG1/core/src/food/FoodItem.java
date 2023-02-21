package food;

import java.util.HashMap;

/**
 * A class that controls how {@link FoodID}s are
 * used and handled.
 */
public class FoodItem {

    /** IDs of all the different possible types of food ingredients.*/
    public enum FoodID {
        /** Lettuce */
        lettuce,
        /** Lettuce -&gt; {@link stations.PreparationStation}
         * with type {@link stations.Station.StationID#cut} */
        lettuceChop,
        /** Tomato */
        tomato,
        /** Tomato -&gt; {@link stations.PreparationStation}
         * with type {@link stations.Station.StationID#cut} */
        tomatoChop,
        /** Onion */
        onion,
        /** Onion -&gt; {@link stations.PreparationStation}
         * with type {@link stations.Station.StationID#cut} */
        onionChop,
        /** Meat */
        meat,
        /** Meat -&gt; {@link stations.PreparationStation}
         * with type {@link stations.Station.StationID#fry} */
        meatCook,
        /** Bun — Used only to specify that the {@link stations.Pantry} gives
         * either a {@link #bottomBun} or {@link #topBun}. */
        bun,
        /** Bottom Bun -&gt; Highest bun on {@link FoodStack} is {@code null} or {@link #topBun} */
        bottomBun,
        /** Top Bun -&gt; Highest bun on {@link FoodStack} is {@link #bottomBun} */
        topBun,
        /** Dough */
        dough,
        /** uc_pizza */
        uc_pizza,
        /** C_pizza -&gt; {@link stations.PreparationStation}
         * with type {@link stations.Station.StationID#oven} */
        c_pizza,
        /** Cheese */
        cheese,
        /** Raw Potato */
        rawPotato,
        /** Jacket Potato */
        jacketPotato,
        /** Raw Jacket Potato */
        rawJacketPotato,
        /** Default */
        none
    }

    /** A dict of the pixel height for each food. Used when rendering the FoodStack.*/
    public static final HashMap<FoodID, Float> foodHeights = new HashMap<>();

    static {
        foodHeights.put(FoodID.lettuce, 20F);
        foodHeights.put(FoodID.lettuceChop, 4F);
        foodHeights.put(FoodID.tomato, 20F);
        foodHeights.put(FoodID.tomatoChop, 5.8F);
        foodHeights.put(FoodID.onion, 20F);
        foodHeights.put(FoodID.onionChop, 5.8F);
        foodHeights.put(FoodID.meat, 8F);
        foodHeights.put(FoodID.meatCook, 8F);
        foodHeights.put(FoodID.bun, 20F);
        foodHeights.put(FoodID.bottomBun, 10F);
        foodHeights.put(FoodID.topBun, 12F);
        foodHeights.put(FoodID.dough, 5F);
        foodHeights.put(FoodID.cheese, 5F);
        foodHeights.put(FoodID.uc_pizza, 5F);
        foodHeights.put(FoodID.c_pizza, 5F);
        foodHeights.put(FoodID.rawPotato, 5F);
        foodHeights.put(FoodID.jacketPotato, 5F);
        foodHeights.put(FoodID.rawJacketPotato, 5F);


    }

}

