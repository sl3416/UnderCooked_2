//package io.sl3416.undercooked.tests;
//
//import io.dimitris.gemo.Castle;
//import io.dimitris.gemo.World;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//@RunWith(GdxTestRunner.class)
//public class InteractionsTests {
//
//    @Test
//    public void testGrabbing() {
//
//    }
//	@Test
//	public void testDamageCastle() {
//		World world = new World();
//
//		Castle castle = new Castle(world);
//		int healthPoints = castle.getHealthPoints();
//		castle.damage();
//
//		assertEquals(healthPoints - 1, castle.getHealthPoints());
//	}
//
//	@Test
//	public void testDestroyCastle() throws Exception {
//		final World world = new World();
//		final Castle castle = new Castle(world, 1);
//		castle.damage();
//		assertFalse(world.getEntities().contains(castle));
//	}
//
//	@Test(expected = IllegalStateException.class)
//	public void testDamageDestroyedCastle() {
//		Castle castle = new Castle(new World(), 0);
//		castle.damage();
//
//	}
//}
