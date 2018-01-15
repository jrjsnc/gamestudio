package gamestudio.service;

import java.util.List;

import gamestudio.entity.Favourite;

public interface FavouriteService {
	
	void addFavourite(Favourite favourite);	
	void removeFavourite(String username, String game);	
	List<Favourite> getFavourites(String username);
	boolean isFavourite(String username, String game);	
}
