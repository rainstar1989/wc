package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wc.bean.Match;
import wc.dao.WCDao;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class MatchTobetServlet
 */
@WebServlet("/MatchTobetServlet")
public class MatchTobetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MatchTobetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId=(String)session.getAttribute("loginId");
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
		System.out.println("---"+nowtime+"matchtobetservlet，session中loginId:"+loginId+"---");
		
		List<Match> li = null;
		try {
			WCDao td=new WCDao();
			
			li=td.queryMatchtobet(loginId);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		JSONArray jsonarray=JSONArray.fromObject(li.toArray());
		
		System.out.println("jsonarray大小"+jsonarray.size());
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(jsonarray.toString());
		writer.flush();
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
