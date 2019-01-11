package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	public static double avrg(double map){
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";

		double sum=0;
		int count=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);


			Statement statement = connection.createStatement();

			//select data
			String allCustomersQuery = "SELECT * FROM logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next())
			{
				if(resultSet.getDouble("SomeDouble")==map) {
					count++;
					sum+=resultSet.getDouble("Point");
				}
			}

			resultSet.close();		
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sum/count;
	}

}
