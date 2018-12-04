/**
 * @author Ethan Young
 * This driver finds the exit by having the robot
 * go to the adjacent cell it has visited least
 * Gives instructions to a Robot
 * Receives instructions from Controller
*/

package edu.wm.cs.cs301.EthanYoung.gui;

import java.util.ArrayList;
import java.util.Random;

import edu.wm.cs.cs301.EthanYoung.generation.CardinalDirection;
import edu.wm.cs.cs301.EthanYoung.generation.Cells;
import edu.wm.cs.cs301.EthanYoung.generation.Distance;
import edu.wm.cs.cs301.EthanYoung.gui.Constants.UserInput;
import edu.wm.cs.cs301.EthanYoung.gui.Robot.Direction;
import edu.wm.cs.cs301.EthanYoung.gui.Robot.Turn;

public class Explorer implements RobotDriver {
	
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
		int[][] board = new int[height][width];
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				board[row][col] = 0;
			}
		}
		Direction exit;
		int[] room;
		int[] doorCord;
		int bestDoorVisits;
		while(!rob.isAtExit() && rob.cont.stateNum == 2) {
			exit = rob.canSeeExit();
			if(exit != null) {
				if(exit == Direction.LEFT) {
					rotateL();
					while(!rob.isAtExit()) {
						go();
					}
				}
				if(exit == Direction.RIGHT) {
					rotateR();
					while(!rob.isAtExit()) {
						go();
					}
				}
				if(exit == Direction.FORWARD) {
					while(!rob.isAtExit()) {
						go();
					}
				}
				if(exit == Direction.BACKWARD) {
					rotateFull();
					while(!rob.isAtExit()) {
						go();
					}
				}
			}
			currentX = rob.pos[0];
			currentY = rob.pos[1];
			if(rob.isInsideRoom()) {
				System.out.println("Its in a room");
				bestDoorVisits = 9999;
				doorCord = new int[2];
				room = cells.inWhichRoom(currentX, currentY);
				for(int i = room[2]; i <= room[4]; i++) {
					if(cells.canGo(i, room[3], CardinalDirection.North)) {
						System.out.println(i + ", " + room[3] + " has no North wall");
						if(board[room[3]][i] < bestDoorVisits) {
							bestDoorVisits = board[room[3]][i];
							doorCord[0] = i;
							doorCord[1] = room[3];
						}
					}
					if(cells.canGo(i, room[5], CardinalDirection.South)) {
						System.out.println(i + ", " + room[5] + " has no South wall");
						if(board[room[5]][i] < bestDoorVisits) {
							bestDoorVisits = board[room[5]][i];
							doorCord[0] = i;
							doorCord[1] = room[5];
						}
					}
				}
				for(int i = room[3]; i <= room[5]; i++) {
					if(cells.canGo(i, room[2], CardinalDirection.West)) {
						System.out.println(room[2] + ", " + i + " has no West wall");
						if(board[i][room[2]] < bestDoorVisits) {
							bestDoorVisits = board[i][room[2]];
							doorCord[0] = room[2];
							doorCord[1] = i;
						}
					}
					if(cells.canGo(i, room[4], CardinalDirection.East)) {
						System.out.println(room[2] + ", " + i + " has no East wall");
						if(board[i][room[4]] < bestDoorVisits) {
							bestDoorVisits = board[i][room[4]];
							doorCord[0] = room[4];
							doorCord[1] = i;
						}
					}
				}
				System.out.println("Door Coordinate " + doorCord[0] + ", " + doorCord[1]);
				while(currentX != doorCord[0]) {
					rob.timeDelay(2000);
					if(currentX > doorCord[0]) {
						rob.switchCDs(rob.getCurrentDirection(), CardinalDirection.West);
					}
					if(currentX < doorCord[0]) {
						rob.switchCDs(rob.getCurrentDirection(), CardinalDirection.East);
					}
					currentX = rob.pos[0];
				}
				rob.timeDelay(2000);
				while(currentY != doorCord[1]) {
					rob.timeDelay(2000);
					if(currentY > doorCord[1]) {
						rob.switchCDs(rob.getCurrentDirection(), CardinalDirection.North);
					}
					if(currentY < doorCord[1]) {
						rob.switchCDs(rob.getCurrentDirection(), CardinalDirection.South);
					}
					currentY = rob.pos[1];
				}
				go();
			}
			else {
				canGos = new boolean[] {false, false, false, false};
				nx = currentX;
				ny = currentY;
				vals = new int[4];
				bestDist = 99999;
				result = rob.getCurrentDirection();
				for (int i = 0; i < directions.length; i++) {
					cd = directions[i];
					if (cells.canGo(currentX, currentY, cd)) {
						canGos[i] = true;
						dir = cd.getDirection();
						nextX = currentX+dir[0];
						nextY = currentY+dir[1];
						if ((0 <= nextX && nextX < width) && (0 <= nextY && nextY < height)) {
							nextDistance = board[nextY][nextX];
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
				currDir = rob.getCurrentDirection();
				printInfo(result, currDir, vals[0], vals[1], vals[2], vals[3], canGos, currentX, currentY, nx, ny);
				//rob.timeDelay(2000);
				rob.switchCDs(currDir, result);
				board[ny][nx]++;
				//rob.timeDelay(2000);
			}
		}
		if(rob.cont.stateNum != 2) {
			done = true;
			return false;
		}
		currDir = rob.getCurrentDirection();
		result = rob.getExitDirection(dist);
		rob.switchCDs(currDir, result);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		printInfo(result, currDir, -1, -1, -1, -1, canGos, currentX, currentY, -1, -1);
		done = true;
		return false;
	}
	
	/**
     * A helper method to print out debugging information
     * information deals with moving
     * @param d is the CardinalDirection the robot wants to go
     * @param dOG is the CardinalDirection the robot is currently facing
     * @param n is the # of times visited value of the cell to the north
     * @param s is the # of times visited value of the cell to the south
     * @param e is the # of times visited value of the cell to the east
     * @param w is the # of times visited value of the cell to the west
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
