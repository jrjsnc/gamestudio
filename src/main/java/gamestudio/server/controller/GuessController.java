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

	@RequestMapping("/guess")
	public String guess(@RequestParam(value = "value", required = false) String value, Model model) {
		processCommand(value);
		fillModel(model);
		return "game";
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
			sb.append("<form  method='post' name='guessNumber' action='/guess'>");
			sb.append(
					"Guess the number: <input type='text' name='value' autofocus='autofocus'/> "
					+ "<input type='submit' value='Guess!' />");
			sb.append("</form>");			
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
