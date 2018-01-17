package gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import gamestudio.entity.Favourite;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.GameService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

public abstract class GeneralController {

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
	
	@Autowired
	protected GameService gameService;
	
	protected Model gameModel;
	
	protected String message;	

	protected void fillModel(Model model) {
		model.addAttribute("controller", this);
		model.addAttribute("scores", scoreService.getTopScores(getGameName()));
		model.addAttribute("avgRating", ratingService.getAverageRating(getGameName()));
		model.addAttribute("comments", commentService.getComments(getGameName()));
		model.addAttribute("game", gameService.getGame(getGameName()));
		model.addAttribute("games", gameService.getGames());
		if (userController.isLogged()) {
			model.addAttribute("userRating",
					ratingService.getUserRating(getGameName(), userController.getLoggedPlayer().getLogin()));
			model.addAttribute("favourite",
					favouriteService.isFavourite(userController.getLoggedPlayer().getLogin(), getGameName()));
			model.addAttribute("userFavourites",
					favouriteService.getFavourites(userController.getLoggedPlayer().getLogin()));
		}
	}
	
	public String getMessage() {
		return message;
	}
	
	protected abstract String getGameName();
	
	protected abstract String render();
	
	}


