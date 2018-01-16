package gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.game.pexeso.Field;
import gamestudio.game.pexeso.GameState;
import gamestudio.game.pexeso.Tile;
import gamestudio.game.pexeso.TileState;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PexesoController extends GeneralController {
	private Field field;
	private String message;

	public String getMessage() {
		return message;
	}

	@RequestMapping("/pexeso")
	public String pexeso(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {
		//setGame("pexeso");
		processCommand(row, column);
		fillModel(model);
		return "pexeso";
	}

	private void processCommand(String row, String column) {
		try {
			field.move(Integer.parseInt(row), Integer.parseInt(column));
			if (field.getState() == GameState.SOLVED) {
				message = "SOLVED";
			}
		} catch (NumberFormatException e) {
			createField();
		}
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='pexeso_field'>\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr class='pexeso_tile'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				String image = "closed";

				if (tile.getState() == TileState.CLOSED) {
					image = "closed";
				} else {
					image = "opened" + (tile.getValue());
				}

				sb.append("<td>\n");		
				if(tile.getState() != TileState.OPENED && field.getState() == GameState.PLAYING)
					sb.append(String.format("<a href='/pexeso?row=%d&column=%d'>\n", row, column));
				sb.append("<img src='/img/pexeso/" + image + ".png'>\n");
				if (tile.getState() != TileState.OPENED && field.getState() == GameState.PLAYING)
					sb.append("</a>\n");
				sb.append("</td>\n");
				
			}
			sb.append("</tr>\n");
		}
		sb.append("</table>\n");

		return sb.toString();
	}

	private void createField() {
		field = new Field(4);
		message = "";
	}

	@Override
	protected String getGameName() {
		return "pexeso";
	}
}
