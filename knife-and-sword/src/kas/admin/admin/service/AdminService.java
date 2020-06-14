package kas.admin.admin.service;
/** 
* @Author 黄子良
* @Time 2020年5月28日 上午8:56:15 
* Description:
*/

import java.sql.SQLException;

import kas.admin.admin.dao.AdminDao;
import kas.admin.admin.domain.Admin;

public class AdminService {
	private AdminDao adminDao=new AdminDao();
	
	public Admin login(Admin admin) {
		try {
			return adminDao.find(admin.getAdminName(), admin.getAdminPassword());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
