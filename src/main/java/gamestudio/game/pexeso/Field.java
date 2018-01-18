package gamestudio.game.pexeso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Field {
	private final int rowCount;
	private final int columnCount;
	private int tileCount;
	private GameState state;

	private Tile[][] tiles;
	private int[][] openedTiles = new int[2][2];;

	public Field(int level) {
		this.rowCount = 4;
		this.columnCount = level+1;
		tileCount = rowCount * columnCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		setState(GameState.PLAYING);
		List<Integer> list = new ArrayList<>();

		int counter = 0;
		for (int i = 0; i < tileCount / 2; i++) {
			list.add(counter);
			list.add(counter);
			counter++;
		}
		Collections.shuffle(list);

		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = new Tile();
				tiles[row][column].setValue(list.get((columnCount * row) + column));
			}

		}

	}

	public void openTile(int row, int column) {
		if (row < rowCount && column < columnCount) {
			Tile tile = tiles[row][column];
			if (tile.getState() == TileState.CLOSED) {
				tile.setState(TileState.OPENED);
			}
		}
	}

	public boolean isSolved() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column].getState() != TileState.PAIRED)
					return false;
			}
		}
		return true;
	}

	public void controlMatch() {
		int counter = 0;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column].getState() == TileState.OPENED) {
					openedTiles[counter][0] = row;
					openedTiles[counter][1] = column;
					counter++;
				}
			}
		}
		
		if (counter == 2) {
			 
             if(tiles[openedTiles[0][0]][openedTiles[0][1]].getValue() == tiles[openedTiles[1][0]][openedTiles[1][1]].getValue()) {
            	 tiles[openedTiles[0][0]][openedTiles[0][1]].setState(TileState.PAIRED);
            	 tiles[openedTiles[1][0]][openedTiles[1][1]].setState(TileState.PAIRED);
            	 openedTiles = new int[2][2];
             } else {
            	 tiles[openedTiles[0][0]][openedTiles[0][1]].setState(TileState.CLOSED);
            	 tiles[openedTiles[1][0]][openedTiles[1][1]].setState(TileState.CLOSED);
            	 openedTiles = new int[2][2];
             }
		}
		
		if(isSolved()) {
			setState(GameState.SOLVED);
		}
	}
	
	public void move(int row, int column) {
		controlMatch();
		openTile(row, column);
	}
	


}
