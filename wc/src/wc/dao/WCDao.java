package wc.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;


import java.text.SimpleDateFormat;

import wc.bean.BetInfo;
import wc.bean.Match;

public class WCDao extends ConnectionFactory{
	
	public List<Match> queryMatchUnfinished() {//列出未填写比赛结果的比赛
		List<Match> list =new ArrayList<Match>();
		String sql = "select c.evid,c.evtime,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and c.evresult is null order by c.evtime asc";
		try {
			conn=getConnection();
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
	
	public List<Match> queryMatchFinished() {//列出已填写比赛结果的比赛
		List<Match> list =new ArrayList<Match>();
		String sql = "select c.evid,a.tmname as '主队名称', b.tmname as '客队名称', c.evresult from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and c.evresult is not null order by c.evtime desc";
		
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Match match=new Match();
				match.setMatchid(rs.getInt(1));
				match.setHteam(rs.getString(2));
				match.setGteam(rs.getString(3));
				if (rs.getString(4).equals("w")) {
					match.setMatchresult("胜");
				}else if(rs.getString(4).equals("t")) {
					match.setMatchresult("平");
				}else if(rs.getString(4).equals("l")) {
					match.setMatchresult("负");
				}
				
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
	
	public int checkMatchResult(int mid) {//检查填写比赛结果是否已经填写过
		int flag=-1;
		String sql="select * from worldcup2018.events where evid="+mid+" and evresult is not null";
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
	
	public int setMatchResult(int mid,String mrt) {//写入比赛结果
		int flag=0;
		String sql="update worldcup2018.events set evresult='"+mrt+"' WHERE evid="+mid;
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
	
	public List<BetInfo> queryBetInfo(int mid){//查询某场比赛所有用户的竞猜结果
		List<BetInfo> list=new ArrayList<BetInfo>();
		String sql="select uid,betinfo from worldcup2018.userbetinfo where evid="+mid;
		try {
			conn=getConnection();
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
	
	public int checkBetResult(String uid,int mid) {//检查竞猜的比赛是否已有竞猜结果
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.userbetinfo ubi where uid='"+uid+"' and evid="+mid+" and betresult is not null";
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
	
	public int setBetResult(String uid,int mid,boolean betresult,int point) {//写入竞猜结果
		int flag=0;
		String sql="update worldcup2018.userbetinfo set betresult ="+betresult+",point="+point+" where uid='"+uid+"' and evid="+mid;
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
	
	public List<Match> queryMatchtobet(String uid){//列出用户需要竞猜的48小时以内的比赛（赛前1小时截止）
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
//		String nowtime="2018-06-12 22:01:00";//测试时间
		List<Match> list=new ArrayList<Match>();
		String sql="select c.evid,c.evtime,c.hteam,c.gteam,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and left(timediff(c.evtime,'"+nowtime+"'),length(timediff(c.evtime,'"+nowtime+"'))-6) between 1 and 48 and c.evid not in (SELECT ub.evid FROM worldcup2018.userbetinfo ub where ub.uid='"+uid+"') order by c.evtime asc";
		try {
			conn=getConnection();
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
	
	public int checkBet(String uid,int mid) {//检查竞猜的比赛是否已经竞猜过
		int flag=-1;
		String sql="SELECT * FROM worldcup2018.userbetinfo ubi where uid='"+uid+"' and evid="+mid;
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
	
	public int checkMatchTime(int mid) {//检查竞猜的比赛是否已经过期
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
//		String nowtime="2018-06-12 22:01:00";//测试时间
		
		int flag=-1;
		String sql="select * from worldcup2018.events ev where ev.evid="+mid+" and evtime<'"+nowtime+"'";
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
	
	public int bet(String uid,int mid,String betinfo) {//写入竞猜结果
		int flag=0;
		String sql="insert into worldcup2018.userbetinfo (uid,evid,betinfo) values (?,?,?)";
		try {
			conn=getConnection();
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
	
	public List<Match> queryPlayoffsMatch(){//列出淘汰赛
		List<Match> list =new ArrayList<Match>();
		String sql = "select evid,hteam,gteam from worldcup2018.events where evtype!='groupmatch' order by evtime asc";
		try {
			conn=getConnection();
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
	
	public int setPlayoffsMatch(int mid,String htm,String gtm) {//修改淘汰赛队名
		int flag=0;
		String sql="update worldcup2018.events set hteam='"+htm+"',gteam='"+gtm+"' where evid="+mid;
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
	
	public int calMatchPoint(int mid) {//计算单场积分
		int matchpoint=0;
		String sql="select evtype from worldcup2018.events where evid="+mid;
		String matchtype=null;
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				matchtype=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		if(matchtype.equals("groupmatch")) {
			matchpoint=1;
		}else if(matchtype.equals("roundof16")) {
			matchpoint=2;
		}else if(matchtype.equals("quarter-finals")) {
			matchpoint=3;
		}else if(matchtype.equals("semi-finals")) {
			matchpoint=4;
		}else if(matchtype.equals("matchfor3")) {
			matchpoint=5;
		}else if(matchtype.equals("final")) {
			matchpoint=6;
		}else {
			System.out.println("比赛类型有问题！");
		}
		return matchpoint;
	}
	
	public int queryUserPoint(String userid) {//计算用户积分
		int userpoint=0;
		String sql="select sum(ub.point) from worldcup2018.userbetinfo ub where uid='"+userid+"'";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				userpoint=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return userpoint;
	}
	
	public int queryBingoNumber(String userid) {//计算用户猜对场数
		int bingonumber=0;
		String sql="select count(ub.evid) from worldcup2018.userbetinfo ub where ub.betresult is true and ub.uid='"+userid+"'";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				bingonumber=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return bingonumber;
	}
	
	public List<BetInfo> queryBetedMatch(String userid){//列出已竞猜比赛列表
		List<BetInfo> list=new ArrayList<BetInfo>();
		String sql="select ub.evid,ub.betinfo,ub.betresult,ub.point,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.userbetinfo ub,worldcup2018.events ev,worldcup2018.teams a,worldcup2018.teams b where uid='"+userid+"' and ub.evid=ev.evid and a.tmid=ev.hteam and b.tmid=ev.gteam order by ev.evtime desc";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				BetInfo bi=new BetInfo();
				bi.setMatchid(rs.getInt(1));
				if (rs.getString(2).equals("w")) {
					bi.setBetinfo("胜");
				}else if(rs.getString(2).equals("t")) {
					bi.setBetinfo("平");
				}else if(rs.getString(2).equals("l")) {
					bi.setBetinfo("负");
				}
				if (rs.getObject(3)==null) {
					bi.setBetresult("未赛");
				}else {
					if(rs.getBoolean(3)) {
						bi.setBetresult("对");
					}else {
						bi.setBetresult("错");
					}
				}
				bi.setMatchpoint(rs.getInt(4));
				bi.setHteam(rs.getString(5));
				bi.setGteam(rs.getString(6));
				list.add(bi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return list;
	}
	
	public int countBetableMatch() {//计算可竞猜场数
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
//		String nowtime="2018-06-12 22:01:00";//测试时间
		
		int counts=0;
		String sql="select count(evid) from worldcup2018.events where left(timediff(evtime,'"+nowtime+"'),length(timediff(evtime,'"+nowtime+"'))-6) <= 48";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				counts=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return counts;
	}
	
	public String matchBetInfo(int mid) {
		String text="";
		String sql="select (SELECT count(betinfo) FROM worldcup2018.userbetinfo ub where ub.betinfo='w' and ub.evid="+mid+") as c1,(SELECT count(betinfo) FROM worldcup2018.userbetinfo ub where ub.betinfo='t' and ub.evid="+mid+") as c2,(SELECT count(betinfo) FROM worldcup2018.userbetinfo ub where ub.betinfo='l' and ub.evid="+mid+") as c3;";
		try {
			conn=getConnection();
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				int c1=rs.getInt(1);
				int c2=rs.getInt(2);
				int c3=rs.getInt(3);
				text="猜胜："+c1+"人，"+"猜平："+c2+"人，"+"猜负："+c3+"人。";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return text;
	}
	
	
	public static void main(String[] args){
		WCDao wd=new WCDao();
		System.out.println(wd.matchBetInfo(1));
	}
}
