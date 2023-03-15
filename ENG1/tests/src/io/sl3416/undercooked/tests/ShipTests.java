//package io.sl3416.undercooked.tests;
//
//import io.dimitris.gemo.Rock;
//import io.dimitris.gemo.Ship;
//import io.dimitris.gemo.World;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(GdxTestRunner.class)
//public class ShipTests {
//
//    @Test
//    public void testMoveLeft() {
//        int x = 10;
//        int y = 10;
//        float delta = 0.001f;
//        Ship ship = new Ship(new World(), x, y);
//        ship.move(true, false, false, false, 1);
//        assertEquals(x - ship.getSpeed(), ship.getX(), delta);
//        assertEquals(y, ship.getY(), delta);
//    }
//
//    @Test
//    public void testCollisionDetection() {
//        int x = 10;
//        int y = 10;
//
//        World world = new World();
//
//        Ship ship = new Ship(world, x, y);
//        new Rock(world, x - ship.getSpeed(), y, 0);
//
//        ship.move(true, false, false, false, 1);
//        assertEquals(x, ship.getX(), 0.001);
//    }
//
//}
