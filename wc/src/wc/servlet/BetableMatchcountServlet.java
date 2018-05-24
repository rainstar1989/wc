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
 * Servlet implementation class BetableMatchcountServlet
 */
@WebServlet("/BetableMatchcountServlet")
public class BetableMatchcountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BetableMatchcountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId=(String)session.getAttribute("loginId");
		System.out.println("BetableMatchcountServlet，session中loginId:"+loginId);
		
		WCDao wd=new WCDao();
		int betablematchcount=wd.countBetableMatch();
		System.out.println(betablematchcount);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(String.valueOf(betablematchcount));
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
