package gamestudio.game.pexeso;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
	private Field field = new Field(4);
	private Scanner scanner = new Scanner(System.in);
	private static final Pattern INPUT_PATTERN = Pattern.compile("([1-9])([1-9])");

	public int run() {
		long startTime = System.currentTimeMillis();
		print();
		while (field.getState() != GameState.SOLVED) {
			processInput();
			print();
		}		
		if(field.getState() == GameState.SOLVED) {
			Long playTime = (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("Congrats, you won! Winning time: " + playTime + "s.");
			return Math.toIntExact(playTime);
		}		
		return 0;
	}

	private void processInput() {
		System.out.println("Enter tile coordinates to open tile, X to exit: ");
		String input = scanner.nextLine().trim().toUpperCase();
		
		if("X".equals(input)) {
			System.exit(0);
		}
		
		Matcher matcher = INPUT_PATTERN.matcher(input);
		if(matcher.matches()) {
			int row = Integer.parseInt(matcher.group(1)) -1;
			int column = Integer.parseInt(matcher.group(2)) -1;
			
			field.openTile(row, column);
		} else {
			System.out.println("Wrong input!");
		}			
	}

	private void print() {
		
		System.out.print("   ");
		for (int column = 1; column <= field.getColumnCount(); column++) {
			System.out.print(column + " ");
		}
		
		System.out.println();
		System.out.println("   _ _ _ _");
		
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print(row + 1 + "| ");
			
			for (int column = 0; column < field.getRowCount(); column++) {
				if (field.getTile(row, column).getState() != TileState.CLOSED) {
					System.out.print(field.getTile(row, column).getValue() + " ");
				} else {
					System.out.print("X ");
				}
			}

			System.out.println();
		}
	}

	public String getName() {
		return "Pexeso";
	}

}
