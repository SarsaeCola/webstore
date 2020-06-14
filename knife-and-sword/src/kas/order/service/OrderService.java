package kas.order.service;
/** 
* @Author 黄子良
* @Time 2020年5月25日 下午5:23:32 
* Description:
*/

import java.sql.SQLException;

import cn.itcast.jdbc.JdbcUtils;
import kas.order.dao.OrderDao;
import kas.order.domain.Order;
import kas.page.domain.PageBean;

public class OrderService {
	private OrderDao orderDao=new OrderDao();
	
	/**
	 * 变更订单状态
	 * @param oid
	 * @param status
	 */
	public void updateStatus(String oid,int status) {
		try {
			orderDao.updateStatus(oid, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 生成订单
	 * @param order
	 */
	public void createOrder(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
	try {
		JdbcUtils.beginTransaction();
		Order order=orderDao.load(oid);
		JdbcUtils.commitTransaction();
		return order;
	} catch (SQLException e) {
		try {
			JdbcUtils.rollbackTransaction();
		} catch (SQLException e2) {}
		throw new RuntimeException(e);
	}
	}
	
	/**
	 * 我的订单
	 * @param uid
	 * @param presentPageNumber
	 * @return
	 */
	public PageBean<Order> myOrders(String uid,int presentPageNumber){
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb=orderDao.findByUser(uid, presentPageNumber);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e2) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询所有
	 * @param presentPageNumber
	 * @return
	 */
	public PageBean<Order> findAll(int presentPageNumber){
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb=orderDao.findAll(presentPageNumber);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e2) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按状态查询订单
	 * @param uid
	 * @param presentPageNumber
	 * @return
	 */
	public PageBean<Order> findByStatus(int status,int presentPageNumber){
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb=orderDao.findByStatus(status, presentPageNumber);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e2) {}
			throw new RuntimeException(e);
		}
	}
}
