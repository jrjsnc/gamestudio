package gamestudio.service.impl;

import java.util.List;

import gamestudio.entity.Rating;
import gamestudio.orm.SORM;
import gamestudio.service.RatingService;

public class RatingServiceSORM implements RatingService {
	private SORM sorm = new SORM();

	@Override
	public void setRating(Rating rating) {
		List<Rating> ratings = sorm.select(Rating.class,
				String.format("WHERE game = '%s' AND username = '%s'", rating.getGame(), rating.getUsername()));

		if (ratings.size() != 0)
			sorm.update(rating);
		sorm.insert(rating);
		
		// delete a insert
		
	}

	@Override
	public double getAverageRating(String game) {
		List<Rating> ratings = sorm.select(Rating.class, String.format("WHERE game = '%s'", game));

		double count = 0;
		for (Rating rating : ratings) {
			count += rating.getValue();
		}
		return 1.0d * count / ratings.size();
	}

	@Override
	public int getUserRating(String game, String username) {
		// TODO Auto-generated method stub
		return 0;
	}
}
