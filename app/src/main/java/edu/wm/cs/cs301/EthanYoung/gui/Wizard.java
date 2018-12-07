/**
 * @author Ethan Young
 * This driver follows the shortest path to the exit
 * by deciding it's next move based on which adjacent cell
 * has the shortest distance to the exit and choosing that one
 * Gives instructions to a Robot
 * Receives instructions from Controller
*/

package edu.wm.cs.cs301.EthanYoung.gui;

import edu.wm.cs.cs301.EthanYoung.generation.CardinalDirection;
import edu.wm.cs.cs301.EthanYoung.generation.Cells;
import edu.wm.cs.cs301.EthanYoung.generation.Distance;
import edu.wm.cs.cs301.EthanYoung.gui.Constants.UserInput;
import edu.wm.cs.cs301.EthanYoung.gui.Robot.Turn;

public class Wizard implements RobotDriver {

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
		int currentX = rob.pos[0];
		int currentY = rob.pos[1];
		int[] dir;
		int nextX;
		int nextY;
		int width = rob.config.getWidth();
		int height = rob.config.getHeight();
		int[][] dists;
		CardinalDirection result = rob.getCurrentDirection();
		//int bestDist = rob.config.getDistanceToExit(currentX, currentY);
		int bestDist = dist.getDistanceValue(currentX, currentY);
		int nextDistance;
		CardinalDirection currDir;
		CardinalDirection cd;
		CardinalDirection[] directions = new CardinalDirection[] {CardinalDirection.North, CardinalDirection.South, CardinalDirection.East, CardinalDirection.West};
		int[] vals = new int[4];
		boolean[] canGos = new boolean[4];
		int nx;
		int ny;
		boolean switchOn;
		while(!rob.isAtExit()) {
			canGos = new boolean[] {false, false, false, false};
			nx = currentX;
			ny = currentY;
			currentX = rob.pos[0];
			currentY = rob.pos[1];
			bestDist = dist.getDistanceValue(currentX, currentY);
			result = rob.getCurrentDirection();
			for (int i = 0; i < directions.length; i++) {
				cd = directions[i];
				if (cells.canGo(currentX, currentY, cd)) {
					canGos[i] = true;
					dir = cd.getDirection();
					nextX = currentX+dir[0];
					nextY = currentY+dir[1];
					if ((0 <= nextX && nextX < width) && (0 <= nextY && nextY < height) && canGos[i]) {
						nextDistance = dist.getDistanceValue(nextX, nextY);
						vals[i] = nextDistance;
						if (bestDist > nextDistance) {
							bestDist = nextDistance;
							result = cd;
							nx = nextX;
							ny = nextY;
						}
					}
				}
			}
			switchOn = false;
			switch(result) {
				case North:
					switchOn = canGos[0];
					break;
				case South:
					switchOn = canGos[1];
					break;
				case East:
					switchOn = canGos[2];
					break;
				case West:
					switchOn = canGos[3];
					break;
			}
			if(switchOn) {
				currDir = rob.getCurrentDirection();
				printInfo(result, currDir, vals[0], vals[1], vals[2], vals[3], canGos, currentX, currentY, nx, ny);
				rob.switchCDs(currDir, result);
				//rob.timeDelay(5000);
			}
		}
		currDir = rob.getCurrentDirection();
		currentX = rob.pos[0];
		currentY = rob.pos[1];
		result = rob.getExitDirection(dist);
		if(result == null) {
			rotateL();
			go();
			result = rob.getExitDirection(dist);
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("I am facing " + currDir + " and the exit is to the " + result);
		//rob.timeDelay(3000);
		rob.switchCDs(currDir, result);
		done = true;
		return false;
	}
	
	/**
     * A helper method to print out debugging information
     * information deals with moving
     * @param d is the CardinalDirection the robot wants to go
     * @param dOG is the CardinalDirection the robot is currently facing
     * @param n is the distance value of the cell to the north
     * @param s is the distance value of the cell to the south
     * @param e is the distance value of the cell to the east
     * @param w is the distance value of the cell to the west
     * @param q is an array that shows whether or not the robot could go in the 4 CDs
     * @param cx is the current x coordinate of the robot
     * @param cy is the current y coordinate of the robot
     * @param nx is the next x coordinate of the robot
     * @param ny is the next y coordinate of the robot
     */
	public void printInfo(CardinalDirection d, CardinalDirection dOG, int n, int s, int e, int w, boolean[] q, int cx, int cy, int nx, int ny) {
		System.out.println("Best is " + d + " but I am facing " + dOG);
		System.out.println("North: " + n + " South: " + s + " East: " + e + " West: " + w);
		System.out.println("Could go North: " + q[0] + " South: " + q[1] + " East: " + q[2] + " West: " + q[3]);
		System.out.println("Will go from (" + cx + ", " + cy + ") to (" + nx + ", " + ny + ")");
		System.out.println("***********************************************************************");
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
		/*if(((BasicRobot)rob).cont == null || ((BasicRobot)rob).cont.stateNum != 2) {
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
		}*/
		return false;
	}

}
