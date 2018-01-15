package gamestudio.game.puzzle.consoleui;

import java.util.Scanner;

import gamestudio.consoleUI.ConsoleGameUI;

import gamestudio.game.puzzle.core.Field;

public class ConsoleUI implements ConsoleGameUI {
	private Field field = new Field(4, 5);
	private Scanner scanner = new Scanner(System.in);

	@Override
	public int run() {
		long startTime = System.currentTimeMillis();
		print();
		while (!field.isSolved()) {
			processInput();
			print();
		}
		Long playTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println("Gratulujem vyhral si! Tvoj vitazny cas je: " + playTime + "s.");

		return Math.toIntExact(playTime);
	}

	private void processInput() {
		System.out.print("Enter tile number or X to exit: ");
		String input = scanner.nextLine().trim().toUpperCase();
		if ("X".equals(input))
			System.exit(0);
		try {
			int tile = Integer.parseInt(input);
			if (!field.moveTile(tile)) {
				System.out.println("Really crazy?");
			}
		} catch (NumberFormatException e) {
			System.out.println("Are you crazy?");
		}
	}

	private void print() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);
				if (tile == Field.EMPTY_TILE)
					System.out.printf("   ", tile);
				else
					System.out.printf(" %2d", tile);
			}
			System.out.println();
		}
	}

	@Override
	public String getName() {
		return "Puzzle";
	}
}
