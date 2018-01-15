package gamestudio;

import java.sql.Timestamp;
import java.util.Date;

import gamestudio.entity.Comment;
import gamestudio.entity.Favourite;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.orm.SORM;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.CommentServiceJDBC;
import gamestudio.service.impl.CommentServiceSORM;
import gamestudio.service.impl.FavouriteServiceSORM;
import gamestudio.service.impl.RatingServiceJDBC;
import gamestudio.service.impl.RatingServiceSORM;
import gamestudio.service.impl.ScoreServiceJDBC;
import gamestudio.service.impl.ScoreServiceSORM;

public class Test {

	public static void main(String[] args) throws Exception {
		// Score score = new Score();
		// score.setUsername("jaro");
		// score.setGame("mines");
		// score.setValue(100);
		//
		// ScoreService scoreService = new ScoreServiceSORM();
		// scoreService.addScore(score);
		//
		// System.out.println(scoreService.getTopScores("mines"));

//		 Comment comment = new Comment();
//		 comment.setUsername("aaa");
//		 comment.setGame("mines");
//		 comment.setContent("Test komentar");
//		 
//		 Date date = new Date();
//		    java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
//		comment.setCreatedOn(sqlDate);
//		 
//		 CommentService commentService = new CommentServiceSORM();
//		 commentService.addComment(comment);
//		
//		 
//		 
//		 System.out.println(commentService.getComments("mines"));

//		Rating rating = new Rating();
//		rating.setUsername("juraj");
//		rating.setGame("Mines");
//		rating.setValue(1000);
//		rating.setIdent(12);
//		RatingService ratingService = new RatingServiceSORM();
//
//		System.out.println(ratingService.getAverageRating("Mines"));
		// System.out.println(ratingService.getAverageRating("mines"));

		// ratingService.setRating(rating);
		// System.out.println(ratingService.getAverageRating("mines"));
		
//		Favourite favourite = new Favourite();
//		favourite.setGame("Stones");
//		favourite.setUsername("Juraj");
//		
		FavouriteService fs = new FavouriteServiceSORM();
//		fs.addFavourite(favourite);
		
		fs.removeFavourite("Juraj", "Mines");
		
		System.out.println(fs.getFavourites("Juraj"));		
	}
}
