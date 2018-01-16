package gamestudio.server.controller;

import java.util.Date;

import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Comment;
import gamestudio.entity.Player;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.PlayerService;
import gamestudio.service.RatingService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private FavouriteService favouriteService;

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private CommentService commentService;
	
	private Player loggedPlayer;
	
//	
//	@RequestMapping("/addComment")
//	public String addComment(@RequestParam(value = "content", required = false)  String content, @RequestParam(value = "game", required = false) String game, Model model) {
//		Comment comment = new Comment(getLoggedPlayer().getLogin(), game, content, new Date());		
//		System.out.println(">>>>>>>" + comment);
//		commentService.addComment(comment);			
//		return "forward:/" + comment.getGame(); 
//	}
	

	@RequestMapping("/")
	public String index(Model model) {
		fillModel(model);
		return "index";
	}

	private void fillModel(Model model) {
		if (isLogged()) {
			model.addAttribute("userFavourites", favouriteService.getFavourites(getLoggedPlayer().getLogin()));
		}
		model.addAttribute("minesRating", ratingService.getAverageRating("mines"));
		model.addAttribute("puzzleRating", ratingService.getAverageRating("puzzle"));
		model.addAttribute("guessRating", ratingService.getAverageRating("guess"));
		// model.addAttribute("pexesoRating", ratingService.getAverageRating("pexeso"))
	}

	@RequestMapping("/user")
	public String user(Model model) {		
		return "login";
	}

	@RequestMapping("/login")
	public String login(Player player, Model model) {
		loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
		if (isLogged()) {
			model.addAttribute("userFavourites", favouriteService.getFavourites(getLoggedPlayer().getLogin()));
			fillModel(model);
			return "index";
		}
		return "login";
	}

	@RequestMapping("/register")
	public String register(Player player, Model model) {
		if (!playerService.nameTaken(player.getLogin())) {
			playerService.register(player);
			loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
			model.addAttribute("message", "");
			return "index";
		}		
		model.addAttribute("message", "Name already used. Try another name.");
		return "login"; 
		//return isLogged() ? "index" : "login";
	}

	@RequestMapping("/logout")
	public String login(Model model) {
		loggedPlayer = null;
		return "index";
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}

}
