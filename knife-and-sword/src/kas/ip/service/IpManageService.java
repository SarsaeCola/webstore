package kas.ip.service;
/** 
* @Author 黄子良
* @Time 2020年6月11日 下午4:17:57 
* Description:
*/

import java.sql.SQLException;

import kas.ip.dao.IpManageDao;
import kas.ip.domain.IpManage;

public class IpManageService {
private IpManageDao ipManageDao=new IpManageDao();

public void insertIpUser(IpManage ip) {
	try {
		ipManageDao.insertIpUser(ip);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}

public void insertIpAdmin(IpManage ip) {
	try {
		ipManageDao.insertIpAdmin(ip);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}

public IpManage findLastIpUser(String uid,int status) {
	try {
		return ipManageDao.findLastIpUser(uid, status);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}

public IpManage findLastIpAdmin(String aid,int status) {
	try {
		return ipManageDao.findLastIpAdmin(aid, status);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
}
