package wc.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wc.bean.User;
import wc.dao.ConnectionFactory;
import wc.dao.UserDao;
import wc.dao.WCDao;

/**
 * Servlet implementation class SetPlayoffsServlet
 */
@WebServlet("/SetPlayoffsServlet")
public class SetPlayoffsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPlayoffsServlet() {
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
		System.out.println("SetPlayoffsServlet，session中loginId:"+loginId);
		
		User u=new User();
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		UserDao ud=new UserDao();
		WCDao wd=new WCDao();
		request.setCharacterEncoding("UTF-8");
		int pomid=Integer.parseInt(request.getParameter("pomid"));
		String pohtm=request.getParameter("pohtm");
		String pogtm=request.getParameter("pogtm");
		System.out.println("ajax传递数据：比赛id："+pomid+"主队："+pohtm+"客队："+pogtm);
		
		String flag=null;
		
		u=ud.userInfo(loginId, co);
		System.out.println(u.getAuth());
		if (!u.getAuth().equals("admin")) {
			flag="您没有管理员权限！";
		}else {
			int f=wd.setPlayoffsMatch(pomid, pohtm, pogtm, co);
			if(f==1) {
				
			}
		}
	}

}