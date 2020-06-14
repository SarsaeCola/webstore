package kas.admin.admin.dao;
/** 
* @Author 黄子良
* @Time 2020年5月28日 上午8:56:04 
* Description:
*/

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;
import kas.admin.admin.domain.Admin;

public class AdminDao {
	private QueryRunner qr=new TxQueryRunner();
	
	/**
	 * 管理员账号查询
	 * @param adminName
	 * @param adminPassword
	 * @return
	 * @throws SQLException
	 */
	public Admin find(String adminName,String adminPassword) throws SQLException {
		String sql="select * from admin where adminName=? and adminPassword=?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class),adminName,adminPassword);
	}
}
