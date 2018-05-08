package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wc.dao.ConnectionFactory;
import wc.dao.WCDao;

/**
 * Servlet implementation class SubmitBetServlet
 */
@WebServlet("/SubmitBetServlet")
public class SubmitBetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitBetServlet() {
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
		System.out.println("SubmitBetServlet，session中loginId:"+loginId);
		
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		WCDao td=new WCDao();
		
		request.setCharacterEncoding("UTF-8");
		String myBet = request.getParameter("myBet");
		System.out.println("ajax传递数据："+myBet);
		JSONArray jsonarray=JSONArray.fromObject(myBet);
		JSONObject jsonobj;
		int count=0;
		for(int i=0;i<jsonarray.size();i++){
			jsonobj=jsonarray.getJSONObject(i);
			int mid=jsonobj.getInt("matchid");
			String bi=jsonobj.getString("betinfo");
			
			int f=td.checkBet(loginId, mid, co);
			if(f==1) {//id不重复，可注册
				int c=td.bet(loginId, mid, bi, co);
				System.out.println("用户id："+loginId+",比赛id："+mid+",计入:"+bi);
				count=count+c;
			}else {
				System.out.println("用户id："+loginId+",比赛id："+mid+",已存在未计入");
			}
		}
			
		
		String flag = "本次竞猜"+count+"场";
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(flag);//返回是否提交成功
		writer.flush();
		writer.close();
	}

}
