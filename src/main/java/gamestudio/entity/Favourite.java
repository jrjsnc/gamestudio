package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Favourite {
	@Id
	@GeneratedValue
	private int ident;
	private String game;
	private String username;

	public Favourite(String game, String username) {
		super();
		this.game = game;
		this.username = username;
	}

	public Favourite() {

	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = System.getProperty("user.name");
	}

	@Override
	public String toString() {
		return String.format("Favourite (%d %s %s)", ident, game, username);
	}

}
