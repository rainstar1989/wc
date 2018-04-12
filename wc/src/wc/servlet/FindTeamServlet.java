package wc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wc.bean.Team;
import wc.dao.*;

/**
 * Servlet implementation class FindTeamServlet
 */
@WebServlet("/FindTeamServlet")
public class FindTeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindTeamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ConnectionFactory coF=new ConnectionFactory();
			Connection co=coF.getConnection();
			WCDao td=new WCDao();
			
			List<Team> li=td.queryTeam(co);
			request.setAttribute("list",li);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("team.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
