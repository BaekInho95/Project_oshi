package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ConUtil5 {
	
	
	
private static DataSource ds;
	
	private static Connection conn;
	
	
	
	
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//String url = "jdbc:oracle:thin:@dbjangsu_high?TNS_ADMIN=A:/GlobalInWorkspace/Oshi/WebContent/Wallet_DBJangSu";
		String url = "jdbc:oracle:thin:@dbjangsu_high?TNS_ADMIN=/oshi5/tomcat/webapps/ROOT/Wallet_DBJangSu";
		String user = "ADMIN";
		String password= "wkdtnchs123Q!";
		
		conn = DriverManager.getConnection(url, user, password);
		
	
	
	return  conn;
}
}
