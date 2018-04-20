﻿$(document).ready(function(){
$("#submitbt").click(function(){
    if($("#userid").val()==""){       //验证用户名是否为空
        //alert("请输入用户名！");
        $("#hint").html("请输入账号！");
        $("#userid").focus();
        return false;
    }
    if($("#password").val()==""){       //验证密码是否为空
        //alert("请输入密码！");
        $("#hint").html("请输入密码！");
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
            $("#hint").html("账号或密码错误！");
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

})

})