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
	
	@RequestMapping("/setLevelguess")
	public String setLevelGuess(@RequestParam(value = "level", required = false) String level, Model model) {	
		super.level = Integer.parseInt(level);
		message = "guess the number from 0 to "+ super.level * 11;
		logic = new Logic(super.level * 11);		
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
					addScore();
			}
		} catch (NumberFormatException e) {
			newGame();
			System.err.println(logic.getNumber());
		}
	}	
	
	private void addScore() {
		int score = (int)((logic.getFinishTime()/1000)*1/level);
		scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), getGameName(), score));
		
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

	private void newGame() {
		level = 1;
		logic = new Logic(level * 11);
		message = "guess the number from 0 to "+ level * 10;
	}

	@Override
	public String getGameName() {
		return "guess";
	}
}
