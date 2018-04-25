package wc.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import wc.bean.Match;
import wc.bean.Next24hmatch;
import wc.bean.Team;

public class WCDao extends ConnectionFactory{
	
	public List<Team> queryTeam(Connection conn) {
		List<Team> list =new ArrayList<Team>();
		String sql = "select * from worldcup2018.teams te order by te.group asc";
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
	
	//查询未来24小时的比赛
	public List<Next24hmatch> queryNext24hmatch(Connection conn) {
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowtime=df.format(today);//当前时间
		String nowtime="2018-06-17 00:00:01";//测试时间
		System.out.println(nowtime);
		List<Next24hmatch> list =new ArrayList<Next24hmatch>();
		String sql="select c.evid,c.evtime,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and left(timediff(c.evtime,'"+nowtime+"'),length(timediff(c.evtime,'"+nowtime+"'))-6) between 0 and 24";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Next24hmatch next24hmatch=new Next24hmatch();
				next24hmatch.setMatchid(rs.getInt(1));
				next24hmatch.setMatchtime(rs.getTimestamp(2));
				next24hmatch.setHteamname(rs.getString(3));
				next24hmatch.setGteamname(rs.getString(4));
				list.add(next24hmatch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return list;
	}
	
	//列出用户需要竞猜的48小时以内的比赛（赛前1小时截止）
	public List<Match> queryMatchtobet(Connection conn,String uid){
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowtime=df.format(today);//当前时间
		String nowtime="2018-06-16 23:00:01";//测试时间
		List<Match> list=new ArrayList<>();
		String sql="select c.evid,c.evtime,c.hteam,c.gteam,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and left(timediff(c.evtime,'"+nowtime+"'),length(timediff(c.evtime,'"+nowtime+"'))-6) between 1 and 48 and c.evid not in (SELECT ub.evid FROM worldcup2018.userbetinfo ub where ub.uid='"+uid+"' order by c.evtime asc)";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Match match=new Match();
				match.setMatchid(rs.getInt(1));
				//日期时间是否需要分两个字段存？
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
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time;
		
		List<Next24hmatch> li=td.queryNext24hmatch(co);
		for(int i=0;i<li.size();i++) {
			time=df.format(li.get(i).getMatchtime());
			System.out.println("比赛时间："+time+"对阵："+li.get(i).getHteamname()+"vs"+li.get(i).getGteamname());
		}
	}
}
