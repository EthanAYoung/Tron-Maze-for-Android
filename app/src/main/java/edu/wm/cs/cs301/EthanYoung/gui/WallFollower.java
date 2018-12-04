/**
 * @author Ethan Young
 * This driver gets to the end by making the 
 * robot follow the wall to it's left until 
 * it finds the exit
 * Gives instructions to a Robot
 * Receives instructions from Controller
*/

package edu.wm.cs.cs301.EthanYoung.gui;

import edu.wm.cs.cs301.EthanYoung.generation.CardinalDirection;
import edu.wm.cs.cs301.EthanYoung.generation.Cells;
import edu.wm.cs.cs301.EthanYoung.generation.Distance;
import edu.wm.cs.cs301.EthanYoung.gui.Constants.UserInput;
import edu.wm.cs.cs301.EthanYoung.gui.Robot.Direction;
import edu.wm.cs.cs301.EthanYoung.gui.Robot.Turn;

public class WallFollower implements RobotDriver {
	
	BasicRobot rob;
	int wid;
	int hei;
	Distance dist;
	float OGBatt;
	int pathL;
	boolean done;
	
	
	
	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		rob = (BasicRobot) r;
		OGBatt = rob.getBatteryLevel();
		pathL = 0;
		wid = 0;
		hei = 0;
		dist = null;
		done = false;
	}

	@Override
	public void setDimensions(int width, int height) {
		// TODO Auto-generated method stub
		wid = width;
		hei = height;
	}

	@Override
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub
		dist = distance;
	}

	@Override
	public boolean drive2Exit() {
		// TODO Auto-generated method stub
		setDistance(rob.config.getMazedists());
		Cells cells = rob.cells;
		int x;
		int y;
		CardinalDirection currDir;
		while(!rob.isAtExit() && rob.cont.stateNum == 2) {
			x = rob.pos[0];
			y = rob.pos[1];
			currDir = rob.getCurrentDirection();
			System.out.println(currDir + " at " + x + ", " + y);
			if(!cells.canGo(x, y, translateDirection(currDir, Direction.LEFT))) {
				if(!cells.canGo(x, y, translateDirection(currDir, Direction.FORWARD))) {
					System.out.println("Turning right cause left is blocked and front is blocked");
					rotateR();
				}
				else {
					System.out.println("Going Forward cause left is blocked and front is clear");
					go();
				}
			}
			else {
				System.out.println("Going Left cause left is clear");
				rotateL();
				go();
			}
			System.out.println("---");
			//rob.timeDelay(3000);
		}
		if(rob.cont.stateNum != 2) {
			done = true;
			return false;
		}
		x = rob.pos[0];
		y = rob.pos[1];
		currDir = rob.getCurrentDirection();
		if(cells.hasNoWall(x, y, translateDirection(currDir, Direction.FORWARD))) {
			go();
			return false;
		}
		CardinalDirection result = rob.getExitDirection(dist);
		if(result == null) {
			rotateL();
			go();
			result = rob.getExitDirection(dist);
		}
		System.out.println("Facing " + rob.getCurrentDirection() + " but will go " + result);
		//rob.timeDelay(4000);
		rob.switchCDs(rob.getCurrentDirection(), result);
		done = false;
		return false;
	}
	
	public CardinalDirection translateDirection(CardinalDirection currDir, Direction turn) {
		switch(currDir) {
			case North:
				switch(turn) {
					case LEFT:
						return CardinalDirection.East;
					case RIGHT:
						return CardinalDirection.West;
					case FORWARD:
						return CardinalDirection.North;
				}
				break;
			case East:
				switch(turn) {
					case LEFT:
						return CardinalDirection.South;
					case RIGHT:
						return CardinalDirection.North;
					case FORWARD:
						return CardinalDirection.East;
				}
				break;
			case South:
				switch(turn) {
					case LEFT:
						return CardinalDirection.West;
					case RIGHT:
						return CardinalDirection.East;
					case FORWARD:
						return CardinalDirection.South;
				}
				break;
			case West:
				switch(turn) {
					case LEFT:
						return CardinalDirection.North;
					case RIGHT:
						return CardinalDirection.South;
					case FORWARD:
						return CardinalDirection.West;
				}
				break;
		}
		System.out.println("Very very bad");
		return currDir;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return OGBatt - rob.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return pathL;
	}
	
	/**
     * tells the robot to go forward
     */
	public void go() {
		rob.move(1, true);
	}
	
	/**
     * tells the robot to turn left
     */
	public void rotateL() {
		rob.rotate(Turn.LEFT);
	}
	
	/**
     * tells the robot to turn right
     */
	public void rotateR() {
		rob.rotate(Turn.RIGHT);
	}
	
	/**
     * tells the robot to turn all the way around
     */
	public void rotateFull() {
		rob.rotate(Turn.AROUND);
	}

	/**
     * sees what movement key has been pressed
     * Left, Right, Up, or Down
     * @param key is the key that has been pressed
     * @param value does not have any function for this method
     */
	@Override
	public boolean keyDown(UserInput key, int value) {
		// TODO Auto-generated method stub
		if(((BasicRobot)rob).cont == null || ((BasicRobot)rob).cont.stateNum != 2) {
			return false;
		}
		/*switch(key) {
			case Left:
				rotateL();
				return true;
		case Right:
				rotateR();
				return true;
			case Up:
				go();
				return true;
			case Down:
				rotateFull();
				return true;
		}*/
		return false;
	}
}
