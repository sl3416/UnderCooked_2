package io.sl3416.undercooked.tests;

import com.badlogic.gdx.Gdx;
import jdk.internal.access.JavaIOFileDescriptorAccess;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import com.badlogic.gdx.files.FileHandle;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class AssetTests {
    String filePath = "cooks/control.png";

    @Test
    public void testAssetExists() {
        assertTrue("This test will only pass when the control.png asset exists.", Gdx.files.internal(filePath).exists());
    }
}