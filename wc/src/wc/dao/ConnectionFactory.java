package wc.dao;
import java.sql.*;

public class ConnectionFactory {
	
	private String driverClassName = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/worldcup2018?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
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
