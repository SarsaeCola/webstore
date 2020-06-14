package kas.cartItem.dao;
/** 
* @Author 黄子良
* @Time 2020年5月19日 下午11:19:57 
* Description:
*/

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import kas.cartItem.domain.CartItem;
import kas.user.domain.User;
import kas.weapon.domain.Weapon;

public class CartItemDao {
	private QueryRunner qr=new TxQueryRunner();
	
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException{
		//把cartItemIds转换成数组
		Object[] cartItemIdArray=cartItemIds.split(",");
		String WhereSql=toWhereSql(cartItemIdArray.length);
		
		//查询
		String sql="select * from cartItem c,weapon w where c.wid=w.wid and "+WhereSql;
		return toCartItemList(qr.query(sql, new MapListHandler(),cartItemIdArray));
	}
	public List<CartItem> findByUser(String uid) throws SQLException{
		String sql="select * from cartItem c, weapon w where c.wid=w.wid and uid=? order by c.orderBy";
		List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler(),uid);
		return toCartItemList(mapList);
	}
	/**
	 * 批量删除条目
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public void batchDelete(String cartItemIds) throws SQLException {
		Object[] cartItemIdArray=cartItemIds.split(",");
		String WhereSql=toWhereSql(cartItemIdArray.length);
		String sql="delete from cartItem where "+WhereSql;
		qr.update(sql,cartItemIdArray);
	}
	/**
	 * 增加购物车条目
	 * @param cartItem
	 * @throws SQLException
	 */
	public void addCartItem(CartItem cartItem) throws SQLException {
		String sql="insert into cartItem(iid,quantity,wid,uid)"+
					"values(?,?,?,?)";
		Object[] params= {cartItem.getIid(),cartItem.getQuantity(),
				cartItem.getWeapon().getWid(),cartItem.getUser().getUid()};
		qr.update(sql,params);
	}
	/**
	 * 更新指定购物车条目
	 * @param iid
	 * @param quantity
	 * @throws SQLException
	 */
	public void updateQuantity(String iid,int quantity) throws SQLException {
		String sql="update cartItem set quantity=? where iid=?";
		qr.update(sql,quantity,iid);
	}
	/**
	 * 查询用户特定的武器的购物车条目是否存在
	 * @param uid
	 * @param wid
	 * @return
	 * @throws SQLException 
	 */
	public CartItem findByUidAndWid(String uid,String wid) throws SQLException {
		String sql="select * from cartItem where uid=? and wid=?";
		Map<String, Object>map=qr.query(sql, new MapHandler(),uid,wid);
		CartItem cartItem=toCartItem(map);
		return cartItem;
	}
	/**
	 * 按照条目id查找并返回CartItem
	 * @param iid
	 * @return
	 * @throws SQLException
	 */
	public CartItem findByCartItemId(String iid) throws SQLException {
		String sql="select * from cartItem c,weapon w where c.wid=w.wid and c.iid=?";
		Map<String,Object>map=qr.query(sql, new MapHandler(),iid);
		return toCartItem(map);
	}
	/**
	 * 把map映射成CartItem
	 * @param map
	 * @return
	 */
	private CartItem toCartItem(Map<String,Object> map) {
		if(map==null||map.size()==0) return null;
		CartItem cartItem=CommonUtils.toBean(map, CartItem.class);
		Weapon weapon=CommonUtils.toBean(map, Weapon.class);
		User user=CommonUtils.toBean(map,User.class);
		cartItem.setUser(user);
		cartItem.setWeapon(weapon);
		cartItem.setSubtotal(cartItem.getSubtotal());
		return cartItem;
	}
	
	private List<CartItem> toCartItemList(List<Map<String, Object>> mapList){
		List<CartItem> cartItemList=new ArrayList<CartItem>();
		for(Map<String,Object> map:mapList) {
			CartItem cartItem=toCartItem(map);
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}
	/**
	 * 生成where子句
	 * @param length
	 * @return
	 */
	private String toWhereSql(int length) {
		StringBuilder sb=new StringBuilder("iid in(");
		for(int i=0;i<length;i++) {
			sb.append("?");
			if(i<length-1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
}
