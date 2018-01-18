package gamestudio.game.guess;

import java.util.Random;

public class Logic {

	private final int range;
	private final int number;
	private long startTime;	

	public Logic(int range) {
		this.range = range;
		number = getRandom(range);
		startTime = System.currentTimeMillis();
	}	
	
	public long getFinishTime() {		
		return System.currentTimeMillis() - startTime;
	}

	public int getRange() {
		return range;
	}

	public int getNumber() {
		return number;
	}

	private int getRandom(int range) {
		return new Random().nextInt(range);
	}

	public boolean isSolved(int guess) {
		return guess == number;
	}
}
