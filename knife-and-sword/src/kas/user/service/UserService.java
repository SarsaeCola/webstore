package kas.user.service;
/** 
* @Author 黄子良
* @Time 2020年5月13日 下午3:47:55 
* Description:
* 用户模块业务层
*/

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.management.RuntimeErrorException;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import kas.user.dao.UserDao;
import kas.user.domain.User;

public class UserService {
	private UserDao userDao=new UserDao();
	/**
	 * 校验用户名是否已经被注册
	 * @param userName
	 * @return boolean
	 * @throws SQLException
	 */
		public boolean ajaxValidateUserName(String userName) {
			try {
				return userDao.ajaxValidateUserName(userName);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * 校验邮箱是否已经被注册
		 * @param email
		 * @return boolean
		 * @throws SQLException
		 */
		public boolean ajaxValidateEmail(String email) {
			try {
				return userDao.ajaxValidateEmail(email);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		/**
		 * 注册功能
		 * @param user
		 */
	public void regist(User user) {
		/**
		 * 补全数据
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivecode(CommonUtils.uuid()+CommonUtils.uuid());
		/*
	     * 插入数据库	
		 */
		try {
			userDao.add(user);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		/*
		 * 发邮箱
		 */
		Properties properties=new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String host=properties.getProperty("host");
		String emailName=properties.getProperty("username");
		String emailPwd=properties.getProperty("password");
		Session session=MailUtils.createSession(host, emailName, emailPwd);
		//创建Mail对象
		String from=properties.getProperty("from");
		String to=user.getEmail();
		String subject=properties.getProperty("subject");
		String content=MessageFormat.format(properties.getProperty("content"),user.getActivecode());
		Mail mail=new Mail(from,to,subject,content);
		//发送邮件
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void activation(String code) throws UserException {
		/*
		 * 通过激活码激活用户，没有该用户就抛出异常；
		 * 有该用户则进一步检测用户状态，已经激活则抛出异常；
		 * 最后激活该用户
		 */
		try {
			User user=userDao.findByCode(code);
			if(user==null) throw new UserException("无效的激活码");
			if(user.isStatus()) throw new UserException("你已经激活激活过了，不要二次激活！");
			userDao.updateStatus(user.getUid(), true);//修改状态
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User login(User user) {
		try {
			return userDao.findByNameAndPwd(user.getUserName(), user.getUserPassword());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 更新密码
	 * @param uid
	 * @param newPwd
	 * @param oldPwd
	 * @throws UserException
	 */
	public void updatePassword(String uid,String newPwd,String oldPwd) throws UserException {
		try {
			boolean bool=userDao.findByUidAndPwd(uid, oldPwd);
			if(!bool) {
				throw new UserException("旧密码错误，失败的认证");
			}
			userDao.updatePassword(uid, newPwd);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
