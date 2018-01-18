package gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Score;
import gamestudio.game.puzzle.core.Field;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController extends GeneralController {
	private Field field;


	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(value = "value", required = false) String value, Model model) {		
		processCommand(value);
		fillModel(model);
		return "game";
	}
	
	@RequestMapping("/setLevelpuzzle")
	public String setLevelPuzzle(@RequestParam(value = "level", required = false) String level, Model model) {	
		super.level = Integer.parseInt(level);
		field = new Field(super.level);		
		fillModel(model);		
		return "game";
	}

	private void processCommand(String value) {
		try {
			field.moveTile(Integer.parseInt(value));			
			if (field.isSolved()) {
				message = "SOLVED";
				if (userController.isLogged())
					addScore();
			}
		} catch (NumberFormatException e) {
			createField();
		}
	}

	private void addScore() {
		int score = (int)((field.getFinishTime()/1000)*1/level);
		scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), getGameName(), score));
		
	}

	public String render() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='puzzle_field'>\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				sb.append("<td class='puzzle_tile'>\n");
				if (!field.isSolved())
					sb.append(String.format("<a href='/puzzle?value=%d'>\n", field.getTile(row, column)));
				if (field.getTile(row, column) != 0) {
					sb.append(field.getTile(row, column));
				}
				sb.append("</a>");
				sb.append("</td>\n");
			}
			sb.append("</tr>\n");
		}
		sb.append("</table>\n");
		return sb.toString();
	}
	
	private void createField() {
		level = 1;
		field = new Field(level);
		message = "";
	}

	@Override
	protected String getGameName() {
		return "puzzle";
	}	
}
