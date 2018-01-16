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

	protected Model gameModel;
	
	protected String message;	

	protected void fillModel(Model model) {
		model.addAttribute("controller", this);
		model.addAttribute("scores", scoreService.getTopScores(getGameName()));
		model.addAttribute("avgRating", ratingService.getAverageRating(getGameName()));
		model.addAttribute("comments", commentService.getComments(getGameName()));
		model.addAttribute("game", getGameName());
		if (userController.isLogged()) {
			model.addAttribute("userRating",
					ratingService.getUserRating(getGameName(), userController.getLoggedPlayer().getLogin()));
			model.addAttribute("favourite",
					favouriteService.isFavourite(userController.getLoggedPlayer().getLogin(), getGameName()));
			model.addAttribute("userFavourites",
					favouriteService.getFavourites(userController.getLoggedPlayer().getLogin()));
		}
	}

	protected void setNewFavourite(Model model) {
		favouriteService.addFavourite(new Favourite(getGameName(), userController.getLoggedPlayer().getLogin()));
	}
	
	public String getMessage() {
		return message;
	}
	
	protected abstract String getGameName();
	
	public abstract String render();
	
	}


