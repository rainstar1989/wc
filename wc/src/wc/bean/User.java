package wc.bean;

public class User {
	private String userid;
	private String password;
	private String remark;
	private String auth;
	private int userpoint;
	private int bingonumber;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int getUserpoint() {
		return userpoint;
	}
	public void setUserpoint(int userpoint) {
		this.userpoint = userpoint;
	}
	public int getBingonumber() {
		return bingonumber;
	}
	public void setBingonumber(int bingonumber) {
		this.bingonumber = bingonumber;
	}
	
	
}
