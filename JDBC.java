import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
	public static String DATABASE = "dblp";
    public static String USER = "dblpuser";
    public static String PASSWORD = "95533";
    
    public static Connection getconn(){
    	try { 
            Class.forName("org.postgresql.Driver"); 
            String url = "jdbc:postgresql://localhost/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD; 
            return DriverManager.getConnection(url);
            } 
            catch (ClassNotFoundException e) { 
              e.printStackTrace(); 
              return null;
            }
            catch (SQLException e) { 
                e.printStackTrace(); 
                return null;
            }
    }
}
