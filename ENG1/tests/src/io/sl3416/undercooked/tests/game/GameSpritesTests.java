package io.sl3416.undercooked.tests.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.GameSprites;
import io.sl3416.undercooked.tests.GdxTestRunner;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * The type Game sprites tests.
 */
@RunWith(GdxTestRunner.class)
public class GameSpritesTests {
    /**
     * Test GameSprites constructor for GameSprites class.
     */
    @Test
    public void testGameSpritesConstructor() {
        GameSprites gameSprites = GameSprites.getInstance();

        assertNotNull(gameSprites);
        // Check that resources are created
        assertNotNull(gameSprites.getSprite(GameSprites.SpriteID.COOK, "RIGHT"));
        assertNotNull(gameSprites.getSprite(GameSprites.SpriteID.FOOD, "burger"));
        assertNotNull(gameSprites.getSprite(GameSprites.SpriteID.STATION, "cut"));
        assertNotNull(gameSprites.getSprite(GameSprites.SpriteID.CUSTOMER, "1"));
        assertNotNull(gameSprites.getSprite(GameSprites.SpriteID.POWERUPS, "speed"));
    }

    /**
     * Test getInstance method to get an instance of the game sprites
     */
    @Test
    public void testGetInstance() {
        // checks if it returns INSTANCE of GameSprite and not null
        assertNotNull("Did not return instance of GameSprite", GameSprites.getInstance());
    }

    /**
     * Test createResources method in GameSprites creates resources
     */
    @Test
    public void testCreateResources() {
        GameSprites gameSprites = new GameSprites();

        gameSprites.createResources();
        assertTrue("Resources were not created", gameSprites.spriteMap.size() > 0);

        // Check the number of sprites created
        int expectedSpritesCount = 41;
        int actualSpritesCount = gameSprites.spriteMap.size();
        assertEquals("Resources were not created", expectedSpritesCount, actualSpritesCount);

        // Check if a specific sprite is created
        Sprite sprite = gameSprites.getSprite(GameSprites.SpriteID.POWERUPS, "stations");
        assertTrue("Resources were not created", gameSprites.spriteMap.containsValue(sprite));
    }

    /**
     * Test spriteKey() method in GameSprites class returns a string in the format "%s-%s" for the ordinal of spriteID and spriteName of gamesprite
     */
    @Test
    public void testSpriteKey() {
        GameSprites gameSprites = new GameSprites();
        String key = gameSprites.spriteKey(GameSprites.SpriteID.COOK, "DOWN");
        assertEquals("Did not convert into a string in format \"%s-%s\" correctly", "0-DOWN", key);
    }

    /**
     * Test getSprite() method in GameSprites class
     */
    @Test
    public void testGetSprite() {
        GameSprites gameSprites = new GameSprites();

        Sprite expectedBurgerSprite = gameSprites.spriteMap.get("1-burger");
        Sprite actualBurgerSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, "burger");
        assertEquals("Sprite was not retrieved correctly", expectedBurgerSprite, actualBurgerSprite);

        Sprite expectedCutSprite = gameSprites.spriteMap.get("2-cut");
        Sprite actualCutSprite = gameSprites.getSprite(GameSprites.SpriteID.STATION, "cut");
        assertEquals("Sprite was not retrieved correctly", expectedCutSprite, actualCutSprite);
    }

    /**
     * Test dispose() method in GameSprites class
     */

    @Test
    public void testDispose() {
        HashMap<GameSprites.SpriteID, TextureAtlas> textureAtlases = new HashMap<>();
        textureAtlases.put(GameSprites.SpriteID.COOK, new TextureAtlas("atlas/cook.atlas"));

        GameSprites gameSprites = new GameSprites();

        gameSprites.dispose();

        // Return True if spriteMap is empty after calling dispose()
        assertTrue("Game sprites was not cleared", gameSprites.spriteMap.isEmpty());
    }
}
