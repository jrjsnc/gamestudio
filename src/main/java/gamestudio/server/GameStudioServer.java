package gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.GameService;
import gamestudio.service.PlayerService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.CommentServiceJPA;
import gamestudio.service.impl.FavouriteServiceJPA;
import gamestudio.service.impl.GameServiceJPA;
import gamestudio.service.impl.PlayerServiceJPA;
import gamestudio.service.impl.RatingServiceJPA;
import gamestudio.service.impl.ScoreServiceJPA;

@SpringBootApplication
//@EnableWs
@EntityScan({"gamestudio.entity"})

public class GameStudioServer {
  public static void main(String[] args) {
      SpringApplication.run(GameStudioServer.class, args);
  }

  @Bean
  public ScoreService scoreService() {
      return new ScoreServiceJPA();
  }
  
  @Bean
  public RatingService ratingService() {
	  return new RatingServiceJPA();
  }
  
  @Bean
  public CommentService commentService() {
	  return new CommentServiceJPA();
  }
  
  @Bean
  public FavouriteService favouriteService() {
	  return new FavouriteServiceJPA();
  }
  
  @Bean
  public PlayerService playerService() {
	  return new PlayerServiceJPA();
  }
  
  @Bean
  public GameService gameService() {
	  return new GameServiceJPA();
  }
}
