$(document).ready(function(){
	var param="UserInfoServlet"; 
	$.ajax({
		type: "get",
		url: param,
		data: {},
		dataType: "json",
		success: function (data){
			$("#userName span").html(data.remark);
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