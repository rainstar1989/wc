<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<title>登陆</title>
</head>
<body>
<div class="SignFlowHomepage-content">
	<div class="Card SignContainer-content">
		<div class="SignFlowHeader" style="padding-bottom:5px;">
			<img src="images/254645_w.png" />
			<div class="SignFlowHeader-slogen">2018 FIFA World Cup Russia™</div>
		</div>
		<div class="SignContainer-inner">
			<div class="Login-content">
				<div class="SignFlow" name="loginform"  id="loginform" >
					<div class="SignFlow-account">
						<div class="SignFlowInput">
							<input type="text" id="userid" class="Input" placeholder="账号">
						</div>
					</div>
					<div class="SignFlow-password">
						<div class="SignFlowInput">
							<input type="text" id="password" class="Input" placeholder="密码">
						</div>
					</div>
					<button class="button" id="submitbt" >登陆</button>
				</div>
			</div>
			<div class="SignContainer-switch">
			</div>
		</div>
	</div>
</div>
</body>
</html>