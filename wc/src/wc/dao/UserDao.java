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
	
	public int login(User user) {
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.users us where uid='"+user.getUserid()+"'";
		try {
			conn=getConnection();
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
	
	public int checkId(User user) {
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.users us where uid='"+user.getUserid()+"'";
		try {
			conn=getConnection();
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
	
	public int reg(User user) {
		int flag=0;
		String sql="insert into worldcup2018.users (`uid`, `password`, `remark`) values(?,?,?,?)";
		try {
			conn=getConnection();
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
	
	public User userInfo(String uid) {
		User user=new User();
		String sql="select us.remark,us.auth from worldcup2018.users us where uid='"+uid+"'";
		try {
			conn=getConnection();
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
	
	public List<User> userList(){
		List<User> list=new ArrayList();
		String sql="select us.uid,us.remark,us.userpoint,us.bingonumber from worldcup2018.users us order by us.userpoint desc,us.bingonumber desc";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				User u=new User();
				u.setUserid(rs.getString(1));
				u.setRemark(rs.getString(2));
				u.setUserpoint(rs.getInt(3));
				u.setBingonumber(rs.getInt(4));
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
	
	public int setPoint(String uid,int userpoint,int bingonumber) {
		int flag=0;
		String sql="update worldcup2018.users set userpoint="+userpoint+",bingonumber="+bingonumber+" where uid='"+uid+"'";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			
			flag=ptmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return flag;
	}
	
	public static void main(String[] args){
		UserDao ud=new UserDao();
		
		String uid="rainstar1989";
		User u=ud.userInfo(uid);
		JSONObject json = JSONObject.fromObject(u);
		String str = json.toString();
		System.out.println("姓名："+u.getRemark()+"权限："+u.getAuth());
		System.out.println(str);
	}
}
