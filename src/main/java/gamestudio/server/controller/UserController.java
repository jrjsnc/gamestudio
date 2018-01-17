package gamestudio.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Game;
import gamestudio.entity.Player;
import gamestudio.service.FavouriteService;
import gamestudio.service.GameService;
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
	private GameService gameService;
	
	private Player loggedPlayer;	

	@RequestMapping("/")
	public String index(Model model) {
		fillModel(model);
		return "index";
	}

	private void fillModel(Model model) {
		List<Game> games = gameService.getGames();
		addRatingToGames(games);
		model.addAttribute("games", games);
		
		if (isLogged()) {
			//System.err.println(games.toString());
			//model.addAttribute("userFavourites", favouriteService.getFavourites(getLoggedPlayer().getLogin()));
			
			
			model.addAttribute("favouriteGames", gameService.getFavouriteGames(getLoggedPlayer().getLogin()));
			//System.err.println(g.toString() + "ahoj");
		}		
		
	}

	@RequestMapping("/user")
	public String user(Model model) {		
		return "login";
	}

	@RequestMapping("/login")
	public String login(Player player, Model model) {
		loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
		if (isLogged()) {
			//model.addAttribute("userFavourites", favouriteService.getFavourites(getLoggedPlayer().getLogin()));
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
			fillModel(model);
			return "index";
		}		
		model.addAttribute("message", "Name already used. Try another name.");
		return "login";		
	}

	@RequestMapping("/logout")
	public String login(Model model) {
		loggedPlayer = null;
		fillModel(model);
		return "index";
	}
	
	private void addRatingToGames(List<Game> games) {
		for (Game game : games) {
			game.setRating(ratingService.getAverageRating(game.getIdent()));
		}
	}	

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}

}
