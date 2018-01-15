package gamestudio.game.guess;

import java.util.Scanner;

import gamestudio.consoleUI.ConsoleGameUI;

public class ConsoleUI implements ConsoleGameUI {

	Logic logic = new Logic(11);

	private Scanner scanner = new Scanner(System.in);
	private int guess = -1;

	@Override
	public int run() {
		long startTime = System.currentTimeMillis();

		while (!logic.isSolved(guess)) {
			processInput();
		}
		Long playTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println("Gratulujem vyhral si! Tvoj vitazny cas je: " + playTime + "s.");

		return Math.toIntExact(playTime);
	}

	public void processInput() {
		System.out.println("Hadaj cislo od 0 do 10, pre koniec zadaj X");
		String input = scanner.nextLine().trim().toLowerCase();
		if ("x".equals(input))
			System.exit(0);
		try {
			guess = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("Nezadal si cislo");
		}
	}

	@Override
	public String getName() {
		return "Guess";
	}

}
