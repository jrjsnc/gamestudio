package gamestudio.service.impl;

import java.util.List;

import gamestudio.entity.Favourite;
import gamestudio.orm.SORM;
import gamestudio.service.FavouriteService;

public class FavouriteServiceSORM implements FavouriteService {
	private SORM sorm = new SORM();

	@Override
	public void addFavourite(Favourite favourite) {
		sorm.insert(favourite);
	}

	@Override
	public void removeFavourite(String username, String game) {
		List<Favourite> favourites = sorm.select(Favourite.class,
				String.format("WHERE username = '%s' AND game = '%s'", username, game));
		
		if(favourites.size() != 0)
			sorm.delete(favourites.get(0));
	}

	@Override
	public List<Favourite> getFavourites(String username) {
		List<Favourite> favourites = sorm.select(Favourite.class,
				String.format("WHERE username = '%s'", username));
		return favourites;
	}

	@Override
	public boolean isFavourite(String username, String game) {
		// TODO Auto-generated method stub
		return false;
	}

}
