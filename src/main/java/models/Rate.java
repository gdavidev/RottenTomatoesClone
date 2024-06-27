package models;

public class Rate {
	public int movieId;
	public int userId;
	public int rating;
	
	public Rate(int movieId, int userId, int rating) {
		this.movieId = movieId;
		this.userId = userId; 	
		this.rating = rating;
	}
	
	public Rate() {
		this.movieId = 0;
		this.userId = 0; 	
		this.rating = 0;
	}
}
