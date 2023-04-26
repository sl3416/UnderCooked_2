package io.sl3416.undercooked.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    String[] filePaths = {
            "atlas/cook.atlas",
            "atlas/cook.png",
            "atlas/customer.atlas",
            "atlas/Customer.png",
            "atlas/food.atlas",
            "atlas/food.png",
            "atlas/powerups.atlas",
            "atlas/powerups.png",
            "atlas/station.atlas",
            "atlas/station.png",

            "cooks/control.png",
            "cooks/cook atlas.tpproj",
            "cooks/hold up.png",
            "cooks/hold right.png",
            "cooks/hold left.png",
            "cooks/hold down.png",
            "cooks/normal up.png",
            "cooks/normal right.png",
            "cooks/normal left.png",
            "cooks/normal down.png",

            "customers/Customer.png",
            "customers/customer.tpproj",
            "customers/CustomerAngry1.png",
            "customers/CustomerAngry2.png",

            "foods/Burger_BunsUpscaled.png",
            "foods/BottomBun.png",
            "foods/Burger_Buns.png",
            "foods/Burger.png",
            "foods/foods atlas.tpproj",
            "foods/Lettuce.png",
            "foods/LettuceChop.png",
            "foods/LettuceUpscaled.png",
            "foods/MargheritaPizza.png",
            "foods/Meat.png",
            "foods/MeatFried.png",
            "foods/Onion.png",
            "foods/OnionChop.png",
            "foods/OnionUpscaled.png",
            "foods/Tomato.png",
            "foods/TomatoChop.png",
            "foods/TomatoUpscaled.png",
            "foods/TopBun.png",

            "Individual_Stations/Bin.png",
            "Individual_Stations/Cutting_Station.png",
            "Individual_Stations/Fryer.png",
            "Individual_Stations/Lock.png",
            "Individual_Stations/stations.tpproj",
            "Individual_Stations/Table.png",
            "Individual_Stations/TableWithChairs.png",

            "Maps/StartMenuBackground.png",
            "Maps/Stations.png",
            "Maps/Stations.tsx",
            "Maps/Stations.xcf",
            "Maps/StationsMap.png",
            "Maps/StationsMap.tmx",
            "Maps/StationsMap1.tmx",
            "Maps/StationsMap2.tmx",

            "powerups/cookingSpeed.png",
            "powerups/money.png",
            "powerups/speed.png",
            "powerups/stations.png",
            "powerups/time.png",

            "Sprites/xcfs/Burger.xcf",
            "Sprites/xcfs/Burger_Buns.xcf",
            "Sprites/xcfs/Cutting_Station.xcf",
            "Sprites/xcfs/Fryer.xcf",
            "Sprites/xcfs/Lettuce.xcf",
            "Sprites/xcfs/MargheritaPizza.xcf",
            "Sprites/xcfs/Meat.xcf",
            "Sprites/xcfs/Onion.xcf",
            "Sprites/xcfs/Table.xcf",
            "Sprites/xcfs/Tomato.xcf"
    };

    @Test
    public void testAssetExists() {
        for (String filePath : filePaths) {
            assertTrue("The asset file path " + filePath + " does not exist", Gdx.files.internal(filePath).exists());
        }
    }
}