//@author Ethan Young
package edu.wm.cs.cs301.EthanYoung.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MazeBuilderEller extends MazeBuilder implements Runnable {
	
	/**
     * constructor for MazeBuilderEller.java
     */
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	
	/**
     * constructor for MazeBuilderEller.java
     * @param det is used to determine if the maze is made deterministic
     */
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	
	private int numOfSets;
	
	@Override
	protected void generatePathways() {
		Random rand = new Random();
		int ranInt;
		Wall currWall;
		//cells.initialize();
		//ArrayList<Wall> walls = new ArrayList<Wall>();
		//updateListOfWalls(0, 0, walls);
		Map<Integer, ArrayList<Integer>> sets = new HashMap<Integer, ArrayList<Integer>>();
		int setNum;
		int[][] names = new int[height][width];
		int count = 1;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				names[row][col] = count;
				count++;
			}
		}
		sets = setUpRooms(sets, names);
		int setNum2;
		//Boolean onePerRow;
		ArrayList<Integer> setsWithVertConns;
		//System.out.println("H: " + height + " W: " + width);
		Boolean switchOn;
		for(int row = 0; row < height - 1; row++) {
			//onePerRow = false;
			setsWithVertConns = new ArrayList<Integer>();
			for(int col = 0; col < width; col++) {
				if(cells.getSetNum(row, col) == -1) {
					setNum = names[row][col];
					cells.setSetNum(row, col, setNum);
					sets.put(setNum, new ArrayList<Integer>());
					sets.get(setNum).add(names[row][col]);
				}
				else {
					setNum = cells.getSetNum(row, col);
				}
				ranInt = rand.nextInt(2);
				//System.out.println(ranInt);
				currWall = new Wall(col, row, CardinalDirection.East);
				if(col != width - 1 && setNum != cells.getSetNum(row, col + 1) && ranInt == 0 && cells.canGo(currWall)) {
					setNum2 = cells.getSetNum(row, col + 1);
					//System.out.println(names[row][col] + " in set " + setNum + " grabs Right: " + names[row][col+1] + "--- setNum: " + setNum2);
					cells.deleteWall(currWall);
					//cells.deleteWall(row, col, 0, 1);
					if(setNum2 == -1) {
						//System.out.println(names[row][col+1]);
						//System.out.println(sets.size());
						sets.get(setNum).add(names[row][col + 1]);
						cells.setSetNum(row, col + 1, setNum);
					}
					else {
						sets = mergeSets(setNum, setNum2, sets);
					}
				}
				ranInt = rand.nextInt(2);
				//System.out.println(ranInt);
				currWall = new Wall(col, row, CardinalDirection.South);
				if(setNum != cells.getSetNum(row + 1, col) && (ranInt == 0 || !setsWithVertConns.contains(setNum))) {
					switchOn = true;
					if(!cells.canGo(currWall)) {
						if(!setsWithVertConns.contains(setNum)) {
							switchOn = true;
						}
						else {
							switchOn = false;
						}
					}
					if(switchOn) {
						setNum2 = cells.getSetNum(row + 1, col);
						//System.out.println(names[row][col] + " in set " + setNum + " grabs Down: " + names[row+1][col] + "--- setNum: " + setNum2);
						cells.deleteWall(currWall);
						//cells.deleteWall(row, col, 1, 0);
						if(setNum2 == -1) {
							//System.out.println(names[row+1][col]);
							//System.out.println(sets.size());
							//System.out.println(sets.get(setNum));
							sets.get(setNum).add(names[row + 1][col]);
							cells.setSetNum(row + 1, col, setNum);
						}
						else {
							//System.out.println("What? " + setNum2);
							sets = mergeSets(setNum, cells.getSetNum(row + 1, col), sets);
						}
						//onePerRow = true;
						setsWithVertConns.add(setNum);
					}
				}
			}
		}
		for(int col = 0; col < width - 1; col++) {
			if(cells.getSetNum(height - 1, col) == -1) {
				setNum = names[height - 1][col];
				cells.setSetNum(height - 1, col, setNum);
				sets.put(setNum, new ArrayList<Integer>());
				sets.get(setNum).add(names[height - 1][col]);
			}
			else {
				setNum = cells.getSetNum(height - 1, col);
			}
			if(setNum != cells.getSetNum(height - 1, col + 1)) {
				//System.out.println(names[height - 1][col + 1] + " in set " + setNum + " grabs Down: " + names[height - 1][col + 1] + "--- setNum: " + cells.getSetNum(height - 1, col + 1));
				cells.deleteWall(new Wall(col, height - 1, CardinalDirection.East));
				//cells.deleteWall(height - 1, col, 1, 0);
				setNum2 = cells.getSetNum(height - 1, col + 1);
				if(setNum2 == -1) {
					sets.get(setNum).add(names[height - 1][col + 1]);
					cells.setSetNum(height - 1, col + 1, setNum);
				}
				else {
					sets = mergeSets(setNum, cells.getSetNum(height - 1, col + 1), sets);
				}
			}
		}
		/*for(int i : sets.keySet()) {
			System.out.println(i + ": " + sets.get(i));
		}*/
		//System.out.println(sets.size());
		//cells.setExitPosition(0, 0);
		//setNums = cells.getSetNums();
		numOfSets = sets.size();
		//System.out.println(numOfSets);
		/*for(int row = 0; row < height; row++) {
			System.out.println();
			for(int col = 0; col < width; col++) {
				System.out.print(cells.getSetNum(row, col) + "\t");
			}
			System.out.println();
		}*/
	}
	
	/**
     * helper method for generatePathways()
     * deals with the merging of sets
     * looks through which cells are in which sets
     * and changes their markers if two sets need to be combined
     * @param setNum is the set number for the set thats receiving the new cells
     * @param setNum2 is the set number for the set thats giving away its cells
     * @param sets is the map that shows which cells are in which set
     */
	private Map<Integer, ArrayList<Integer>> mergeSets(int setNum, int setNum2, Map<Integer, ArrayList<Integer>> sets) {
		// TODO Auto-generated method stub
		//System.out.println("1: " + setNum + " --- 2: " + setNum2 + " --- Size: " + sets.size());
		sets.get(setNum).addAll(sets.get(setNum2));
		//System.out.println(sets.size());
		sets.remove(setNum2);
		//System.out.println(sets.size());
		int temp;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				temp = cells.getSetNum(row, col);
				if(temp == setNum2) {
					cells.setSetNum(row, col, setNum);
				}
			}
		}
		return sets;
	}
	
	/**
     * returns the number of different sets
     */
	public int getSetNums() {
		return numOfSets;
	}
	
	/**
     * sets up the map of different sets to include the rooms
     * rooms get their own sets at the start of the generation
     * @param sets is the map that shows which cells are in which set
     * @param names is the 2d array that shows what each cell is named
     */
	public Map<Integer, ArrayList<Integer>> setUpRooms(Map<Integer, ArrayList<Integer>> sets, int[][] names) {
		int setNum;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				setNum = cells.getSetNum(row, col);
				if(setNum != -1) {
					if(!sets.keySet().contains(setNum)) {
						sets.put(setNum, new ArrayList<Integer>());
						sets.get(setNum).add(names[row][col]);
					} else {
						sets.get(setNum).add(names[row][col]);
					}
				}
			}
		}
		return sets;
	}
	

}
