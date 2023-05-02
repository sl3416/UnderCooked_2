package io.sl3416.undercooked.tests.helper;

import helper.BodyHelper;
import io.sl3416.undercooked.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Rectangle;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class BodyHelperTests {
    /**
     * Test BodyHelperCollision for when the bodies collide.
     */
    @Test
    public void testBodyHelperCollisionTrue() {
        Rectangle testRectangle = new Rectangle(1, 2, 5, 5);
        Rectangle testRectangle2 = new Rectangle(0, 0, 5, 5);
        assertTrue("Body Helper collisions failed.", BodyHelper.checkCollision(testRectangle, testRectangle2));
    }

    /**
     * Test BodyHelperCollision for when the bodies do not collide.
     */
    @Test
    public void testBodyHelperCollisionFalse() {
        Rectangle testFalseRectangle = new Rectangle(10,10, 5, 5);
        Rectangle testRectangle2 = new Rectangle(0, 0, 5, 5);
        assertFalse("Body Helper collisions failed.",BodyHelper.checkCollision(testFalseRectangle,testRectangle2));
    }
}

