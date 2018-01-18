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

	public boolean isMarking() {
		return marking;
	}

	@RequestMapping("/mines_mark")
	public String mines(Model model) {
		marking = !marking;
		fillModel(model);
		return "game";
	}

	@RequestMapping("/mines")
	public String mines(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {
		processCommand(row, column);
		fillModel(model);
		return "game";
	}
	
	@RequestMapping("/setLevelmines")
	public String setLevelMines(@RequestParam(value = "level", required = false) String level, Model model) {	
		super.level = Integer.parseInt(level);
		field = new Field(super.level);
		fillModel(model);		
		return "game";
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

		sb.append("<br/>");
		sb.append("Action set to: ");
		sb.append("<a href='/mines_mark'>");
		if (marking)
			sb.append("Marking");
		else
			sb.append("Opening");

		sb.append("</a>");

		return sb.toString();
	}

	private void createField() {
		level = 1;
		field = new Field(level);
		message = "";
	}

	@Override
	protected String getGameName() {
		return "mines";
	}
	
	
}
