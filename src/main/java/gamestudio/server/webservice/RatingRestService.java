package gamestudio.server.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import gamestudio.entity.Rating;
import gamestudio.service.RatingService;

@Path("/rating")
public class RatingRestService {
	
	@Autowired
	private RatingService ratingService;
	
	@POST
	@Consumes("application/json")
	public Response addRating(Rating rating) {
		ratingService.setRating(rating);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{game}")
	@Produces("application/json")
	public double getAverageRatin(@PathParam("game") String game) {
		return ratingService.getAverageRating(game);
	}
	
	@GET
	@Path("/{game}/{username}")
	@Produces("application/json")
	public double getUserRating(@PathParam("game") String game, @PathParam("username") String username) {
		return ratingService.getUserRating(game, username);
	}
	
	
}
