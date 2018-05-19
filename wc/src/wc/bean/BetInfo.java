package wc.bean;

public class BetInfo {
	private String userid;
	private String betinfo;
	private String betresult;
	private int matchpoint;
	private int matchid;
	private String hteam;
	private String gteam;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBetinfo() {
		return betinfo;
	}
	public void setBetinfo(String betinfo) {
		this.betinfo = betinfo;
	}
	
	public int getMatchpoint() {
		return matchpoint;
	}
	public void setMatchpoint(int matchpoint) {
		this.matchpoint = matchpoint;
	}
	public int getMatchid() {
		return matchid;
	}
	public void setMatchid(int matchid) {
		this.matchid = matchid;
	}
	public String getBetresult() {
		return betresult;
	}
	public void setBetresult(String betresult) {
		this.betresult = betresult;
	}
	public String getHteam() {
		return hteam;
	}
	public void setHteam(String hteam) {
		this.hteam = hteam;
	}
	public String getGteam() {
		return gteam;
	}
	public void setGteam(String gteam) {
		this.gteam = gteam;
	}
	
}
