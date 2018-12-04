/**
 * @author Ethan Young
 * A basic maze exploring robot that follows instructions
 * Receives instructions from a driver
 * Takes data from Controller
 * Takes data from MazeConfiguration
 * Takes data from Cells
*/


package edu.wm.cs.cs301.EthanYoung.gui;

import edu.wm.cs.cs301.EthanYoung.generation.CardinalDirection;
import edu.wm.cs.cs301.EthanYoung.generation.Cells;
import edu.wm.cs.cs301.EthanYoung.generation.Distance;
import edu.wm.cs.cs301.EthanYoung.generation.MazeConfiguration;
import edu.wm.cs.cs301.EthanYoung.gui.Constants.UserInput;

public class BasicRobot implements Robot {
	
	float batt;
	Controller cont;
	int[] pos;
	int pathL;
	MazeConfiguration config;
	Cells cells;
	boolean hasRS;
	public CardinalDirection currDir;
	boolean leftSen;
	boolean righSen;
	boolean fronSen;
	boolean backSen;
	public boolean dead;
	
	/**
     * Constructor for BasicRobot.java
     * Initializes many of the global variables
     */
	public BasicRobot() {
		batt = 3000;
		pos = new int[2];
		leftSen = true;
		righSen = true;
		fronSen = true;
		backSen = true;
		pathL = 0;
		dead = false;
		hasRS = true;
		currDir = CardinalDirection.North;
	}

	@Override
	public void rotate(Turn turn) {
		// TODO Auto-generated method stub
		currDir = getCurrentDirection();
		if(hasStopped() == false) {
			switch(turn) {
				case LEFT:
					if(batt >= 3) {
						batt += -3;
						cont.states[2].keyDown(UserInput.Left, 0);
					}
					break;
				case RIGHT:
					if(batt >= 3) {
						batt += -3;
						cont.states[2].keyDown(UserInput.Right, 0);
					}
					break;
				case AROUND:
					if(batt >= 6) {
						batt += -6;
						cont.states[2].keyDown(UserInput.Right, 0);
						cont.states[2].keyDown(UserInput.Right, 0);
					}
					break;
			}
		}
	}

	@Override
	public void move(int distance, boolean manual) {
		// TODO Auto-generated method stub
		if(hasStopped() == false && !cells.hasWall(pos[0], pos[1], getCurrentDirection())) {
			for(int i = 0; i < distance; i++) {
				cont.states[2].keyDown(UserInput.Up, 0);
				batt -= 5;
				pos = cont.getCurrentPosition();
				pathL += 1;
				if(hasStopped() == true) {
					break;
				}
			}
		}
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		// TODO Auto-generated method stub
		if(config.getHeight() <= pos[1] || config.getWidth() <= pos[0] || config.getHeight() < 0 || config.getWidth() < 0) {
			new Exception("OUT OF BOUNDS");
		}
		pos = cont.getCurrentPosition();
		return null;
	}

	@Override
	public void setMaze(Controller controller) {
		// TODO Auto-generated method stub
		config = controller.getMazeConfiguration();
		cont = controller;
		cells = config.getMazecells();
	}

	@Override
	public boolean isAtExit() {
		// TODO Auto-generated method stub
		batt--;
		if(config.getDistanceToExit(pos[0], pos[1]) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		if(distanceToObstacle(direction) == Integer.MAX_VALUE)
			return true;
		return false;
	}

	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		if(!hasRoomSensor())
			new UnsupportedOperationException("NEED ROOM SENSOR");
		else if(cells.isInRoom(pos[0], pos[1]))
			return true;
		return false;
	}

	@Override
	public boolean hasRoomSensor() {
		// TODO Auto-generated method stub
		return hasRS;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		// TODO Auto-generated method stub
		return cont.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		// TODO Auto-generated method stub
		return batt;
	}

	@Override
	public void setBatteryLevel(float level) {
		// TODO Auto-generated method stub
		batt = level;
	}

	@Override
	public int getOdometerReading() {
		// TODO Auto-generated method stub
		return pathL;
	}

	@Override
	public void resetOdometer() {
		// TODO Auto-generated method stub
		pathL = 0;
	}

