package io.sl3416.undercooked.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {
    String filePath = "cooks/control.png";

    @Test
    public void testAssetExists() {
        assertTrue("This test will only pass when the control.png asset exists.", Gdx.files.internal(filePath).exists());
    }
}