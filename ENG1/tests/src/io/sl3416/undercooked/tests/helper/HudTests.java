package io.sl3416.undercooked.tests.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import helper.Constants;
import helper.Hud;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
public class HudTests {
    @Test
    public void testConstructor() {
        SpriteBatch batch = mock(SpriteBatch.class);
        Hud hud = new Hud(batch);

        Table[] actor = new Table[] {hud.table};

        assertEquals("Viewport's width was not set correctly", Constants.V_Width, hud.viewport.getWorldWidth(), 0.1f);
        assertEquals("Viewport's height was not set correctly", Constants.V_Height, hud.viewport.getWorldHeight(), 0.1f);
        assertEquals("The batch object in the stage is not as expected", batch, hud.stage.getBatch());
        assertArrayEquals("Actor(s) do not exist in the stage", actor, hud.stage.getActors().toArray());
        assertEquals("Table is not the first actor in stage", hud.table, hud.stage.getActors().get(0));
        assertNotNull("The top of the table is null", hud.table.getTop());
    }
}
