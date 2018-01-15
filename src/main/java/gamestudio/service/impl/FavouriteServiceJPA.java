package gamestudio.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import gamestudio.entity.Favourite;
import gamestudio.service.FavouriteService;

@Transactional
public class FavouriteServiceJPA implements FavouriteService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addFavourite(Favourite favourite) {
		try {
			Favourite f = (Favourite) entityManager
					.createQuery("SELECT f FROM Favourite f WHERE f.username = :username AND f.game = :game")
					.setParameter("username", favourite.getUsername()).setParameter("game", favourite.getGame())
					.getSingleResult();

			removeFavourite(favourite.getUsername(), favourite.getGame());

		} catch (NoResultException e) {
			entityManager.persist(favourite);
		}

	}

	@Override
	public void removeFavourite(String username, String game) {
		entityManager.createQuery("DELETE FROM Favourite f WHERE f.username = :username AND f.game = :game")
				.setParameter("username", username).setParameter("game", game).executeUpdate();

	}

	@Override
	public List<Favourite> getFavourites(String username) {
		return entityManager.createQuery("SELECT f FROM Favourite f WHERE f.username = :username")
				.setParameter("username", username).getResultList();
	}
	
	@Override
	public boolean isFavourite(String username, String game) {		
		try {
			Favourite f = (Favourite) entityManager
					.createQuery("SELECT f FROM Favourite f WHERE f.username = :username AND f.game = :game")
					.setParameter("username", username).setParameter("game", game).getSingleResult();
		} catch (NoResultException e){
			return false;	
			}
		
		return true;
	}

}
