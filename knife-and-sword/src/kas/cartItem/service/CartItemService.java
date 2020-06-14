package kas.cartItem.service;
/** 
* @Author 黄子良
* @Time 2020年5月19日 下午11:20:11 
* Description:
*/

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import kas.cartItem.dao.CartItemDao;
import kas.cartItem.domain.CartItem;

public class CartItemService {
	private CartItemDao cartItemDao=new CartItemDao();
	
	public List<CartItem> myCart(String uid){
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载多个条目
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds){
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 批量删除
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加购物车条目
	 * @param cartItem
	 */
	public void add(CartItem cartItem) {
		try {//查询是否已经存在
			CartItem _cartItem=cartItemDao.findByUidAndWid(
					cartItem.getUser().getUid(),
					cartItem.getWeapon().getWid());
			if(_cartItem==null) {//没有就添加
				cartItem.setIid(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			}else {//有就修改数量
				int quantity=cartItem.getQuantity()+_cartItem.getQuantity();
				cartItemDao.updateQuantity(_cartItem.getIid(), quantity);
				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 修改条目数量
	 * @param iid
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String iid,int quantity) {
		try {
			cartItemDao.updateQuantity(iid, quantity);
			return cartItemDao.findByCartItemId(iid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
