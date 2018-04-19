package wc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import wc.bean.User;

public class UserDao extends ConnectionFactory {
	
	public int login(User user,Connection conn) {
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.users us where uid='"+user.getUserid()+"'";
		try {
			
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				String pwd=user.getPassword();
				if(pwd.equals(rs.getString(2))) {
					flag=1;
				}else {
					flag=-1;
				}
			}else {
				flag=-1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			flag=-2;
		}
		finally {
			closeAll();
		}
		return flag;
	}
	
	
	
	public static void main(String[] args){
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		UserDao ud=new UserDao();
		
		User u=new User();
		u.setUserid("test1");
		u.setPassword("123");
		int f=ud.login(u,co);
		System.out.println(f);
	}
}
