package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import wc.bean.User;
import wc.dao.ConnectionFactory;
import wc.dao.UserDao;
import wc.dao.WCDao;

/**
 * Servlet implementation class ScoreBoardServlet
 */
@WebServlet("/ScoreBoardServlet")
public class ScoreBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId=(String)session.getAttribute("loginId");
		System.out.println("ScoreBoardServlet，session中loginId:"+loginId);
		
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		WCDao td=new WCDao();
		UserDao ud=new UserDao();
		
		List<User> li=ud.userList(co);
		for (int i=0;i<li.size();i++) {
			int up=td.queryUserPoint(li.get(i).getUserid(), co);
			int bn=td.queryBingoNumber(li.get(i).getUserid(), co);
			li.get(i).setUserpoint(up);
			li.get(i).setBingonumber(bn);
		}
		Collections.sort(li,Collections.reverseOrder());
		for (int j=0;j<li.size();j++) {
			li.get(j).setRank(j+1);
		}
		JSONArray jsonarray=JSONArray.fromObject(li.toArray());
		System.out.println("jsonarray大小"+jsonarray.size()+jsonarray.toString());
		
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
