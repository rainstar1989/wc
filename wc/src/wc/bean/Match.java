package wc.bean;

import java.util.Date;

public class Match {
	
	private int matchid;
	private String matchtype;
	private Date matchtime;
	private String hteam;
	private String gteam;
	private int hgoal;
	private int ggoal;
	
	public int getMatchid() {
		return matchid;
	}
	public void setMatchid(int matchid) {
		this.matchid=matchid;
	}
	public String getMatchtype() {
		return matchtype;
	}
	public void setMatchtype(String matchtype) {
		this.matchtype=matchtype;
	}
	public Date getMatchtime() {
		return matchtime;
	}
	public void setMatchtime(Date matchtime) {
		this.matchtime=matchtime;
	}
	public String getHteam() {
		return hteam;
	}
	public void setHteam(String hteam) {
		this.hteam=hteam;
	}
	public String getGteam() {
		return gteam;
	}
	public void setGteam(String gteam) {
		this.gteam=gteam;
	}
	public int getHgoal() {
		return hgoal;
	}
	public void setHgoal(int hgoal) {
		this.hgoal=hgoal;
	}
	public int getGgoal() {
		return ggoal;
	}
	public void setGgoal(int ggoal) {
		this.ggoal=ggoal;
	}

}
