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
				String pwd=SHA.getResult(user.getPassword());
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
	
	public int checkId(User user,Connection conn) {
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.users us where uid='"+user.getUserid()+"'";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				flag=0;
			}else {
				flag=1;
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
	
	public int reg(User user,Connection conn) {
		int flag=0;
		String sql="insert into worldcup2018.users (`uid`, `password`, `point`, `remark`) values(?,?,?,?)";
		try {
			ptmt=conn.prepareStatement(sql);
			ptmt.setString(1, user.getUserid());
			ptmt.setString(2, SHA.getResult(user.getPassword()));
			ptmt.setInt(3, 0);
			ptmt.setString(4, user.getRemark());
			flag=ptmt.executeUpdate();
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
		u.setUserid("test4");
		u.setPassword("333");
		u.setRemark("peter");
		int r=ud.reg(u, co);
		System.out.println(r);
	}
}