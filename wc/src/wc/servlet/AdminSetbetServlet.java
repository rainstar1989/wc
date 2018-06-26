package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import wc.dao.WCDao;

/**
 * Servlet implementation class AdminSetbetServlet
 */
@WebServlet("/AdminSetbetServlet")
public class AdminSetbetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSetbetServlet() {
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
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
		System.out.println("---"+nowtime+"AdminSetbetServlet，session中loginId:"+loginId+"---");
		
		WCDao td=new WCDao();
		request.setCharacterEncoding("UTF-8");
		String myBet = request.getParameter("myBet");
		System.out.println("ajax传递数据："+myBet);
		JSONObject jsonobj=JSONObject.fromObject(myBet);
		int count=0;
		int mid=jsonobj.getInt("matchid");
		String bi=jsonobj.getString("betinfo");
		String uid=jsonobj.getString("userid");
		String flag="";
		int f=td.checkBet(uid, mid);
		if(f==1) {//比赛id不重复
			int c=td.bet(uid, mid, bi);
			System.out.println("用户id："+uid+",比赛id："+mid+",计入:"+bi);
			count=count+c;
			flag = "本次竞猜"+count+"场";
		}else {
			System.out.println("用户id："+uid+",比赛id："+mid+",已存在未计入");
			flag="用户id："+uid+",比赛id："+mid+",已存在未计入";
		}
		
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(flag);//返回是否提交成功
		writer.flush();
		writer.close();
	}

}
