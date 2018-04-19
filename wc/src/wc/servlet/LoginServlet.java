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

import wc.bean.User;
import wc.dao.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String action=request.getParameter("action");
		if("login".equals(action)){//用户登陆
            this.login(request, response);
        }
	}
	
	private void login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		UserDao ud=new UserDao();
		User u=new User();
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		request.setCharacterEncoding("UTF-8");
		u.setUserid(request.getParameter("userid"));
		u.setPassword(request.getParameter("password"));
		int f=ud.login(u, co);
		boolean flag=false;
		if(f>=0) {//用户登陆成功
			HttpSession session=request.getSession();
			session.setAttribute("userid",u.getUserid());
			flag = true;
		}else {
			flag = false;
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(flag);//返回登录信息
        out.flush();
        out.close();
	}

}
