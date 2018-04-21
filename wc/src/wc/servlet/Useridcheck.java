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
@WebServlet("/Useridcheck")
public class Useridcheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Useridcheck() {
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
		System.out.println("收到userid检查请求");
		UserDao ud=new UserDao();
		User u=new User();
		ConnectionFactory coF=new ConnectionFactory();
		Connection co=coF.getConnection();
		System.out.println("账号："+request.getParameter("reguserid")+"检查是否重复");
		request.setCharacterEncoding("UTF-8");
		u.setUserid(request.getParameter("reguserid"));
		int f=ud.checkId(u,co);
		String flag = "false";
		if(f==1) {//id不重复，可注册
			flag = "true";
			System.out.println(u.getUserid()+"数据库无记录，可以注册");
		}if(f==0) {//id重复，不可注册
			flag = "false";
			System.out.println(u.getUserid()+"数据库有记录，不可注册");
		}else {
			flag = "error";
			System.out.println("checkId失败！");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(flag);//返回是否可注册信息
		writer.flush();
		writer.close();
	}

}
