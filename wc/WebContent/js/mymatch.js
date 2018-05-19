window.onpageshow = function (event) {
if (event.persisted) {//如果是读的缓存让页面强制刷新
        window.location.reload()
    }
}

$(document).ready(function(){
	var userinfourl="UserInfoServlet"; 
	$.ajax({
		type: "get",
		url: userinfourl,
		data: {},
		dataType: "json",
		success: function (data){
			$("#userName span").html(data.remark);
			$("#userScore span").html(data.userpoint);
			$("#bingoNumber span").html(data.bingonumber);
			if (data.auth=="admin"){
				$("#manage").show();
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
//			// 状态码
//			alert(XMLHttpRequest.status);
//			// 状态
//			alert(XMLHttpRequest.readyState);
//			// 错误信息   
//			alert(textStatus);
			alert("UserInfoServlet ajax出错");
		}
	});
	
	
	function matchtobetlist(){//读取未预测比赛列表
		$.ajax({
			type: "get",
			url: "MatchTobetServlet",
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('toggle');
			},
			success: function (data){
				
				var str = "";
				for(var i=0;i<data.length;i++){
					str+="<li class='list-group-item row'>";
					str+="<div class='row text-center'>"+data[i].matchdate+"</div>";
					str+="<div class='row text-center'>";
					str+="<div class='col-xs-4 col-sm-4 col-md-4 col-lg-4'>"+data[i].hteam+"</div>";
					str+="<div class='col-xs-1 col-sm-1 col-md-1 col-lg-1'><img src='images/wcflag/"+data[i].htm+".png' class='flag'></div>";
					str+="<div class='col-xs-2 col-sm-2 col-md-2 col-lg-2'>"+data[i].matchtime+"</div>";
					str+="<div class='col-xs-1 col-sm-1 col-md-1 col-lg-1'><img src='images/wcflag/"+data[i].gtm+".png' class='flag'></div>";
					str+="<div class='col-xs-4 col-sm-4 col-md-4 col-lg-4'>"+data[i].gteam+"</div>";
					str+="</div>";
					str+="<div class='row text-center'>";
					str+="<div class='btn-group wtl' data-toggle='buttons'>";
					str+="<label class='btn btn-primary btn-md green checkbb' data-matchid='"+data[i].matchid+"' data-betinfo='w'><input type='radio' name='options' >胜</label>";
					str+="<label class='btn btn-primary btn-md yellow checkbb' data-matchid='"+data[i].matchid+"' data-betinfo='t'><input type='radio' name='options' >平</label>";
					str+="<label class='btn btn-primary btn-md red checkbb' data-matchid='"+data[i].matchid+"' data-betinfo='l'><input type='radio' name='options' >负</label>";
					str+="</div>";
					str+="</div>";
					str+="</li>";
				}
				$("#dyclist").html(str);
				if (data.length>0){
					
					$("#bba").show();
					
				}else{
					$("#bba").hide();
				}
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('toggle');
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
//				// 状态码
//				alert(XMLHttpRequest.status);
//				// 状态
//				alert(XMLHttpRequest.readyState);
//				// 错误信息   
//				alert(textStatus);
				alert("MatchTobetServlet ajax出错");
			}
		});
	};
	
	function betedmatchlist(){//读取已预测比赛列表
		$.ajax({
			type: "get",
			url: "BetedMatchServlet",
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('toggle');
			},
			success: function (data){
				var str ="<tbody><tr><td>比赛id</td><td>对阵</td><td>您的预测</td><td>是否猜中</td><td>本场积分</td></tr>";
				for(var i=0;i<data.length;i++){
					str+="<tr><td>"+data[i].matchid+"</td><td>"+data[i].hteam+"vs"+data[i].gteam+"</td><td>"+data[i].betinfo+"</td><td>"+data[i].betresult+"</td><td>"+data[i].matchpoint+"</td></tr>";
				}
				str+="</tbody>";
				$("#betedmatchlist").html(str);
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('toggle');
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
//				// 状态码
//				alert(XMLHttpRequest.status);
//				// 状态
//				alert(XMLHttpRequest.readyState);
//				// 错误信息   
//				alert(textStatus);
				alert("BetedMatchServlet ajax出错");
			}
		});
	}
	
	function scoreboard(){
		$.ajax({
			type: "get",
			url: "ScoreBoardServlet",
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('toggle');
			},
			success: function (data){
				var str = "<tbody><tr><td>排名</td><td>昵称</td><td>猜对场数</td><td>积分</td></tr>";
				for(var i=0;i<data.length;i++){
					var myclass="";
					if(data[i].remark==$("#userName span").html()){
						myclass="class='myname'";
					}
					str+="<tr><td>"+data[i].rank+"</td><td "+myclass+">"+data[i].remark+"</td><td>"+data[i].bingonumber+"</td><td>"+data[i].userpoint+"</td></tr>";
				}
				str+="</tbody>";
				$("#scoreboardlist").html(str);
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('toggle');
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
//				// 状态码
//				alert(XMLHttpRequest.status);
//				// 状态
//				alert(XMLHttpRequest.readyState);
//				// 错误信息   
//				alert(textStatus);
				alert("ScoreBoardServlet ajax出错");
			}
		});
	}
	
	matchtobetlist();//mymatch页面载入时执行读取未预测比赛列表
	
	$("#dyctab").click(function(){//点击未预测标签执行读取未预测比赛列表
		matchtobetlist();
	});
	
	$("#yyctab").click(function(){//点击已预测标签执行读取已预测比赛列表
		betedmatchlist();
	});
	
	var onoff=false;
	
	$("#betbutton").click(function(){//点击竞猜按钮提交竞猜结果
		var betArray=new Array();
		$(".checkbb.active").each(function(){
			var betObj={};
			betObj["matchid"]=$(this).data("matchid");
			betObj["betinfo"]=$(this).data("betinfo");
			betArray.push(betObj);
		});
		var betString=JSON.stringify(betArray)
		if (betString=="[]"){
			return false;
		}else{
			
			$.ajax({
				type: "post",
				url: "SubmitBetServlet",
				data: {myBet:betString},
				dataType: "text",
				beforeSend:function(XMLHttpRequest){
					$("#myModal").modal('toggle');
				},
				success: function (data){
					$("#myModalLabel").toggle();
					$("#betresp").text(data);
					$("#betresp").toggle();
					onoff=true;
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert("SubmitBetServlet ajax出错");
				}
			});
		}
	});
	
	
	
	$("#myModal").on('hidden.bs.modal', function(){//模态框消失时重置模态框内容
		$("#myModalLabel").show();
		$("#betresp").hide();
		if (onoff){
			matchtobetlist();
			onoff=false;
		}else{
			return false;
		}
	});
	
	$("#wdyca").click(function(){
		$("#wdyc").show();
		$("#wdyca").addClass("active");
		$("#phb").hide();
		$("#phba").removeClass("active");
		$("#jfgz").hide();
		$("#jfgza").removeClass("active");
		if($("#dyc").hasClass("active")){
			matchtobetlist();
		}
		if($("#yyc").hasClass("active")){
			betedmatchlist();
		}
		
	});
	
	$("#phba").click(function(){
		$("#phb").show();
		$("#phba").addClass("active");
		$("#wdyc").hide();
		$("#wdyca").removeClass("active");
		$("#jfgz").hide();
		$("#jfgza").removeClass("active");
		scoreboard();
	});
	
	$("#jfgza").click(function(){
		$("#jfgz").show();
		$("#jfgza").addClass("active");
		$("#wdyc").hide();
		$("#wdyca").removeClass("active");
		$("#phb").hide();
		$("#phba").removeClass("active");
	});
	
})