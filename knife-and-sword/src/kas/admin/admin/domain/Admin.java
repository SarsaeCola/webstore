package kas.admin.admin.domain;
/** 
* @Author 黄子良
* @Time 2020年5月28日 上午8:54:16 
* Description:
*/
public class Admin {
	private String aid;
	private String adminName;
	private String adminPassword;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
}
