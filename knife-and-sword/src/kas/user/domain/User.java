package kas.user.domain;

/** 
* @Author 黄子良
* @Time 2020年5月13日 下午3:49:46 
* Description:用户实体类
* 将user表的数据，网页表单数据封装成对象 
*/
public class User {
	private String uid;//用户id
	private String userName;//用户名
	private String userPassword;//用户密码
	private String email;//邮箱
	private String activecode;//激活码
	private boolean status;//状态
	
	//表单数据
	private String newPassword;
	private String repeatPassword;
	private String VerifyCode;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActivecode() {
		return activecode;
	}
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public String getVerifyCode() {
		return VerifyCode;
	}
	public void setVerifyCode(String VerifyCode) {
		this.VerifyCode = VerifyCode;
	}
	
	@Override
	public String toString() {
		return "User [uid=" + uid + ", userName=" + userName + ", userPassword=" + userPassword + ", email=" + email
				+ ", activecode=" + activecode + ", status=" + status + ", newPassword=" + newPassword
				+ ", repeatPassword=" + repeatPassword + ", VerifyCode=" + VerifyCode + "]";
	}
}
