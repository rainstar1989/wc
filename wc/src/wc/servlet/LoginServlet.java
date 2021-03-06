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
		Date today=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime=df.format(today);//当前时间
		if("login".equals(action)){//用户登陆
//            this.login(request, response);
			System.out.println("---"+nowtime+"收到登陆请求"+"---");
			UserDao ud=new UserDao();
			User u=new User();
			System.out.println("账号："+request.getParameter("userid")+"试图登陆");
			request.setCharacterEncoding("UTF-8");
			u.setUserid(request.getParameter("userid"));
			u.setPassword(request.getParameter("password"));
			int f=ud.login(u);
			String flag = "false";
			if(f==1) {//用户登陆成功
				HttpSession session=request.getSession();
				session.setAttribute("loginId",u.getUserid());
				String sessionId=session.getId();
				if(session.isNew()) {
					 System.out.println("session创建成功，session的id是："+sessionId);
					 System.out.println("session中存储的用户名为："+session.getAttribute("loginId"));
				}
				else {
					System.out.println("session已存在，session的id是"+sessionId);
					System.out.println("session中存储的用户名为："+session.getAttribute("loginId"));
				}
				flag = "true";
				System.out.println(u.getUserid()+"登陆成功");
			}else {
				flag = "false";
				System.out.println(u.getUserid()+"登陆失败");
			}
			System.out.println(flag);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(flag);//返回登录信息
			writer.flush();
			writer.close();
        }
	}
	
}
