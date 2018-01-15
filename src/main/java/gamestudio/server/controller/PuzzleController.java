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

	private String message;

	public String getMessage() {
		return message;
	}	

	@RequestMapping("/updateRating_puzzle")
	public String updateRating(@RequestParam(value = "value", required = false) String value, Model model) {
		updateNewRating(value, model);
		fillModel(model);
		return "puzzle";
	}

	@RequestMapping("/addComment_puzzle")
	public String addComment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {
		addNewComment(newComment, model);
		fillModel(model);
		return "puzzle";
	}

	@RequestMapping("/setFavourite_puzzle")
	public String setFavourite(Model model) {
		setNewFavourite(model);
		fillModel(model);
		return "puzzle";
	}

	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(value = "value", required = false) String value, Model model) {
		setGame("Puzzle");
		processCommand(value);
		fillModel(model);
		return "puzzle";
	}

	private void processCommand(String value) {
		try {
			field.moveTile(Integer.parseInt(value));
			if (field.isSolved()) {
				message = "SOLVED";
				if (userController.isLogged())
					scoreService
							.addScore(new Score(userController.getLoggedPlayer().getLogin(), getGame(), getWinningTime()));
			}
		} catch (NumberFormatException e) {
			createField();
		}
	}

	private int getWinningTime() {
		return (int) ((System.currentTimeMillis() - field.getStartTime()) / 1000);
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
		field = new Field(2, 2);
		message = "";
	}
}
