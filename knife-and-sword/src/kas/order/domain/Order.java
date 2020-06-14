package kas.order.domain;

import java.util.List;

import kas.user.domain.User;

/** 
* @Author 黄子良
* @Time 2020年5月25日 下午5:21:59 
* Description:订单类,与订单条目类关联
*/
public class Order {
	private String oid;
	private String orderTime;//订单生成时间
	private double total;
	private int status;//订单状态：1。未付款，2.已付款未发货，3.已发货未确认收货，4.确认收货交易成功。5.已取消
	private String address;
	private User owner;//订单所有者
	private List<OrderItem> orderItemList;

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
