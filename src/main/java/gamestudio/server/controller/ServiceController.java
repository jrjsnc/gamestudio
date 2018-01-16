package gamestudio.server.controller;

import java.util.Date;

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

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ServiceController{	
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private RatingService ratingService;	
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FavouriteService favouriteService;
	
	@RequestMapping("/addComment")
	public String addComment(Comment comment, Model model) {				
		commentService.addComment( new Comment(userController.getLoggedPlayer().getLogin(), comment.getGame(), comment.getContent(), new Date()));			
		return "forward:/" + comment.getGame();
		
	}
	
	@RequestMapping("/updateRating")
	public String updateRating(Rating rating, Model model) {
		ratingService.setRating(new Rating(userController.getLoggedPlayer().getLogin(), rating.getGame(), rating.getValue()));
		return "forward:/" + rating.getGame();
	}
	
	@RequestMapping("/setFavourite")
	public String setFavourite(Favourite favourite, Model model) {		
		favouriteService.addFavourite(new Favourite(favourite.getGame(), userController.getLoggedPlayer().getLogin()));		
		return "forward:/" + favourite.getGame();
	}	

}
