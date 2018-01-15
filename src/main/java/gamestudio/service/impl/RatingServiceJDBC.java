package gamestudio.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import gamestudio.entity.Rating;
import gamestudio.service.RatingService;

public class RatingServiceJDBC implements RatingService {

	private static final String INSERT_COMMAND = "INSERT INTO rating (ident, username, game, value) VALUES (nextval('ident_seq'), ?, ?, ?)";
	private static final String SELECT_COMMAND = "SELECT ident, username, game, value FROM rating WHERE game = ? AND username = ?";
	private static final String SELECT_COMMAND2 = "SELECT AVG(value) FROM rating WHERE game = ?";
	private static final String UPDATE_COMMAND = "UPDATE rating SET value = ? WHERE username = ? AND game = ?";

	@Override
	public void setRating(Rating rating) {
		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD);
				PreparedStatement update = connection.prepareStatement(UPDATE_COMMAND);
				PreparedStatement select = connection.prepareStatement(SELECT_COMMAND)) {
			select.setString(1, rating.getGame());
			select.setString(2, rating.getUsername());

			try (ResultSet rs = select.executeQuery()) {
				if (rs.next()) {
					update.setInt(1, rating.getValue());
					update.setString(2, rating.getUsername());
					update.setString(3, rating.getGame());
					update.executeUpdate();
				} else {
					setNewRating(rating);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setNewRating(Rating rating) {
		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD); PreparedStatement ps = connection.prepareStatement(INSERT_COMMAND)) {
			ps.setString(1, rating.getUsername());
			ps.setString(2, rating.getGame());
			ps.setInt(3, rating.getValue());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getAverageRating(String game) {
		
		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD); PreparedStatement ps = connection.prepareStatement(SELECT_COMMAND2)) {

			ps.setString(1, game);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getUserRating(String game, String username) {
		// TODO Auto-generated method stub
		return 0;
	}

}
