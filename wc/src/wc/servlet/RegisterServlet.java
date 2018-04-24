package wc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wc.bean.User;
import wc.dao.ConnectionFactory;
import wc.dao.UserDao;

/**
 * Servlet implementation class Useridcheck
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		System.out.println("收到注册请求");
		UserDao ud=new UserDao();
		User u=new User();
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		request.setCharacterEncoding("UTF-8");
		u.setUserid(request.getParameter("reguserid"));
		u.setPassword(request.getParameter("regpassword1"));
		u.setRemark(request.getParameter("regusername"));
		int f=ud.checkId(u,co);
		System.out.println("账号："+request.getParameter("reguserid")+"检查是否重复");
		
		String flag = "false";
		if(f==1) {//id不重复，可注册
			System.out.println(u.getUserid()+"数据库无记录，可以注册");
			int r=ud.reg(u,co);
			if (r==1) {
				flag = "true";
				System.out.println(u.getUserid()+"注册成功");
			}else {
				flag = "false";
				System.out.println(u.getUserid()+"注册失败");
			}
		}else if(f==0) {//id重复，不可注册
			flag = "duplicate";
			System.out.println(u.getUserid()+"数据库有记录，不可注册");
		}else {
			flag = "error";
			System.out.println("checkId失败！");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(flag);//返回是否完成注册
		writer.flush();
		writer.close();
	}

}