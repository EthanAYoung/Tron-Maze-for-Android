package edu.wm.cs.cs301.EthanYoung.generation;

public class OrderHelper implements Order {
	private int skill;
	private Builder builder;
	private boolean perfect;
	private MazeConfiguration mazeConfig;
	int percentDone;
	
	/**
     * constructor for OrderHelper.java
     * @param skill is the skill level for the maze
     * @param builder is the type algorithm the maze should be built with
     * @param perfect sets whether or not the maze for this order should have rooms
     */
	public OrderHelper(int skill, Builder builder, boolean perfect){
		this.skill = skill;
		this.builder = builder;
		this.perfect= perfect;
	}
	
	@Override
	public int getSkillLevel() {
		return skill;
	}
	
	@Override
	public Builder getBuilder() {
		return builder;
	}
	
	@Override
	public boolean isPerfect() {
		return perfect;
	}
	
	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		this.mazeConfig = mazeConfig;
	}
	
	@Override
	public void updateProgress(int percentage) {
		this.percentDone = percentage;
	}
	
	/**
     * returns the maze configuration object for this order
     */
	public MazeConfiguration getConfig(){
		return mazeConfig;
	}
	
	/**
     * sets whether or not the maze for this order should have rooms
     */
	public void setPerfection(Boolean n) {
		perfect = n;
	}
}