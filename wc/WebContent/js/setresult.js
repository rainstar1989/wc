
$(document).ready(function(){
	
	function matchtofinish(){//读取未填写结果的比赛列表
		$.ajax({
			type: "get",
			url: "MatchUnfinishedServlet",
			cache:false,
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('show');
			},
			success: function (data){
				
				var str = "<tbody><tr><td>比赛id</td><td>比赛日期</td><td>对阵</td><td>赛果</td></tr>";
				for(var i=0;i<data.length;i++){
					str+="<tr><td>"+data[i].matchid+"</td><td>"+data[i].matchdate+"</td><td>"+data[i].hteam+"vs"+data[i].gteam+"</td>";
					str+="<td><div class='row text-center'>";
					str+="<div class='btn-group wtl' data-toggle='buttons'>";
					str+="<label class='btn btn-primary btn-md green checkbb' data-matchid='"+data[i].matchid+"' data-matchresult='w'><input type='radio' name='options' >胜</label>";
					str+="<label class='btn btn-primary btn-md yellow checkbb' data-matchid='"+data[i].matchid+"' data-matchresult='t'><input type='radio' name='options' >平</label>";
					str+="<label class='btn btn-primary btn-md red checkbb' data-matchid='"+data[i].matchid+"' data-matchresult='l'><input type='radio' name='options' >负</label>";
					str+="</div></div></td></tr>";
				}
				str+="</tbody>";
				$("#unfinishedlist").html(str);
				if (data.length>0){
					
					$("#dtxbd").show();
					
				}else{
					$("#dtxbd").hide();
				}
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('hide');
			},
			error: function (xhr, textStatus, errorThrown) {
//				// 状态码
//				alert(XMLHttpRequest.status);
//				// 状态
//				alert(XMLHttpRequest.readyState);
//				// 错误信息   
//				alert(textStatus);
				var sessionStatus = xhr.getResponseHeader('sessionstatus');
		        if(sessionStatus == 'timeout') {
		            alert("会话过期，请重新登录！");
		            window.location.replace("login.html");
		        }else{
		        	alert("MatchUnfinishedServlet ajax出错");
		        }
				
			}
		});
	};
	matchtofinish();//setresult页面载入时读取未填写结果的比赛列表
	
	function matchfinished(){//读取未填写结果的比赛列表
		$.ajax({
			type: "get",
			url: "MatchFinishedServlet",
			cache:false,
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('show');
			},
			success: function (data){
				var str ="<tbody><tr><td>比赛id</td><td>对阵</td><td>赛果</td></tr>";
				for(var i=0;i<data.length;i++){
					str+="<tr><td>"+data[i].matchid+"</td><td>"+data[i].hteam+"vs"+data[i].gteam+"</td><td>"+data[i].matchresult+"</td></tr>";
				}
				str+="</tbody>";
				$("#finishedlist").html(str);
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('hide');
			},
			error: function (xhr, textStatus, errorThrown) {
//				// 状态码
//				alert(XMLHttpRequest.status);
//				// 状态
//				alert(XMLHttpRequest.readyState);
//				// 错误信息   
//				alert(textStatus);
				var sessionStatus = xhr.getResponseHeader('sessionstatus');
		        if(sessionStatus == 'timeout') {
		            alert("会话过期，请重新登录！");
		            window.location.replace("login.html");
		        }else{
		        	alert("MatchFinishedServlet ajax出错");
		        }
				
			}
		});
	}
	
	function playoffsmatch(){//读取淘汰赛列表
		$.ajax({
			type: "get",
			url: "PlayOffsServlet",
			cache:false,
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('show');
			},
			success: function (data){
				var str = "<tbody><tr><td>比赛id</td><td>主队</td><td>客队</td><td>操作</td></tr>";
				for(var i=0;i<data.length;i++){
					str+="<tr><td>"+data[i].matchid+"</td><td><input type='text' class='inputt htm' value='"+data[i].htm+"'></td>";
					str+="<td><input type='text' class='inputt gtm' value='"+data[i].gtm+"'></td>";
					str+="<td><button class='btn btn-primary btn-block pob' data-mid='"+data[i].matchid+"'>提交</button></td></tr>";
				}
				str+="</tbody>";
				$("#playoffslist").html(str);
				
				$(".pob").on("click",function(){//提交淘汰赛队名
					var pomid=$(this).data("mid");
					var pohtm=$(this).parent().siblings().children(".htm").val();
					var pogtm=$(this).parent().siblings().children(".gtm").val();
					
					$.ajax({
						type: "post",
						url: "SetPlayoffsServlet",
						cache:false,
						data: {pomid:pomid,pohtm:pohtm,pogtm:pogtm},
						dataType: "text",
						beforeSend:function(XMLHttpRequest){
							$("#myModal").modal('show');
						},
						success: function (data){
							$("#myModalLabel").toggle();
							$("#setresp").html(data);
							$("#setresp").toggle();
							onoff=true;
							rorp="setplayoffs";
						},
						error: function (xhr, textStatus, errorThrown) {
							var sessionStatus = xhr.getResponseHeader('sessionstatus');
					        if(sessionStatus == 'timeout') {
					            alert("会话过期，请重新登录！");
					            window.location.replace("login.html");
					        }else{
					        	alert("SetPlayoffsServlet ajax出错");
					        }
							
						}
					});
				});
				
				$(".inputt").on("click",function(){
					$(this).select();
				});
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('hide');
			},
			error: function (xhr, textStatus, errorThrown) {
				var sessionStatus = xhr.getResponseHeader('sessionstatus');
		        if(sessionStatus == 'timeout') {
		            alert("会话过期，请重新登录！");
		            window.location.replace("login.html");
		        }else{
		        	alert("PlayOffsServlet ajax出错");
		        }
				
			}
		});
		
		
	}
	
	$("#dtxtab").click(function(){//点击未填写标签执行读取未填写结果的比赛列表
		matchtofinish();
	});
	
	$("#ytxtab").click(function(){//点击填写标签执行读取已填写结果的比赛列表
		matchfinished();
	});
	
	var onoff=false;
	var rorp=null;//setresult还是setplayoffs
	
	$("#dtxbb").click(function(){//点击竞猜按钮提交竞猜结果
		var resArray=new Array();
		$(".checkbb.active").each(function(){
			var resObj={};
			resObj["matchid"]=$(this).data("matchid");
			resObj["matchresult"]=$(this).data("matchresult");
			resArray.push(resObj);
		});
		var resString=JSON.stringify(resArray)
		if (resString=="[]"){
			return false;
		}else{
			
			$.ajax({
				type: "post",
				url: "SetResultServlet",
				cache:false,
				data: {myresult:resString},
				dataType: "text",
				beforeSend:function(XMLHttpRequest){
					$("#myModal").modal('show');
				},
				success: function (data){
					$("#myModal").modal('show');
					$("#myModalLabel").toggle();
					$("#setresp").html(data);
					$("#setresp").toggle();
					onoff=true;
					rorp="setresult";
				},
				error: function (xhr, textStatus, errorThrown) {
					var sessionStatus = xhr.getResponseHeader('sessionstatus');
			        if(sessionStatus == 'timeout') {
			            alert("会话过期，请重新登录！");
			            window.location.replace("login.html");
			        }else{
			        	alert("SetResultServlet ajax出错");
			        }
					
				}
			});
		}
	});
	
	
	
	$("#myModal").on('hidden.bs.modal', function(){//模态框消失时重置模态框内容
		$("#myModalLabel").show();
		$("#setresp").hide();
		if (onoff){
			if(rorp=="setresult"){
				matchtofinish();
			}else if(rorp=="setplayoffs"){
				playoffsmatch();
			}
			rorp=null;
			onoff=false;
		}else{
			return false;
		}
	});
	
	$("#sgtba").click(function(){
		$("#sgtb").show();
		$("#sgtba").addClass("active");
		$("#tts").hide();
		$("#ttsa").removeClass("active");
		$("#jccc").hide();
		$("#jccca").removeClass("active");
		if($("#dtx").hasClass("active")){
			matchtofinish();
		}
		if($("#ytx").hasClass("active")){
			matchfinished();
		}
		
	});
	
	$("#ttsa").click(function(){
		$("#tts").show();
		$("#ttsa").addClass("active");
		$("#sgtb").hide();
		$("#sgtba").removeClass("active");
		$("#jccc").hide();
		$("#jccca").removeClass("active");
		playoffsmatch();
	});
	
	$("#jccca").click(function(){
		$("#jccc").show();
		$("#jccca").addClass("active");
		$("#sgtb").hide();
		$("#sgtba").removeClass("active");
		$("#tts").hide();
		$("#ttsa").removeClass("active");
//		betablematchcount();
//		betedmatchcount();
		userlist();
	});
	
