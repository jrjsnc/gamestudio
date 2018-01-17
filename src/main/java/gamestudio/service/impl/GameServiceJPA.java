package gamestudio.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import gamestudio.entity.Game;
import gamestudio.service.GameService;

@Transactional
public class GameServiceJPA implements GameService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Game getGame(String ident) {
		return entityManager.find(Game.class, ident);
	}

	@Override
	public List<Game> getGames() {
		return entityManager.createQuery("SELECT g FROM Game g").getResultList();
	}

	@Override
	public List<Game> getFavouriteGames(String player) {
		return entityManager
				.createQuery("SELECT g FROM Game g where EXISTS "
						+ "(SELECT f FROM Favourite f where g.ident = f.game and f.username = :player)")
				.setParameter("player", player).getResultList();
	}	

	public void setup() {
		Game game;
		game = new Game("mines", "Minesweeper", "Are you fascinated by the beeps of your metal-detector?");
		entityManager.persist(game);
		game = new Game("puzzle", "Stones puzzle", "Every stone has its own place in this universe.");
		entityManager.persist(game);
		game = new Game("guess", "Guess the number", "This thrilling game with insane logic will drive you crazy!");
		entityManager.persist(game);
		game = new Game("pexeso", "Pexeso", "Don't have a mate? You'll definitely find some in this game");
		entityManager.persist(game);

	}

}
