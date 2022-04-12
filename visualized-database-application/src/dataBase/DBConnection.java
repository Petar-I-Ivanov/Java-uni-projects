package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	static Connection connect = null;

	public static Connection getConnection() {

		try {
			
			Class.forName("org.h2.Driver");
			connect = DriverManager.getConnection(
					"jdbc:h2:tcp://localhost/path",
					"username",
					"password"
					);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connect;
	}
}