//	function betablematchcount(){
//		$.ajax({
//			type: "get",
//			url: "BetableMatchcountServlet",
//			cache:false,
//			data: {},
//			dataType: "text",
//			beforeSend:function(XMLHttpRequest){
//				$("#myModal").modal('show');
//			},
//			success: function (data){
//				$("#betablematchcount span").html(data);
//			},
//			complete:function(XMLHttpRequest,textStatus){
//				$("#myModal").modal('hide');
//			},
//			error: function (xhr, textStatus, errorThrown) {
//				var sessionStatus = xhr.getResponseHeader('sessionstatus');
//		        if(sessionStatus == 'timeout') {
//		            alert("会话过期，请重新登录！");
//		            window.location.replace("login.html");
//		        }else{
//		        	alert("BetableMatchcountServlet ajax出错");
//		        }
//				
//			}
//		});
//	};
	
//	function betedmatchcount(){
//		$.ajax({
//			type: "get",
//			url: "BetedMatchcountServlet",
//			cache:false,
//			data: {},
//			dataType: "json",
//			beforeSend:function(XMLHttpRequest){
//				$("#myModal").modal('show');
//			},
//			success: function (data){
//				var str = "<tbody><tr><td>昵称</td><td>已竞猜场次</td></tr>";
//				for(var i=0;i<data.length;i++){
//					str+="<tr><td>"+data[i].remark+"</td><td>"+data[i].betedcount+"</td></tr>";
//				}
//				str+="</tbody>";
//				$("#cclist").html(str);
//			},
//			complete:function(XMLHttpRequest,textStatus){
//				$("#myModal").modal('hide');
//			},
//			error: function (xhr, textStatus, errorThrown) {
//				var sessionStatus = xhr.getResponseHeader('sessionstatus');
//		        if(sessionStatus == 'timeout') {
//		            alert("会话过期，请重新登录！");
//		            window.location.replace("login.html");
//		        }else{
//		        	alert("BetedMatchcountServlet ajax出错");
//		        }
//				
//			}
//		});
//	};
	
	function userlist(){
		$.ajax({
			type: "get",
			url: "ScoreBoardServlet",
			cache:false,
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('show');
			},
			success: function (data){
				var str="<option>---请选择---</option>";
				for(var i=0;i<data.length;i++){
					str+="<option data-uid='"+data[i].userid+"'>"+data[i].remark+"</option>";
				}
				$("#sslist").html(str);
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#myModal").modal('hide');
				
			},
			error: function (xhr, textStatus, errorThrown) {
//				// 状态码
//				alert(XMLHttpRequest.status);
//				// 状态
//				alert(XMLHttpRequest.readyState);
//				// 错误信息   
//				alert(textStatus);
				var sessionStatus = xhr.getResponseHeader('sessionstatus');
		        if(sessionStatus == 'timeout') {
		            alert("会话过期，请重新登录！");
		            window.location.replace("login.html");
		        }else{
		        	alert("ScoreBoardServlet ajax出错");
		        }
				
			}
		});
	};
	
	
	$("#bbsb").click(function(){
		if ($("#mmid").val()!=""&&$("#sslist").children('option:selected').val()!="---请选择---"&&$("#wtllist").children('option:selected').val()!="---请选择---"){
			var betObj={};
			betObj["matchid"]=$("#mmid").val();
			betObj["betinfo"]=$("#wtllist").children('option:selected').data("br");
			betObj["userid"]=$("#sslist").children('option:selected').data("uid");
			var betString=JSON.stringify(betObj)
			$.ajax({
				type: "post",
				url: "AdminSetbetServlet",
				cache:false,
				data: {myBet:betString},
				dataType: "text",
				beforeSend:function(XMLHttpRequest){
					$("#myModal").modal('show');
				},
				success: function (data){
					$("#myModal").modal('show');
					$("#myModalLabel").toggle();
					$("#setresp").html(data);
					$("#setresp").toggle();
				},
				error: function (xhr, textStatus, errorThrown) {
					var sessionStatus = xhr.getResponseHeader('sessionstatus');
			        if(sessionStatus == 'timeout') {
			            alert("会话过期，请重新登录！");
			            window.location.replace("login.html");
			        }else{
			        	alert("AdminSetbetServlet ajax出错");
			        }
					
				}
			});
		}else{
			alert("请填写完整！");
			return false;
		}
	});
	
})