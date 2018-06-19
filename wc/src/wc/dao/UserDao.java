package wc.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import net.sf.json.JSONObject;

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
				if(rs.getString(2)==null) {
					int r=resetPassword(user.getUserid(),pwd);
					if(r==1){
						flag=1;
					}else {
						flag=-1;
					}
				}else {
					if(pwd.equals(rs.getString(2))) {
						flag=1;
					}else {
						flag=-1;
					}
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
		String sql="insert into worldcup2018.users (`uid`, `password`, `remark`) values(?,?,?)";
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
		finally {
			closeAll();
		}
		return user;
	}
	
	public List<User> userList(){
		List<User> list=new ArrayList<User>();
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
	
	public int resetPassword(String userid,String password) {
		int flag=0;
		String sql="update worldcup2018.users set password='"+password+"' where uid='"+userid+"'";
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
	
	public List<User> betedCount(){
		List<User> list=new ArrayList<User>();
		String sql="select ub.uid,us.remark,count(ub.evid) from worldcup2018.userbetinfo ub,worldcup2018.users us where ub.uid=us.uid group by ub.uid";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				User u=new User();
				u.setUserid(rs.getString(1));
				u.setRemark(rs.getString(2));
				u.setBetedcount(rs.getInt(3));
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
	
	public List<User> bingoUser(int mid){
		List<User> list=new ArrayList<User>();
		String sql="select u.remark from worldcup2018.userbetinfo ui,worldcup2018.users u where betresult=1 and ui.uid=u.uid and evid="+mid;
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				User u=new User();
				u.setRemark(rs.getString(1));
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
		UserDao ud=new UserDao();
		
		String uid="rainstar1989";
		User u=ud.userInfo(uid);
		JSONObject json = JSONObject.fromObject(u);
		String str = json.toString();
		System.out.println("姓名："+u.getRemark()+"权限："+u.getAuth());
		System.out.println(str);
	}
}
