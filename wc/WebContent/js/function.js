$(document).ready(function(){
	var uid="";
	var ruid="";
	$("#submitbt").click(function(){
		if($("#userid").val()==""){       //验证用户名是否为空
			//alert("请输入用户名！");
			$("#hintl span").html("请输入账号！");
			$("#userid").focus();
			return false;
		}
		if($("#password").val()==""){       //验证密码是否为空
			//alert("请输入密码！");
			$("#hintl span").html("请输入密码！");
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
			beforeSend:function(XMLHttpRequest){
				$("#hintl img").toggle();
				$("#hintl span").html("登录中...");
			},
			success: function (data){
				if(data == "false"){
					$("#hintl span").html("账号或密码错误！");
					$("#userid").val("");
					$("#password").val("");
					$("#userid").focus();
					return false;
				}else{
					uid=$("#userid").val();
					window.location.href = "index.jsp";//跳转到主页
				}
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#hintl img").toggle();
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
		if ($("#reguserid").val()==""){
			$("#hintr span").html("请输入账号！");
			$("#reguserid").focus();
			return false;
		}
		if ($("#regusername").val()==""){
			$("#hintr span").html("请输入昵称！");
			$("#regusername").focus();
			return false;
		}
		if ($("#regpassword1").val()==""){
			$("#hintr span").html("请输入密码！");
			$("#regpassword1").focus();
			return false;
		}
		if ($("#regpassword2").val()==""){
			$("#hintr span").html("请再次输入密码！");
			$("#regpassword2").focus();
			return false;
		}
		if ($("#regpassword1").val()!=$("#regpassword2").val()){
			$("#hintr span").html("输入密码不一致请重输！");
			$("#regpassword1").val()=="";
			$("#regpassword2").val()=="";
			$("#regpassword1").focus();
			return false;
		}else{
			var param="RegisterServlet";
			$.ajax({
				type: "post",
				url: param,
				data: {
					reguserid:$("#reguserid").val(),
					regpassword1:$("#regpassword1").val(),
					regusername:$("#regusername").val()
				},
				dataType: "text",
				beforeSend:function(XMLHttpRequest){
					$("#hintr img").toggle();
					$("#hintr span").html("注册中...");
				},
				success: function (data){
					if(data == "duplicate"){
						$("#hintr span").html("该账号已注册！");
						$("#regpassword1").val("");
						$("#regpassword2").val("");
						$("#reguserid").focus();
						return false;
					}else if(data == "true"){
						$("#hintr span").html("注册成功！点击登录");
						ruid=$("#reguserid").val();
						$("#hintr span").attr("id","aa");//添加链接
						$("#aa").on("click",function(){
							$("#loginarea").toggle();
							$("#registerarea").toggle();
							$("#loginswitch").toggle();
							$("#registerswitch").toggle();
							$("#userid").val(ruid);
							$("#password").focus();
							$("#aa").unbind("click");
							$("#hintr span").removeAttr("id","aa");
			            });
						$("#reguserid").val("");
						$("#regusername").val("");
						$("#regpassword1").val("");
						$("#regpassword2").val("");
						return false;
					}else{
						$("#hintr span").html("注册失败！联系管理员");
						return false;
					}
				},
				complete:function(XMLHttpRequest,textStatus){
					$("#hintr img").toggle();
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
		$("#hintl span").html("");
		$("#hintr span").html("");
	});
	
	

})