	@Override
	public float getEnergyForFullRotation() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		if(batt <= 0) {
			dead = true;
			cont.switchFromPlayingToWinning(getOdometerReading());
			return true;
		}
		//if(cells.hasWall(pos[0], pos[1], getCurrentDirection())) {
			//System.out.println("can't theres a wall");
			//return true;
		//}
		return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		if(hasDistanceSensor(direction) == false){
			new UnsupportedOperationException("NO SENSOR");
		}
		batt--;
		currDir = getCurrentDirection();
		switch(currDir) {
			case North:
				switch (direction) {
					case LEFT:
						return checkWest();
					case RIGHT:
						return checkEast();
					case FORWARD:
						return checkNorth();
					case BACKWARD:
						return checkSouth();
				}
				break;
			case South:
				switch (direction) {
					case LEFT:
						return checkEast();
					case RIGHT:
						return checkWest();
					case FORWARD:
						return checkSouth();
					case BACKWARD:
						return checkNorth();
				}
				break;
			case East:
				switch (direction) {
					case LEFT:
						return checkNorth();
					case RIGHT:
						return checkSouth();
					case FORWARD:
						return checkEast();
					case BACKWARD:
						return checkWest();
				}
				break;
			case West:
				switch (direction) {
					case LEFT:
						return checkSouth();
					case RIGHT:
						return checkNorth();
					case FORWARD:
						return checkWest();
					case BACKWARD:
						return checkEast();
				}
				break;
		}
		
