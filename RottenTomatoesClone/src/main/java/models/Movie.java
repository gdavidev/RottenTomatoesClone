package models;

public class Movie {
	public int id;
	public String title;
	public String director;
	public String genre;
	
	public Movie(int id, String title, String director, String genre) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.genre = genre;
	}
	
	public Movie() {
		this.id = 0;
		this.title = "";
		this.director = "";
		this.genre = "";
	}
}
