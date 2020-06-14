$(function() {
	/**
	 * 检查所有错误，决定错误信息的显示
	 */
	$(".errorWarn").each(function() {
		showError($(this));
	});
	
	/**
	 * 当input获得焦点时隐藏错误信息
	 */
	$(".inputclass").focus(function() {
		var errorId = $(this).attr("id")+"Error";
		$("#"+errorId).text("");//清空label内容
		showError("#"+errorId);
	});

	/**
	 * input失去焦点进行校验
	 */
	$(".inputclass").blur(function() {
		var id=$(this).attr("id");
		var funname="validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
		eval(funname);
	});
	

});
/*
 * 表单校验函数
 */
function checkForm() {
	var bool=true;
	if(!validateUserName()){
		bool=false;
	}
	if(!validateUserPassword()){
		bool=false;
	}
	if(!validateRepeatPassword()){
		bool=false;
	}
	if(!validateEmail()){
		bool=false;
	}
	if(!validateVerifyCode()){
		bool=false;
	}
	
	return bool;
}
/**
 * 控制错误信息的显示
 */
function showError(ele) {
	var text=ele.text();
	if(!text)
		{
			ele.css("display","none");
		}else{
			ele.css("display","");
		}
}

/**
 * 更换验证码图片
 */
function changeImg() {
	$("#imgVerifyCode").attr("src","/knife-and-sword/VerifyCodeServlet?a=" + new Date().getTime());
}

function validateUserName() {
	var id="userName";
	var value=$("#"+id).val();
	//检验是否为空
	if(!value){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>用户名不能为空!</label>';
		return false;
	}
	//检验用户名长度
	if(value.length<3||value.length>20){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>用户名长度必须在3-20之间</label>';
		return false;
	}
	//检验是否已被注册
	$.ajax({
		url:"/knife-and-sword/UserServlet", 
		data:{method:"ajaxValidateUserName",userName:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>用户名已经被注册</label>';
				return false;
			}
		}
	});
	return true;
}

function validateUserPassword() {
	var id="userPassword";
	var value=$("#"+id).val();
	//检验是否为空
	if(!value){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>密码不能为空!</label>';
		return false;
	}
	//检验密码长度
	if(value.length<3||value.length>20){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>密码长度必须在3-20之间</label>';
		return false;
	}
	return true;
}

function validateRepeatPassword() {
	var id="repeatPassword";
	var value=$("#"+id).val();
	//检验是否为空
	if(!value){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>密码不能为空!</label>';
		return false;
	}
	//检验密码长度
	if(value.length<3||value.length>20){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>密码长度必须在3-20之间</label>';
		return false;
	}
	
	//检验两次密码是否一致
	if(value != $("#userPassword").val()){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>两次密码输入不一致</label>';
		return false;
	}
	return true;
}

function validateEmail() {
	var id="email";
	var value=$("#"+id).val();
	//检验是否为空
	if(!value){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>邮箱不能为空!</label>';
		return false;
	}
	//检验邮箱格式
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>错误的邮箱格式!</label>';
		return false;
	}
	
	$.ajax({
		url:"/knife-and-sword/UserServlet", 
		data:{method:"ajaxValidateEmail",email:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>邮箱已经被注册</label>';
				return false;
			}
		}
	});
	return true;
}
function validateVerifyCode() {
	var id="VerifyCode";
	var value=$("#"+id).val();
	//检验是否为空
	if(!value){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>验证码不能为空!</label>';
		return false;
	}
	//验证码长度不为4必定错误
	if(value.length!=4){
		document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>验证码长度不对!</label>';
		return false;
	}
	//是否注册校验
	$.ajax({
		url:"/knife-and-sword/UserServlet", 
		data:{method:"ajaxValidateVerifyCode",VerifyCode:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				document.getElementById(id+"Error").innerHTML='<label class="errorWarn" ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>码错误</label>';
				return false;
			}
		}
	});
	return true;
}