		return 0;
	}
	
	/**
     * helper method for distanceToObstacle()
     * finds the distance to the first obstacle to the south
     */
	private int checkSouth() {
		int count = 0;
		for(int i = pos[1]; i < config.getHeight(); i++) {
			if(cells.hasWall(pos[0], i, CardinalDirection.South))
				return count;
			count++;
		}
		if(cells.hasNoWall(pos[0], config.getHeight()-1, CardinalDirection.South)) {
			return Integer.MAX_VALUE;
		}
		return count;
	}
	
	/**
     * helper method for distanceToObstacle()
     * finds the distance to the first obstacle to the north
     */
	private int checkNorth() {
		int count = 0;
		for(int i = pos[1]; i >= 0; i--) {
			if(cells.hasWall(pos[0], i, CardinalDirection.North))
				return count;
			count++;
		}
		if(cells.hasNoWall(pos[0], 0, CardinalDirection.North)) {
			return Integer.MAX_VALUE;
		}
		return count;
	}
	
	/**
     * helper method for distanceToObstacle()
     * finds the distance to the first obstacle to the east
     */
	private int checkEast() {
		int count = 0;
		for(int i = pos[0]; i < config.getWidth(); i++) {
			if(cells.hasWall(i, pos[1], CardinalDirection.East))
				return count;
			count++;
		}
		if(cells.hasNoWall(config.getWidth()-1, pos[1], CardinalDirection.East)) {
			return Integer.MAX_VALUE;
		}
		return count;
	}
	
	/**
     * helper method for distanceToObstacle()
     * finds the distance to the first obstacle to the west
     */
	private int checkWest() {
		int count = 0;
		for(int i = pos[0]; i >= 0; i--) {
			if(cells.hasWall(i, pos[1], CardinalDirection.West))
				return count;
			count++;
		}
		if(cells.hasNoWall(0, pos[1], CardinalDirection.West)) {
			return Integer.MAX_VALUE;
		}
		return count;
	}
	
	/**
     * checks if the robot can see the exit
     * @return the Direction of the exit
     * finds the distance to the first obstacle to the west
     */
	public Direction canSeeExit() {
		int max = Integer.MAX_VALUE;
		if(distanceToObstacle(Direction.FORWARD) == max) {
			return Direction.FORWARD;
		}
		if(distanceToObstacle(Direction.LEFT) == max) {
			return Direction.LEFT;
		}
		if(distanceToObstacle(Direction.RIGHT) == max) {
			return Direction.RIGHT;
		}
		if(distanceToObstacle(Direction.BACKWARD) == max) {
			return Direction.BACKWARD;
		}
		return null;
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		// TODO Auto-generated method stub
		switch(direction) {
			case LEFT:
				return leftSen;
			case RIGHT:
				return righSen;
			case FORWARD:
				return fronSen;
			case BACKWARD:
				return backSen;
		}
		return false;
	}
	
	/**
     * Switches the CardinalDirection of the robot
     * A helper method to turn the robot
     * @param currDir is the current CardinalDirection of the robot
     * @param result is the CardinalDirection the robot should have in the end
     */
	public void switchCDs(CardinalDirection currDir, CardinalDirection result) {
		switch(currDir) {
			case North:
				switch(result) {
					case North:
						move(1, true);
						break;
					case East:
						rotate(Turn.LEFT);
						move(1, true);
						break;
					case South:
						rotate(Turn.AROUND);
						move(1, true);
						break;
					case West:
						rotate(Turn.RIGHT);
						move(1, true);
						break;
				}
				break;
			case East:
				switch(result) {
					case North:
						rotate(Turn.RIGHT);
						move(1, true);
						break;
					case East:
						move(1, true);
						break;
					case South:
						rotate(Turn.LEFT);
						move(1, true);
						break;
					case West:
						rotate(Turn.AROUND);
						move(1, true);
						break;
				}
				break;
			case South:
				switch(result) {
					case North:
						rotate(Turn.AROUND);
						move(1, true);
						break;
					case East:
						rotate(Turn.RIGHT);
						move(1, true);
						break;
					case South:
						move(1, true);
						break;
					case West:
						rotate(Turn.LEFT);
						move(1, true);
						break;
				}
				break;
			case West:
				switch(result) {
					case North:
						rotate(Turn.LEFT);
						move(1, true);
						break;
					case East:
						rotate(Turn.AROUND);
						move(1, true);
						break;
					case South:
						rotate(Turn.RIGHT);
						move(1, true);
						break;
					case West:
						move(1, true);
						break;
				}
				break;
		}
	}
	
	/**
     * A helper method to get the CardinalDirection of the exit
     */
	public CardinalDirection getExitDirection(Distance dist) {
		int[] exit = dist.getExitPosition();
		int x = exit[0];
		int y = exit[1];
		int width = config.getWidth();
		int height = config.getHeight();
		System.out.println("X: " + x + " and Y: " + y);
		/*if(x == 0 && y == 0) {
			if(cells.hasNoWall(x, y, CardinalDirection.North))
				return CardinalDirection.North;
			if(cells.hasNoWall(x, y, CardinalDirection.West))
				return CardinalDirection.West;
		}
		if(x == 0 && y == width-1) {
			if(cells.hasNoWall(x, y, CardinalDirection.North))
				return CardinalDirection.North;
			if(cells.hasNoWall(x, y, CardinalDirection.East))
				return CardinalDirection.East;
		}
		if(x == height-1 && y == 0) {
			if(cells.hasNoWall(x, y, CardinalDirection.South))
				return CardinalDirection.South;
			if(cells.hasNoWall(x, y, CardinalDirection.West))
				return CardinalDirection.West;
		}
		if(x == height-1 && y == width-1) {
			if(cells.hasNoWall(x, y, CardinalDirection.South))
				return CardinalDirection.South;
			if(cells.hasNoWall(x, y, CardinalDirection.East))
				return CardinalDirection.East;
		}*/
		if(x == 0 && cells.hasNoWall(x, y, CardinalDirection.West))
			return CardinalDirection.West;
		if(x == height - 1 && cells.hasNoWall(x, y, CardinalDirection.East))
			return CardinalDirection.East;
		if(y == 0 && cells.hasNoWall(x, y, CardinalDirection.North))
			return CardinalDirection.North;
		if(y == width - 1 && cells.hasNoWall(x, y, CardinalDirection.South))
			return CardinalDirection.South;
		System.out.println("Houston we have a problem");
		return null;
	}
	
	/**
     * A helper method to pause the program
     * useful to see what decisions the driver is making
     */
	public void timeDelay(long t) {
	    try {
	        Thread.sleep(t);
	    } catch (InterruptedException e) {}
	}

}
