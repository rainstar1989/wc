$(document).ready(function(){
	$("#submitbt").click(function(){
		if($("#userid").val()==""){       //验证用户名是否为空
			//alert("请输入用户名！");
			$("#hintl").html("请输入账号！");
			$("#userid").focus();
			return false;
		}
		if($("#password").val()==""){       //验证密码是否为空
			//alert("请输入密码！");
			$("#hintl").html("请输入密码！");
			$("#password").focus();
			return false;
		}   
		var param="LoginServlet?action=login"; 
		$.ajax({
			type: "post",
			url: param,
			data: {
				userid:$("#userid").val(),
				password:$("#password").val()
			},
			dataType: "text",
			success: function (data){
				//alert(data);
				if(data == "false"){
					$("#hintl").html("账号或密码错误！");
					$("#userid").val("");
					$("#password").val("");
					$("#userid").focus();
					return false;
				}else{
					window.location.href = "index.jsp";//跳转到主页
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				// 状态码
				alert(XMLHttpRequest.status);
				// 状态
				alert(XMLHttpRequest.readyState);
				// 错误信息   
				alert(textStatus);
				}
    		});
	});
	
	$("#registerbt").click(function(){
		alert("注册");
	});
	
	$("#reguserid").blur(function(){
		if($("#reguserid").val()==""){
			return false;
		}else{
			var checkidurl="UseridcheckServlet";
			$.ajax({
				type: "post",
				url: checkidurl,
				data:{
					reguserid:$("#reguserid").val()
					},
				dataType: "text",
				success:function (data){
					if (data=="false"){
						$("#hintr").html("账号已注册！");
						$("#reguserid").focus();
						return false;
					}else if (data=="true"){
						$("#hintr").html("账号可注册！");
						return false;
					}else{
						$("#hintr").html("读取账号错误！");
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					// 状态码
					alert(XMLHttpRequest.status);
					// 状态
					alert(XMLHttpRequest.readyState);
					// 错误信息   
					alert(textStatus);
				}
			});
		}
	});
	
	$(".SignContainer-switch").click(function(){
		$("#loginarea").toggle();
		$("#registerarea").toggle();
		$("#loginswitch").toggle();
		$("#registerswitch").toggle();
	});
	

})