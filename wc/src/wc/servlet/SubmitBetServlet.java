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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
		System.out.println("---"+nowtime+"SubmitBetServlet，session中loginId:"+loginId+"---");
		
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
			
			int f=td.checkBet(loginId, mid);
			if(f==1) {//比赛id不重复
				int cm=td.checkMatchTime(mid);
				if(cm==1) {//竞猜时间早于比赛时间
					int c=td.bet(loginId, mid, bi);
					System.out.println("用户id："+loginId+",比赛id："+mid+",计入:"+bi);
					count=count+c;
				}else {
					System.out.println("开赛时间已过无法下注，用户id："+loginId+",比赛id："+mid);
				}
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
