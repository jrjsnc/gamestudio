package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 CREATE TABLE rating(
ident INTEGER PRIMARY KEY,
username VARCHAR(32) NOT NULL,
game VARCHAR(32) NOT NULL,
value INTEGER NOT NULL,
UNIQUE (username, game)

)
 */
@Entity
public class Rating {
	
	@Id
	@GeneratedValue
	private int ident;
	private String username;
	private String game;
	private int value;

	public Rating() {

	}

	public Rating(String username, String game, int value) {
		super();
		this.username = username;
		this.game = game;
		this.value = value;
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("Rating (%d %s %s %d)", ident, username, game, value);
	}

}
