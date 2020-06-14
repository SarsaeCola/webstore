package kas.cartItem.domain;

import java.math.BigDecimal;

import kas.user.domain.User;
import kas.weapon.domain.Weapon;

/** 
* @Author 黄子良
* @Time 2020年5月19日 下午11:19:02 
* Description:
*/
public class CartItem {
	private String iid;//主键
	private int quantity;//数量
	private Weapon weapon;//武器
	private User user;//所属用户
	private double subtotal;
	
	//返回单一物品的总计
	public double getSubtotal() {
		BigDecimal currentPrice=new BigDecimal(weapon.getCurrentPrice()+"");
		BigDecimal number=new BigDecimal(quantity+"");
		BigDecimal result=currentPrice.multiply(number);
		return result.doubleValue();
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	


	
}
