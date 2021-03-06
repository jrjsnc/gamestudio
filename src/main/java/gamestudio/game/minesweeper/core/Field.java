package gamestudio.game.minesweeper.core;

import java.util.Random;

import javax.naming.OperationNotSupportedException;

public class Field {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;	
	private long startTime;	

	private GameState state = GameState.PLAYING;

	private final Tile[][] tiles;

	
	public Field(int level) {
		this.rowCount = 5 + level;
		this.columnCount = 5 + level;
		this.mineCount = 5*level;
		tiles = new Tile[5 + level][5 + level];
		generate();
		startTime = System.currentTimeMillis();	
	}
	
	public long getFinishTime() {		
		return System.currentTimeMillis() - startTime;
	}


	private void generate() {
		//generateMines();
		generateFixedMines();
		fillWithClues();
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generateMines() {
		int minesToSet = mineCount;
		Random random = new Random();
		while (minesToSet > 0) {
			// vracia od nuly vratane, po parameter excluded
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}
	}

	// only for testing
	private void generateFixedMines() {
		for (int i = 0; i < 3; i++) {
			tiles[0][i] = new Mine();
		}
	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countNeighbourMines(row, column));
				}
			}
		}
	}

	private int countNeighbourMines(int row, int column) {
		int mines = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						if (tiles[aRow][aColumn] instanceof Mine)
							mines++;
					}
				}
			}
		}

		// if (isMineAt(row - 1, column - 1))
		// mines++;
		// if (isMineAt(row - 1, column))
		// mines++;
		// if (isMineAt(row - 1, column + 1))
		// mines++;
		// if (isMineAt(row, column - 1))
		// mines++;
		// if (isMineAt(row, column + 1))
		// mines++;
		// if (isMineAt(row + 1, column - 1))
		// mines++;
		// if (isMineAt(row + 1, column))
		// mines++;
		// if (isMineAt(row + 1, column + 1))
		// mines++;

		return mines;

	}

	private boolean isMineAt(int row, int column) {
		return (row >= 0 && row < rowCount && column >= 0 && column < columnCount
				&& tiles[row][column] instanceof Mine);
	}

	public void markTile(int row, int column) {
		if(state == GameState.PLAYING) {
		Tile tile = tiles[row][column];
		if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.MARKED);
		} else if (tile.getState() == TileState.MARKED) {
			tile.setState(TileState.CLOSED);
		}
	}
	}

	public void openTile(int row, int column) {
		if(state == GameState.PLAYING) {
		Tile tile = tiles[row][column];
		if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.OPEN);

			if (tile instanceof Mine) {
				state = GameState.FAILED;				
				return;
			}
			if (((Clue) tile).getValue() == 0)
				openNeighbourTiles(row, column);

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
			}
		}

	}

	private void openNeighbourTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						openTile(aRow, aColumn);
					}
				}
			}
		}

	}

	private boolean isSolved() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Tile tile = tiles[row][column];
				if (tile instanceof Clue && tile.getState() != TileState.OPEN)
					return false;
			}
		}
		return true;

	}

}
