package kas.order.domain;
/** 
* @Author 黄子良
* @Time 2020年5月25日 下午5:21:47 
* Description:订单条目类
*/

import kas.weapon.domain.Weapon;

public class OrderItem {
	private String oiid;
	private int quantity;
	private double subtotal;
	private Weapon weapon;
	private Order order;//所属的订单
	public String getOiid() {
		return oiid;
	}
	public void setOiid(String oiid) {
		this.oiid = oiid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
