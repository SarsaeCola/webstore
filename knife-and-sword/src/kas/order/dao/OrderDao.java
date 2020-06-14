package kas.order.dao;
/** 
* @Author 黄子良
* @Time 2020年5月25日 下午5:23:17 
* Description:
*/

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import kas.order.domain.Order;
import kas.order.domain.OrderItem;
import kas.page.domain.Expression;
import kas.page.domain.PageBean;
import kas.page.domain.PageConstances;
import kas.weapon.domain.Weapon;

public class OrderDao {
	private QueryRunner qr=new TxQueryRunner();
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String oid,int status) throws SQLException {
		String sql="update `order` set status=? where oid=?";
		qr.update(sql, status,oid);
	}
	/**
 * 查询订单状态
 * @param oid
 * @return
	 * @throws SQLException 
 */
	public int findStatus(String oid) throws SQLException {
		String sql="select status from `order` where oid=?";
		Number number=Integer.parseInt( qr.query(sql, new ScalarHandler(),oid).toString());
		return number.intValue();
	}
	public void add(Order order) throws SQLException {
		//插入订单
		String sql="insert into `order` (oid,orderTime,total,status,address,uid) "
				+ "values (?,?,?,?,?,?)";
		Object[] params= {order.getOid(),order.getOrderTime(),order.getTotal(),
				order.getStatus(),order.getAddress(),order.getOwner().getUid()};
		qr.update(sql,params);
		//插入订单条目
		sql="insert into orderItem (oiid,quantity,subtotal,wid,wname,currentPrice,simg,oid)"
				+ "values (?,?,?,?,?,?,?,?)";
		int length=order.getOrderItemList().size();
		Object[][] itemParams=new Object[length][];
		for(int i=0;i<length;i++) {
			OrderItem item=order.getOrderItemList().get(i);
			itemParams[i]=new Object[] {item.getOiid(),item.getQuantity(),item.getSubtotal(),
					item.getWeapon().getWid(),item.getWeapon().getWname(),item.getWeapon().getCurrentPrice(),
					item.getWeapon().getSimg(),order.getOid()};
			}
		qr.batch(sql, itemParams);
		}
/**
 * 加载订单
 * @param oid
 * @return
 * @throws SQLException
 */
	public Order load(String oid) throws SQLException {
		String sql="select * from `order` where oid=?";
		Order order=qr.query(sql,new BeanHandler<Order>(Order.class),oid);
		loadOrderItem(order);
		return order;
	}
	/**
	 * 按状态查询用户的订单
	 * @param uid
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByStatus(int status,int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		expressions.add(new Expression("status", "=",status+""));
		return findByCriteria(expressions, presentPageNumber);
	}
	
	/**
	 * 查询用户的订单
	 * @param uid
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid,int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		expressions.add(new Expression("uid", "=",uid));
		return findByCriteria(expressions, presentPageNumber);
	}
	
	/**
	 * 查询所有
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findAll(int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		return findByCriteria(expressions, presentPageNumber);
	}
	

	/**
	 * 通用查询方法
	 * @param expList
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException 
	 */
	private PageBean<Order> findByCriteria(List<Expression> expList,int presentPageNumber) throws SQLException{
		//得到每页记录数
		int pageRecord=PageConstances.ORDER_PAGE_SIZE;
		/**
		 * 得到总记录数
		 */
		 //通过expression生成select语句
		StringBuilder whereSql=new StringBuilder(" where 1=1");
		List<Object> params=new ArrayList<Object>();//对应sql语句中的占位符
		for(Expression exp:expList) {
			/*
			 * 添加查询条件
			 */
			whereSql.append(" and ").append(exp.getName())
			.append(" ").append(exp.getOperator())
			.append(" ");
			if(!exp.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(exp.getValue());
			}
		}
		String sql="select count(*) from `order`"+whereSql;
		Number number=(Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int totalRecord=number.intValue();//得到总记录数
		/*
		 * 得到BeanList
		 */
		sql="select * from `order`"+whereSql+" order by orderTime desc limit ?,?";
		params.add((presentPageNumber-1)*pageRecord);//计算当前页首行记录的下标
		params.add(pageRecord);
		List<Order> beanList=qr.query(sql, new BeanListHandler<Order>(Order.class),params.toArray());
		
		/*
		 *为每个订单加载其条例 
		 */
		for(Order order:beanList) {
			loadOrderItem(order);
		}
		
		/*
		 * 创建PageBean
		 * 缺失的url由servlet提供
		 */
		PageBean<Order> pageBean=new PageBean<Order>();
		pageBean.setBeanList(beanList);
		pageBean.setPresentPageNumber(presentPageNumber);
		pageBean.setPageRecord(pageRecord);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setTotalPage(pageBean.getTotalPage());
		
		return pageBean;
	}

	/**
	 * 为order加载条目
	 * @param order
	 * @throws SQLException 
	 */
	private void loadOrderItem(Order order) throws SQLException {
		String sql="select * from orderItem where oid=?";
		List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem> orderItemList=toOrderItemList(mapList);	
		order.setOrderItemList(orderItemList);
	}

	/**
	 * 把List<Map>象转化为List<OrderItem>对象
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		for(Map<String,Object> map:mapList) {
			OrderItem orderItem=toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	/**
	 * 把map转换成OrderItem
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem=CommonUtils.toBean(map, OrderItem.class);
		Weapon weapon=CommonUtils.toBean(map, Weapon.class);
		orderItem.setWeapon(weapon);
		return orderItem;
	}
	
	

}
