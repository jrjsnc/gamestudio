package gamestudio.server.webservice;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import gamestudio.entity.Comment;
import gamestudio.service.CommentService;

@Path("/comment")
public class CommentRestService {

	@Autowired
	private CommentService commentService;

	// http://localhost:8080/rest/comment/
	@POST
	@Consumes("application/json")
	public Response addComment(Comment comment) {
		commentService.addComment(comment);
		return Response.ok().build();
	}

	// http://localhost:8080/rest/comment/mines
	@GET
	@Path("/{game}")
	@Produces("application/json")
	public List<Comment> getComments(@PathParam("game") String game) {
		return commentService.getComments(game);
	}

}
