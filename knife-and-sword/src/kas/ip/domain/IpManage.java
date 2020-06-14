package kas.ip.domain;
/** 
* @Author 黄子良
* @Time 2020年6月11日 下午2:47:55 
* Description:ip管理类
*/

import java.sql.Timestamp;

import kas.admin.admin.domain.Admin;
import kas.user.domain.User;

public class IpManage {
private String id;
private User uid;
private Admin aid;
private String ip;
private int status;//1为登陆，2为退出
private Timestamp time;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public User getUid() {
	return uid;
}
public void setUid(String uid) {
	User user=new User();
	user.setUid(uid);
	this.uid = user;
}
public Admin getAid() {
	return aid;
}
public void setAid(String aid) {
	Admin admin=new Admin();
	admin.setAid(aid);
	this.aid = admin;
}
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public Timestamp getTime() {
	return time;
}
public void setTime(Timestamp time) {
	this.time = time;
}

}
