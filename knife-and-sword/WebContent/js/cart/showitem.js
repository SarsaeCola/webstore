	//计算合计
	$(function() {
		var total = 0;
		$(".subtotal").each(function() {
			total += Number($(this).text());
		});
		$("#total").text(round(total, 2));
		
		$("#form1").submit(function(){
			var bool = true;
		    if(!validateAddr()) {
					bool = false;
				}
			return bool;
		});
		/*
		 * 输入框得到焦点时隐藏错误信息
		 */

		$("#addr").focus(function() {
			$("#addrError").css("display", "none");
		});
		
		/*
		 * 4. 输入框推动焦点时进行校验
		 */
		$("#addr").blur(function() {
			eval("validateAddr()");
		});
	});
	
	function validateAddr() {
		var bool = true;
		$("#addrError").css("display", "none");
		var value = $("#addr").val();
		if(!value) {// 非空校验
			$("#addrError").css("display", "");
			$("#addrError").text("地址不能为空！");
			bool = false;
		} 
		return bool;
	}