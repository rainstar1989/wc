package wc.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import net.sf.json.JSONArray;

import java.text.SimpleDateFormat;

import wc.bean.BetInfo;
import wc.bean.Match;
import wc.bean.Team;

public class WCDao extends ConnectionFactory{
	
	public List<Match> queryMatchUnfinished(Connection conn) {//列出未填写比赛结果的比赛
		List<Match> list =new ArrayList<Match>();
		String sql = "select c.evid,c.evtime,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and c.evresult is null order by c.evtime asc";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Match match=new Match();
				match.setMatchid(rs.getInt(1));
				match.setMatchdate(rs.getDate(2).toString());
				match.setHteam(rs.getString(3));
				match.setGteam(rs.getString(4));
				list.add(match);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return list;
	}
	
	public int checkMatchResult(int mid,Connection conn) {//检查填写比赛结果是否已经填写过
		int flag=-1;
		String sql="select * from worldcup2018.events where evid="+mid+" and evresult is not null";
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
	
	public int setMatchResult(int mid,String mrt,Connection conn) {//写入比赛结果
		int flag=0;
		String sql="update worldcup2018.events set evresult='"+mrt+"' WHERE evid="+mid;
		try {
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
	
	public List<BetInfo> queryBetInfo(int mid,Connection conn){//查询某场比赛所有用户的竞猜结果
		List<BetInfo> list=new ArrayList();
		String sql="select uid,betinfo from worldcup2018.userbetinfo where evid="+mid;
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				BetInfo betinfo =new BetInfo();
				betinfo.setUserid(rs.getString(1));
				betinfo.setBetinfo(rs.getString(2));
				list.add(betinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return list;
	}
	
	public int checkBetResult(String uid,int mid,Connection conn) {//检查竞猜的比赛是否已有竞猜结果
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.userbetinfo ubi where uid='"+uid+"' and evid="+mid+" and betresult is not null";
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
	
	public int setBetResult(String uid,int mid,boolean betresult,Connection conn) {//写入竞猜结果
		int flag=0;
		String sql="update worldcup2018.userbetinfo set betresult ="+betresult+" where uid='"+uid+"' and evid="+mid;
		try {
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
	
	public List<Match> queryMatchtobet(Connection conn,String uid){//列出用户需要竞猜的48小时以内的比赛（赛前1小时截止）
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowtime=df.format(today);//当前时间
		String nowtime="2018-06-16 23:00:00";//测试时间
		List<Match> list=new ArrayList();
		String sql="select c.evid,c.evtime,c.hteam,c.gteam,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and left(timediff(c.evtime,'"+nowtime+"'),length(timediff(c.evtime,'"+nowtime+"'))-6) between 1 and 48 and c.evid not in (SELECT ub.evid FROM worldcup2018.userbetinfo ub where ub.uid='"+uid+"' order by c.evtime asc)";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Match match=new Match();
				match.setMatchid(rs.getInt(1));
				match.setMatchdate(rs.getDate(2).toString());
				match.setMatchtime(rs.getTime(2).toString().substring(0, 5));
				match.setHtm(rs.getString(3));
				match.setGtm(rs.getString(4));
				match.setHteam(rs.getString(5));
				match.setGteam(rs.getString(6));
				list.add(match);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return list;
	}
	
	public int checkBet(String uid,int mid,Connection conn) {//检查竞猜的比赛是否已经竞猜过
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.userbetinfo ubi where uid='"+uid+"' and evid="+mid;
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
	
	public int checkMatchTime(int mid,Connection conn) {//检查竞猜的比赛是否已经过期
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowtime=df.format(today);//当前时间
		String nowtime="2018-06-17 01:00:00";//测试时间
		
		int flag=-1;
		String sql="select * from worldcup2018.events ev where ev.evid="+mid+" and evtime<'"+nowtime+"'";
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
	
	public int bet(String uid,int mid,String betinfo,Connection conn) {//写入竞猜结果
		int flag=0;
		String sql="insert into worldcup2018.userbetinfo (uid,evid,betinfo) values (?,?,?)";
		try {
			ptmt=conn.prepareStatement(sql);
			ptmt.setString(1, uid);
			ptmt.setInt(2, mid);
			ptmt.setString(3, betinfo);
			flag=ptmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return flag;
	}
	
	public List<Match> queryPlayoffsMatch(Connection conn){
		List<Match> list =new ArrayList<Match>();
		String sql = "select evid,hteam,gteam from worldcup2018.events where evtype!='groupmatch' order by evid asc";
		try {
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Match match=new Match();
				match.setMatchid(rs.getInt(1));
				match.setHtm(rs.getString(2));
				match.setGtm(rs.getString(3));
				list.add(match);
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
		
		int ff=td.setBetResult("test", 10, false, co);
		int f=td.checkBetResult("test", 10, co);
		System.out.println(ff);
		System.out.println(f);
	}
}
