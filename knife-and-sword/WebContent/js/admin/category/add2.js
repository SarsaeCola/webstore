function checkForm() {
	if (!$("#cname").val()) {
		alert("分类名不能为空！");
		return false;
	}
	if (!$("#pid").val()) {
		alert("一级分类不能为空！");
		return false;
	}
	if (!$("#description").val()) {
		alert("分类描述不能为空！");
		return false;
	}
	return true;
}