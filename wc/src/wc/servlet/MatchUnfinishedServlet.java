package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import wc.bean.Match;
import wc.dao.ConnectionFactory;
import wc.dao.WCDao;

/**
 * Servlet implementation class MatchUnfinishedServlet
 */
@WebServlet("/MatchUnfinishedServlet")
public class MatchUnfinishedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MatchUnfinishedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId=(String)session.getAttribute("loginId");
		System.out.println("MatchUnfinishedServlet，session中loginId:"+loginId);
		
		List<Match> li = null;
		try {
			WCDao td=new WCDao();
			
			li=td.queryMatchUnfinished();
			
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
