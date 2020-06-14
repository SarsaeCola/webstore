package kas.user.dao;
/** 
* @Author 黄子良
* @Time 2020年5月13日 下午3:45:55 
* Description:
* 用户模块持久层
*/

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import kas.user.domain.User;

public class UserDao {
	private QueryRunner qr=new TxQueryRunner();
/**
 * 校验用户名是否已经被注册
 * @param userName
 * @return boolean
 * @throws SQLException
 */
	public boolean ajaxValidateUserName(String userName) throws SQLException {
		String sql="select count(*) from user where userName=?";
		Number number=(Number)qr.query(sql, new ScalarHandler(),userName);
		return number.intValue()==0;
	}
	
	/**
	 * 校验邮箱是否已经被注册
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql="select count(*) from user where email=?";
		Number number=(Number)qr.query(sql, new ScalarHandler(),email);
		return number.intValue()==0;
	}
	/**
	 * 新增用户
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) throws SQLException {
		String sql="insert into user values(?,?,?,?,?,?)";
		Object[] params= {user.getUid(),user.getUserName(),user.getUserPassword(),user.getEmail(),user.getActivecode(),user.isStatus()};
		qr.update(sql,params);
	}
	/**
	 * 通过激活码查找用户
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public User findByCode(String code) throws SQLException {
		String sql ="select * from user where activeCode=?";
		return qr.query(sql, new BeanHandler<User>(User.class),code);
	}
	/**
	 * 激活特定用户
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String uid,boolean status) throws SQLException {
		String sql="update user set status=? where uid=?";
		qr.update(sql,status,uid);
	}
	
	/*
	 * 用用户名和密码查询用户
	 */
	public User findByNameAndPwd(String name,String pwd) throws SQLException {
		String sql="select * from user where userName=? and userPassword=?";
		return qr.query(sql, new BeanHandler<User>(User.class),name,pwd);
	}
	/*
	 * 按uid和password查询用户存在性
	 */
	public boolean findByUidAndPwd(String uid,String pwd) throws SQLException {
		String sql="select count(*) from user where uid=? and userPassword=?";
		Number number=(Number)qr.query(sql, new ScalarHandler(),uid,pwd);
		return number.intValue()>0;
		
	}
	/*
	 * 修改密码
	 */
	public void updatePassword(String uid,String password) throws SQLException {
		String sql="update user set userPassword=? where uid=?";
		qr.update(sql, password,uid);
	}
}
