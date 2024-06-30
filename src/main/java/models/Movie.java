package models;

public class Movie {
	public int id;
	public String title;
	public String director;
	public String genre;
	public int ratingCount;
	public float ratingAverage;
	public String tumbnailPath;
	
	public Movie(int id, String title, String director, String genre, int ratingCount, float ratingAverage) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.genre = genre;
		this.ratingCount = ratingCount;
		this.ratingAverage = ratingAverage;
	}
	
	public Movie(int id, String title, String director, String genre) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.genre = genre;
		this.ratingCount = 0;
		this.ratingAverage = 0f;
	}
	
	public Movie() {
		this.id = 0;
		this.title = "";
		this.director = "";
		this.genre = "";
		this.ratingCount = 0;
		this.ratingAverage = 0f;
	}
}
