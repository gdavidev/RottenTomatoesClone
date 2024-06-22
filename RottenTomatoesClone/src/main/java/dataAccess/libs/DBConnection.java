package dataAccess.libs;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private String host = "127.0.0.1";
	private String port = "3306";
	private String schema = "rottomato";
	private String user = "root";
	private String password = "";
	
	private Connection connection = null;
	
	public DBConnection(String host, String port, String schema, String user, String password) {
		this.setHost(host);
		this.setPort(port);
		this.setSchema(schema);
		this.setUser(user);
		this.setPassword(password);
		this.doConnection();
	}
	
	public DBConnection() {
		this.doConnection();
	}
	
	private void doConnection() {
		String timezone = "&useTimezone=true&serverTimezone=UTC";
		String url = "jdbc:mariadb://"+this.host+":"+port+"/"+this.schema+"?user="+this.user+"&password="+this.password+timezone;
		try {
			Class.forName("org.mariadb.jdbc.Driver").getConstructor().newInstance();
			this.connection = DriverManager.getConnection(url);;
		} catch (InstantiationException e){
			e.printStackTrace();
		} catch (IllegalAccessException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = ( host.isEmpty() ? "localhost" : host ) ;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = ( port.isEmpty() ? "3306" : port ) ;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Connection getConnection() {
		return (this.connection);
	}

}
