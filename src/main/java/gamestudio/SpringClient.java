package gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import gamestudio.consoleUI.ConsoleGameUI;
import gamestudio.consoleUI.ConsoleMenu;
import gamestudio.game.minesweeper.consoleUI.ConsoleUI;
import gamestudio.game.minesweeper.core.Field;
import gamestudio.server.webservice.CommentRestService;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.CommentServiceJPA;
import gamestudio.service.impl.CommentServiceRestClient;
import gamestudio.service.impl.CommentServiceSORM;
import gamestudio.service.impl.FavouriteServiceJPA;
import gamestudio.service.impl.RatingServiceJPA;
import gamestudio.service.impl.RatingServiceRestClient;
import gamestudio.service.impl.RatingServiceSORM;
import gamestudio.service.impl.ScoreServiceJPA;
import gamestudio.service.impl.ScoreServiceRestClient;
import gamestudio.service.impl.ScoreServiceSORM;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = { "gamestudio" },
excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "gamestudio.server.*"))
public class SpringClient {

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(SpringClient.class, args);
		new SpringApplicationBuilder(SpringClient.class).web(false).run(args);
	}

	@Bean
	public CommandLineRunner runner(ConsoleMenu menu) {
		return args -> menu.show();
	}

	@Bean
	public ConsoleMenu menu(ConsoleGameUI[] games) {
		return new ConsoleMenu(games);
	}

	@Bean
	public ConsoleGameUI consoleUIMines() {
		return new ConsoleUI();
	}

	@Bean
	public ConsoleGameUI puzzleConsoleUI() {
		return new gamestudio.game.puzzle.consoleui.ConsoleUI();
	}
	
	@Bean
	public ConsoleGameUI guessConsoleUI() {
		return new gamestudio.game.guess.ConsoleUI();
	}
	
	
	@Bean
	public FavouriteService favouriteService() {
		return new FavouriteServiceJPA();
	}
	
	@Bean
	public RatingService ratingService() {
		return new RatingServiceRestClient();
	}
	
	@Bean
	public ScoreService scoreService() {
		return new ScoreServiceRestClient();
	}
	
	@Bean
	public CommentService commentService() {
		return new CommentServiceRestClient();
	}
}
