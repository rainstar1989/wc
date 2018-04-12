package wc.dao;
import java.sql.*;

public class ConnectionFactory {
	
	private String driverClassName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://rainstar1989.asuscomm.com:8765/testdb?characterEncoding=utf8&useSSL=true";
	private String dbUser = "root";  
    private String dbPassword = "05151989";
    Connection conn;
    PreparedStatement ptmt;
    ResultSet rs;
    
    public Connection getConnection() {
    	try {
    		Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	try {
			conn=DriverManager.getConnection(url, dbUser, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return conn;
    }
    
    public void closeAll(){
    	try {
    		if(rs!=null)
    			rs.close();
    		if(ptmt!=null)
    			ptmt.close();
    		if(conn!=null)
    			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
