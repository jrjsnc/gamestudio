package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class Game {

	@Id
	private String ident;

	private String name;

	private String description;

	@Transient
	private double rating;

	public Game(String ident, String name, String description) {
		super();
		this.ident = ident;
		this.name = name;
		this.description = description;
	}

	public Game() {
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
