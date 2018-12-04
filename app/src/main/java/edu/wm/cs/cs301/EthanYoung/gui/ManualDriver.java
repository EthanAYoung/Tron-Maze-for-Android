/**
 * @author Ethan Young
 * Allows for manual piloting of the Robot
 * Gives instructions to a Robot
 * Receives instructions from Controller
*/

package edu.wm.cs.cs301.EthanYoung.gui;

import edu.wm.cs.cs301.EthanYoung.generation.Distance;
import edu.wm.cs.cs301.EthanYoung.gui.Robot.Turn;
import edu.wm.cs.cs301.EthanYoung.gui.Constants.UserInput;

public class ManualDriver implements RobotDriver {
	
	Robot rob;
	int wid;
	int hei;
	Distance dist;
	float OGBatt;
	int pathL;
	
	
	
	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		rob = r;
		OGBatt = rob.getBatteryLevel();
		pathL = 0;
		wid = 0;
		hei = 0;
		dist = null;
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
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
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
		switch(key) {
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
		}
		return false;
	}

}
