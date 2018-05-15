window.onpageshow = function (event) {
if (event.persisted) {//如果是读的缓存让页面强制刷新
        window.location.reload()
    }
}

$(document).ready(function(){
	
	function matchtofinish(){//读取未填写结果的比赛列表
		$.ajax({
			type: "get",
			url: "MatchUnfinishedServlet",
			data: {},
			dataType: "json",
			beforeSend:function(XMLHttpRequest){
				$("#myModal").modal('toggle');
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
					str+="</div></div></td></tr></tbody>";
				}
				$("#unfinishedlist").html(str);
				if (data.length>0){
					
					$("#dtxbd").show();
					
				}else{
					$("#dtxbd").hide();
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
				alert("MatchUnfinishedServlet ajax出错");
			}
		});
	};
	matchtofinish();//setresult页面载入时读取未填写结果的比赛列表
	
	$("#dtxtab").click(function(){//点击未填写标签执行读取未填写结果的比赛列表
		matchtofinish();
	});
	
	var onoff=false;
	
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
				data: {myresult:resString},
				dataType: "text",
				beforeSend:function(XMLHttpRequest){
					$("#myModal").modal('toggle');
				},
				success: function (data){
					$("#myModalLabel").toggle();
					$("#setresp").text(data);
					$("#setresp").toggle();
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
		$("#setresp").hide();
		if (onoff){
			matchtofinish();
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
	});
	
	$("#ttsa").click(function(){
		$("#tts").show();
		$("#ttsa").addClass("active");
		$("#sgtb").hide();
		$("#sgtba").removeClass("active");
	});
	
	
})