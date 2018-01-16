package gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Score;
import gamestudio.game.guess.Logic;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessController extends GeneralController {
	private Logic logic;


	
	
	

//	@RequestMapping("/updateRating_guess")
//	public String updateRating(@RequestParam(value = "value", required = false) String value, Model model) {
//		updateNewRating(value, model);
//		fillModel(model);
//		return "guess";
//	}

//	@RequestMapping("/addComment_guess")
//	public String addComment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {
//		addNewComment(newComment, model);
//		fillModel(model);
//		return "guess";
//	}

//	@RequestMapping("/setFavourite_guess")
//	public String setFavourite(Model model) {
//		setNewFavourite(model);
//		fillModel(model);
//		return "guess";
//	}

	@RequestMapping("/guess")
	public String guess(@RequestParam(value = "value", required = false) String value, Model model) {

		processCommand(value);
		fillModel(model);
		return "guess";
	}

	private void processCommand(String value) {
		try {
			if (!logic.isSolved(Integer.parseInt(value)))
				message = "TRY AGAIN";
			if (logic.isSolved(Integer.parseInt(value))) {
				message = "SOLVED";
				if (userController.isLogged())
					scoreService.addScore(
							new Score(userController.getLoggedPlayer().getLogin(), getGameName(), getWinningTime()));
			}
		} catch (NumberFormatException e) {
			newGame();
		}
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		if (message != "SOLVED") {
			sb.append(
					"Guess the number: <input type='text' name='value' autofocus='autofocus'/> <input type='submit' value='Guess!' />");
		}
		return sb.toString();
	}

	private int getWinningTime() {
		return (int) ((System.currentTimeMillis() - logic.getStartTime()) / 1000);
	}

	private void newGame() {
		logic = new Logic(11);
		message = "";
	}

	@Override
	public String getGameName() {
		return "guess";
	}
}
