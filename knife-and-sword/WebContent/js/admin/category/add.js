function checkForm() {
	if ($("#cname").val()==""||$("#cname").val()==null) {
		alert("分类名不能为空！");
		return false;
	}
	if ($("#description").val()==""||$("#description").val()==null) {
		alert("分类描述不能为空！");
		return false;
	}
	return true;
}