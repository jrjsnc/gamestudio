package gamestudio.game.pexeso;

public class Tile {
	private TileState state;
	private int value;
	
	public Tile() {
		state = TileState.CLOSED;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public TileState getState() {
		return state;
	}
	
	public void setState(TileState state) {
		this.state = state;
	}
}
