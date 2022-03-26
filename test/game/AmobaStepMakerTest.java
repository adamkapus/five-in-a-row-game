package game;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
/*Tesztelt metódusok:
 * - public void calculateStep();
 * 
 */
public class AmobaStepMakerTest {
	AmobaStage stage;
	AmobaStepMaker stepMaker;
	
	/*Starting table:
	 * (P = player's marker and C = cumputer's marker) (border markers not visualized here)
	 *   0 1 2 3 4 5
	 * 0 C C C C . .
	 * 1 . . . . . .
	 * 2 . . . . . .
	 * 3 . . P P P P
	 * 4 . . . . . P
	 * 
	 * And the computer steps next.
	 */
	@Before
	public void setUp() {
		stage = new AmobaStage(5, 6, "Circle", null);
		stage.makeStep(new Coordinate(3,2));
		stage.makeStep(new Coordinate(0,0));
		stage.makeStep(new Coordinate(3,3));
		stage.makeStep(new Coordinate(0,1));
		stage.makeStep(new Coordinate(3,4));
		stage.makeStep(new Coordinate(0,2));
		stage.makeStep(new Coordinate(3,5));
		stage.makeStep(new Coordinate(0,3));
		stage.makeStep(new Coordinate(4, 5));
		
		stepMaker = new AmobaStepMaker(stage);
	}
	
	@Test
	public void testStepMaker() {
		//Expected step for computer : (0,4)
		stepMaker.calculateStep();
		Coordinate step = stepMaker.getStep();
		Assert.assertEquals(0 , step.getRow(), 0.0);
		Assert.assertEquals(4 , step.getColumn(), 0.0);
	}
	
	

}
