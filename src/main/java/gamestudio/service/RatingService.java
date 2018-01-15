package gamestudio.service;

import gamestudio.entity.Rating;

public interface RatingService {

	void setRating(Rating rating);	
	double getAverageRating(String game);
	int getUserRating(String game, String username);
}
