package gamestudio.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import gamestudio.entity.Favourite;
import gamestudio.entity.Rating;
import gamestudio.service.RatingService;

@Transactional
public class RatingServiceJPA implements RatingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void setRating(Rating rating) {		
		Rating r;
		try {
			r = (Rating) entityManager
					.createQuery("SELECT r FROM Rating r WHERE r.username = :username AND r.game = :game")
					.setParameter("username", rating.getUsername()).setParameter("game", rating.getGame()).getSingleResult();
			
			entityManager.createQuery("DELETE FROM Rating r WHERE r.username = :username AND r.game = :game")
			.setParameter("username", rating.getUsername()).setParameter("game", rating.getGame()).executeUpdate();	
			
			entityManager.persist(rating);
			
		} catch (NoResultException e) {
			entityManager.persist(rating);
		}		
	}

	@Override
	public double getAverageRating(String game) {
		Object averageRating = entityManager.createQuery("SELECT AVG(r.value) FROM Rating r WHERE r.game = :game")
				.setParameter("game", game).getSingleResult();
		return averageRating == null ? 0 : (double) averageRating;
	}

	@Override
	public int getUserRating(String game, String username) {
		Rating rating;
		try {
			rating = (Rating)entityManager
					.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.username = :username")
					.setParameter("game", game).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
		return rating.getValue();
	}

}
