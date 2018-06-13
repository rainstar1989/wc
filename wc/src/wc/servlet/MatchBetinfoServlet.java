package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wc.dao.WCDao;

/**
 * Servlet implementation class MatchBetinfoServlet
 */
@WebServlet("/MatchBetinfoServlet")
public class MatchBetinfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MatchBetinfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId=(String)session.getAttribute("loginId");
		System.out.println("MatchBetinfoServlet，session中loginId:"+loginId);
		
		request.setCharacterEncoding("UTF-8");
		int mid = Integer.parseInt(request.getParameter("bbfmid"));
		WCDao wd=new WCDao();
		String flag=wd.matchBetInfo(mid);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(flag);
		writer.flush();
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
