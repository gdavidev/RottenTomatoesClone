package models;

public class User {
	public int id;
	public String userName; 	
	public String email;
	public String password;
	public int admin;
	
	public User(int id, String userName, String email, String password, int admin) {
		this.id = id;
		this.userName = userName; 	
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	
	public User() {
		this.id = 0;
		this.userName = ""; 	
		this.email = "";
		this.password = "";
		this.admin = 0;
	}
}
