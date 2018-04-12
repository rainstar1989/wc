package wc.dao;

import java.sql.*;
import java.util.*;

import wc.bean.Team;

public class WCDao extends ConnectionFactory{
	
	public List<Team> queryTeam(Connection conn) {
		List<Team> list =new ArrayList<Team>();
		String sql = "select * from worldcup2018.teams";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Team team=new Team();
				team.setTeamid(rs.getString(1));
				team.setTeamname(rs.getString(2));
				team.setGroup(rs.getString(3));
				team.setGppoint(rs.getInt(4));
				list.add(team);
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
		WCDao td=new WCDao();
		
		if(co!=null)
			System.out.println("yes");
		else System.out.println("no");
		
		List<Team> li=td.queryTeam(co);
		for(int i=0;i<li.size();i++) {
			System.out.println(li.get(i).getTeamname());
		}
	}
}
