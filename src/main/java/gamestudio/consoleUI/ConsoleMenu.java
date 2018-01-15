package gamestudio.consoleUI;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import gamestudio.entity.Comment;
import gamestudio.entity.Favourite;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

public class ConsoleMenu {
	private static final String USERNAME = System.getProperty("user.name");
	private ConsoleGameUI[] games;
	private Scanner scanner = new Scanner(System.in);

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private FavouriteService favouriteService;

	public ConsoleMenu(ConsoleGameUI[] games) {
		this.games = games;
	}

	public void show() {

		// ratingService.setRating(new Rating("Test", "Test", 10));
		//
		// System.out.println(ratingService.getAverageRating("Test"));

		// favouriteService.addFavourite(new Favourite("jednahra", "jedenUser"));

		System.out.println("____________________________________________________");

		System.out.println("List of games: ");
		int index = 1;
		for (ConsoleGameUI game : games) {
			double rating = ratingService.getAverageRating(game.getName());

			System.out.printf("%d. %s %.2f/10 \n", index, game.getName(), rating);

			index++;
		}
		System.out.println("____________________________________________________");
		do {
			System.out.println("Select a game, for exit enter X: ");
			String line = scanner.nextLine().trim();

			if ("x".equals(line.toLowerCase()))
				System.exit(0);

			try {
				index = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				for (ConsoleGameUI game : games) {
					if (line.trim().toLowerCase().equals(game.getName().toLowerCase())) {
						game.run();
						afterGamePrint(game);
					}
				}
				index = -1;
			}
		} while (index < 1 || index > games.length);
		ConsoleGameUI game = games[index - 1];
		game.run();
		afterGamePrint(game);
	}

	private void afterGamePrint(ConsoleGameUI game) {
		List<Score> topScores = scoreService.getTopScores(game.getName());
		int index = 1;
		System.out.println("____________________________________________________");
		System.out.println("Tabulka vitazov: ");
		System.out.println("___MENO___HRA___SKORE");
		for (Score score : topScores) {
			System.out.printf("%d. %s %s %d \n", index, score.getUsername(), score.getGame(), score.getValue());
			index++;
		}
		System.out.println("____________________________________________________");
		
		System.out.println("Tvoje hodnotenie hry je " + ratingService.getUserRating(game.getName(), USERNAME));
		System.out.println("Ak ho chces zmenit, zadaj hodnotu.");

		String line = scanner.nextLine();

		if (!line.equals("")) {

			try {
				int value = Integer.parseInt(line);
				ratingService.setRating(new Rating(USERNAME, game.getName(), value));
			} catch (NumberFormatException e) {
				System.err.println("Zadal si nespravnu hodnotu.");
			}
			System.out.println("____________________________________________________");

		}
		List<Comment> gameComments = commentService.getComments(game.getName());
		
		for (Comment comment : gameComments) {
			System.out.printf("%tc | %s | %s", comment.getCreatedOn(), comment.getUsername(), comment.getContent());
		}
		
		System.out.println("____________________________________________________");
		
		System.out.println("Pridaj komentar k hre: ");
		
		

		// String line = scanner.nextLine().trim();

	}
}
