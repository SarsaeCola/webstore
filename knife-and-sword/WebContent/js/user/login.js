$(function() {
	
	$("#loginForm").submit(function(){
		$("#msg").text("");
		var bool = true;
		$(".input").each(function() {
			var inputName = $(this).attr("name");
			if(!invokeValidateFunction(inputName)) {
				bool = false;
			}
		});
		return bool;
	});
	/*
	 * 输入框得到焦点时隐藏错误信息
	 */

	$(".input").focus(function() {
		var inputName = $(this).attr("name");
		$("#" + inputName + "Error").css("display", "none");
	});
	
	/*
	 * 4. 输入框推动焦点时进行校验
	 */
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidateFunction(inputName);
	});
});


/*
 * 输入input名称，调用对应的validate方法。
 * 例如input名称为：userName，那么调用validateuserName()方法。
 */
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");	
}

/**
 * 更换验证码图片
 */
function changeImg() {
	$("#vCode").attr("src","/knife-and-sword/VerifyCodeServlet?a=" + new Date().getTime());
}

/*
 * 校验登录名
 */
function validateUserName() {
	var bool = true;
	$("#userNameError").css("display", "none");
	var value = $("#userName").val();
	if(!value) {// 非空校验
		$("#userNameError").css("display", "");
		$("#userNameError").text("用户名不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#userNameError").css("display", "");
		$("#userNameError").text("用户名长度必须在3 ~ 20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验密码
 */
function validateUserPassword() {
	var bool = true;
	$("#userPasswordError").css("display", "none");
	var value = $("#userPassword").val();
	if(!value) {// 非空校验
		$("#userPasswordError").css("display", "");
		$("#userPasswordError").text("密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#userPasswordError").css("display", "");
		$("#userPasswordError").text("密码长度必须在3 ~ 20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验验证码
 */
function validateVerifyCode() {
	var bool = true;
	$("#VerifyCodeError").css("display", "none");
	var value = $("#VerifyCode").val();
	if(!value) {//非空校验
		$("#VerifyCodeError").css("display", "");
		$("#VerifyCodeError").text("验证码不能为空！");
		bool = false;
	} else if(value.length != 4) {//长度不为4就是错误的
		$("#VerifyCodeError").css("display", "");
		$("#VerifyCodeError").text("错误的验证码！");
		bool = false;
	} else {//验证码是否正确
		$.ajax({
			url: "/knife-and-sword/UserServlet",
			data: {method: "ajaxValidateVerifyCode", VerifyCode: value},
			cache: false,
			async: false,
			type: "POST",
			dataType: "json",
			success: function(flag) {
				if(!flag) {
					$("#VerifyCodeError").css("display", "");
					$("#VerifyCodeError").text("错误的验证码！");
					bool = false;					
				}
			}
		});
	}
	return bool;
}

