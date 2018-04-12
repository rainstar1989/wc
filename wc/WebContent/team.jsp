<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@page import="wc.bean.Team"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>队伍</title>
</head>
<body>
	<table align="center" width="450" border="1" height="30" bordercolor="white"  cellpadding="1" cellspacing="1">
		<tr>
			<th>队伍代码</th><th>队伍名称</th><th>小组名称</th><th>小组积分</th>
		</tr>
		<%
			List<Team> list=(List<Team>)request.getAttribute("list");
			if (list==null||list.size()<1){
				out.println("没有数据");
				}else{
					for(Team team:list){
						%>
						<tr align="center" >
			 				<td><%=team.getTeamid()%></td>
			 				<td><%=team.getTeamname()%></td>
			 				<td><%=team.getGroup()%></td>
			 				<td><%=team.getGppoint()%></td>
						</tr>
						<%
					}
				}
		%>
		
	</table>
</body>
</html>
