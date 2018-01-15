package gamestudio.server.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Comment;
import gamestudio.entity.Favourite;
import gamestudio.entity.Rating;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

//@RequestMapping("/game")
//@Controller
//@Scope(WebApplicationContext.SCOPE_SESSION)
public class GeneralController {

	@Autowired
	protected ScoreService scoreService;

	@Autowired
	protected RatingService ratingService;

	@Autowired
	protected CommentService commentService;

	@Autowired
	protected FavouriteService favouriteService;

	@Autowired
	protected UserController userController;

	private String game;

	protected Model gameModel;

	public void setGame(String game) {
		this.game = game;
		// System.err.println(this.game);
	}

	public String getGame() {
		return game;
	}

	protected void fillModel(Model model) {
		model.addAttribute("controller", this);
		model.addAttribute("scores", scoreService.getTopScores(game));
		model.addAttribute("avgRating", ratingService.getAverageRating(game));
		model.addAttribute("comments", commentService.getComments(game));
		if (userController.isLogged()) {
			model.addAttribute("userRating",
					ratingService.getUserRating(game, userController.getLoggedPlayer().getLogin()));
			model.addAttribute("isFavourite",
					favouriteService.isFavourite(userController.getLoggedPlayer().getLogin(), game));
			model.addAttribute("userFavourites",
					favouriteService.getFavourites(userController.getLoggedPlayer().getLogin()));
		}
	}

	protected void addNewComment(String newComment, Model model) {
		commentService
				.addComment(new Comment(userController.getLoggedPlayer().getLogin(), game, newComment, new Date()));
	}

	protected void updateNewRating(String value, Model model) {
		System.err.println(game);
		try {
			ratingService
					.setRating(new Rating(userController.getLoggedPlayer().getLogin(), game, Integer.parseInt(value)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	protected void setNewFavourite(Model model) {
		favouriteService.addFavourite(new Favourite(game, userController.getLoggedPlayer().getLogin()));
	}

}
