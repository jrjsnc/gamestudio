package gamestudio.service;

import java.util.List;

import gamestudio.entity.Game;

public interface GameService {
	
	Game getGame(String ident);
	List<Game> getGames();
	List<Game> getFavouriteGames(String player);
}
