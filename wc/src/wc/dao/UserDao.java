package wc.dao;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wc.bean.BetInfo;
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
		String sql="insert into worldcup2018.users (`uid`, `password`, `remark`) values(?,?,?,?)";
		try {
			ptmt=conn.prepareStatement(sql);
			ptmt.setString(1, user.getUserid());
			ptmt.setString(2, SHA.getResult(user.getPassword()));
			ptmt.setString(3, user.getRemark());
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
	
	public User userInfo(String uid,Connection conn) {
		User user=new User();
		String sql="select us.remark,us.auth from worldcup2018.users us where uid='"+uid+"'";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while (rs.next()) {
				user.setRemark(rs.getString(1));
				user.setAuth(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> userList(Connection conn){
		List<User> list=new ArrayList();
		String sql="select us.uid,us.remark from worldcup2018.users us";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				User u=new User();
				u.setUserid(rs.getString(1));
				u.setRemark(rs.getString(2));
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return list;
	}
	
	public static void main(String[] args){
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		UserDao ud=new UserDao();
		
		String uid="rainstar1989";
		User u=ud.userInfo(uid, co);
		JSONObject json = JSONObject.fromObject(u);
		String str = json.toString();
		System.out.println("姓名："+u.getRemark()+"权限："+u.getAuth());
		System.out.println(str);
	}
}
