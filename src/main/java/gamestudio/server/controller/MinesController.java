package gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.game.minesweeper.core.Clue;
import gamestudio.game.minesweeper.core.Field;
import gamestudio.game.minesweeper.core.GameState;
import gamestudio.game.minesweeper.core.Tile;
import gamestudio.game.minesweeper.core.TileState;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController extends GeneralController {
	private Field field;

	private boolean marking;
	private String message;

	public boolean isMarking() {
		return marking;
	}

	public String getMessage() {
		return message;
	}

	@RequestMapping("/mines_mark")
	public String mines(Model model) {
		marking = !marking;
		fillModel(model);
		return "mines";
	}

	@RequestMapping("/updateRating_mines")
	public String updateRating(@RequestParam(value = "value", required = false) String value, Model model) {		
		updateNewRating(value, model);
		fillModel(model);
		return "mines";
	}

	@RequestMapping("/addComment_mines")
	public String addComment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {
		addNewComment(newComment, model);
		fillModel(model);
		return "mines";
	}

	@RequestMapping("/setFavourite_mines")
	public String setFavourite(Model model) {
		setNewFavourite(model);
		fillModel(model);
		return "mines";
	}

	@RequestMapping("/mines")
	public String mines(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {
		setGame("Mines");
		processCommand(row, column);
		fillModel(model);
		return "mines";
	}

	private void processCommand(String row, String column) {
		try {
			if (marking)
				field.markTile(Integer.parseInt(row), Integer.parseInt(column));
			else
				field.openTile(Integer.parseInt(row), Integer.parseInt(column));
			if (field.getState() == GameState.FAILED) {
				message = "FAILED";
			} else if (field.getState() == GameState.SOLVED) {
				message = "SOLVED";
			}
		} catch (NumberFormatException e) {
			createField();
		}
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='mines_field'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr class='mines_tile'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				String image = "closed";

				switch (tile.getState()) {
				case CLOSED:
					image = "closed";
					break;
				case MARKED:
					image = "marked";
					break;

				case OPEN:
					if (tile instanceof Clue)
						image = "open" + ((Clue) tile).getValue();
					else
						image = "mine";
					break;
				}

				sb.append("<td>\n");
				if (tile.getState() != TileState.OPEN && field.getState() == GameState.PLAYING)
					sb.append(String.format("<a href='/mines?row=%d&column=%d'>\n", row, column));
				sb.append("<img src='/img/mines/" + image + ".png'>\n");
				if (tile.getState() != TileState.OPEN && field.getState() == GameState.PLAYING)
					sb.append("</a>\n");
				sb.append("</td>\n");
			}
			sb.append("</tr>\n");

		}
		sb.append("</table>\n");

		return sb.toString();
	}

	private void createField() {
		field = new Field(9, 9, 10);
		message = "";
	}
}
