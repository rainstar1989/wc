package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wc.bean.BetInfo;
import wc.bean.User;
import wc.dao.ConnectionFactory;
import wc.dao.UserDao;
import wc.dao.WCDao;

/**
 * Servlet implementation class SetResultServlet
 */
@WebServlet("/SetResultServlet")
public class SetResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId=(String)session.getAttribute("loginId");
		System.out.println("SetResultServlet，session中loginId:"+loginId);
		
		User u=new User();
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		UserDao ud=new UserDao();
		WCDao wd=new WCDao();
		request.setCharacterEncoding("UTF-8");
		String myresult=request.getParameter("myresult");
		System.out.println("ajax传递数据："+myresult);
		JSONArray jsonarray=JSONArray.fromObject(myresult);
		JSONObject jsonobj;
		
		String flag=null;
		
		u=ud.userInfo(loginId, co);
		System.out.println(u.getAuth());
		if (!u.getAuth().equals("admin")) {
			flag="您没有管理员权限！";
		}else {
			int count=0;
			for(int i=0;i<jsonarray.size();i++){
				jsonobj=jsonarray.getJSONObject(i);
				int mid=jsonobj.getInt("matchid");
				String mrt=jsonobj.getString("matchresult");
				int f=wd.checkMatchResult(mid, co);
				if(f==1) {//比赛未填写结果
					int c=wd.setMatchResult(mid, mrt, co);
					System.out.println("比赛id："+mid+"计入结果："+mrt);
					count=count+c;
					if(c==1) {
						List<BetInfo> bl=new ArrayList();
						bl=wd.queryBetInfo(mid, co);
						for(int j=0;j<bl.size();j++) {
							String buid=bl.get(j).getUserid();
							String bbetinfo=bl.get(j).getBetinfo();
							int cbr=wd.checkBetResult(buid, mid, co);
							if(cbr==1) {
								boolean bbetresult;
								int point=0;
								if (bbetinfo.equals(mrt)) {
									bbetresult=true;
									point=wd.calMatchPoint(mid, co);
								}else {
									bbetresult=false;
								}
								int sbr=wd.setBetResult(buid, mid, bbetresult,point, co);
								if(sbr==1) {
									System.out.println("-----比赛id："+mid+"用户id："+buid+"计入是否猜对："+bbetresult+"本场积分"+point);
								}else {
									System.out.println("-----比赛id："+mid+"用户id："+buid+"未计入结果！");
								}
							}
						}
					}
				}else {
					System.out.println("比赛id："+mid+"已有结果未计入");
				}
			}
			flag="本次写入"+count+"场";
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(flag);//返回是否提交成功
		writer.flush();
		writer.close();
	}

}
