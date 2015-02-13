var i18nConf={
	"US":{
		"title":"Sign in",
		"language":"English",
		"username":"Username:",
		"password":"Password:"
	},
	"ZHS":{
		"title":"登录",
		"language":"中文",
		"username":"用户名:",
		"password":"口令:"
	}
}
$(".languageSel").change(function(){
	var language=$(".languageSel").val();
	$(".login_txt").text(i18nConf[language].title);
	$(".logint_btn").text(i18nConf[language].title);
	$(".target_language").text(i18nConf[language].language);
	$(".username_txt").text(i18nConf[language].username);
	$(".password_txt").text(i18nConf[language].password);
});
function inputChange(){
	if($("#username").val()!=""&&$("#password").val()!="")
		$(".logint_btn").css("background-image","url('static/images/login_btn_input.png')");
	else
		$(".logint_btn").css("background-image","url('static/images/login_btn_none.png')");
}
$("#username").bind('input propertychange',inputChange); 
$("#password").bind('input propertychange', inputChange);
function submit(){
	var language=$(".languageSel").val();
	var username=$("#username").val();
	var password=$("#password").val();
	if(username==""){
		$(".error_info").text("用户名不能为空");
		return;
	}
	if(password==""){
		$(".error_info").text("密码不能为空");
		return;
	}
	$(".error_info").text("");
	$.ajax({
	     type: 'POST',
	     url: "login" ,
	    data: {
	    	"language":language[0],
	    	"username":username,
	    	"password":password
	    } ,
	    dataType: "json",
	    success: function(data){
	    	if(data.url=="error")
	    		$(".error_info").text("服务器异常请联系管理员");
	    	else{
	    		 window.open(data.url);
	    		 $(".error_info").text("");
//	    		 $("#username").val("");
//	    		 $("#password").val("");
	    	}
	    }
	});
}