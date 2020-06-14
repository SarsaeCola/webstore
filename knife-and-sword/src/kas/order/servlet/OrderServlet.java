package kas.order.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import kas.cartItem.domain.CartItem;
import kas.cartItem.service.CartItemService;
import kas.order.domain.Order;
import kas.order.domain.OrderItem;
import kas.order.service.OrderService;
import kas.page.domain.PageBean;
import kas.user.domain.User;

/** 
* @Author 黄子良
* @Time 2020年5月25日 下午5:23:59 
* Description:
*/
public class OrderServlet extends BaseServlet {
	private OrderService orderService=new OrderService();
	private CartItemService cartItemService=new CartItemService();
	
	/**
	 * 支付方法
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String payment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid=req.getParameter("oid");//订单编码
		orderService.updateStatus(oid, 2);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "支付成功");
		return "f:/jsp/main/msg.jsp";
	}
	public String createOrder(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		//获取购物车条目的id
		String cartItemIds=request.getParameter("cartItemIds");
		List<CartItem> cartItemList=cartItemService.loadCartItems(cartItemIds);
		//创建订单
		Order order=new Order();
		order.setOid(CommonUtils.uuid());//主键
		order.setOrderTime(String.format("%tF %<tT", new Date()));//时间
		order.setStatus(1);//状态
		order.setAddress(request.getParameter("address"));//地址
		User owner=(User)request.getSession().getAttribute("sessionUser");
		order.setOwner(owner);//所有者
		
		BigDecimal total=new BigDecimal("0");
		for(CartItem cartItem:cartItemList) {
			total=total.add(new BigDecimal(cartItem.getSubtotal()+""));
		}
		order.setTotal(total.doubleValue());//总计
		
		//创建List<OrderItem>
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		for(CartItem cartItem:cartItemList) {
			OrderItem orderItem=new OrderItem();
			orderItem.setOiid(CommonUtils.uuid());//设置主键
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setWeapon(cartItem.getWeapon());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		
		//调用service
		orderService.createOrder(order);
		
		//删除购物车条目
		cartItemService.batchDelete(cartItemIds);
		//保存订单，并转发
		request.setAttribute("order", order);
		return "f:/jsp/order/success.jsp";
	}
	
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		String oid=request.getParameter("oid");
		int status=orderService.findStatus(oid);
		if(status!=1) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "不允许的操作");
			return "f:/jsp/main/msg.jsp";
		}
		orderService.updateStatus(oid, 5);//设置状态为交易取消
		request.setAttribute("code", "success");
		request.setAttribute("msg", "下次遇到喜欢的东西就买了吧，毕竟不会有人喜欢你了");
		return "f:/jsp/main/msg.jsp";
		
	}
	
	/**
	 * 确认收货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		String oid=request.getParameter("oid");
		int status=orderService.findStatus(oid);
		if(status!=3) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "你当前的操作状态异常，该操作不允许");
			return "f:/jsp/main/msg.jsp";
		}
		orderService.updateStatus(oid, 4);//设置状态为交易取消
		request.setAttribute("code", "success");
		request.setAttribute("msg", "有些东西该买还是得买，买了就是赚了");
		return "f:/jsp/main/msg.jsp";
		
	}
	/**
	 * 加载订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		String oid=request.getParameter("oid");
		Order order=orderService.load(oid);
		request.setAttribute("order", order);
		String btn=request.getParameter("btn");
		request.setAttribute("btn", btn);
		return "f:/jsp/order/description.jsp";
	}
	
	public String paymentPre(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		request.setAttribute("order", orderService.load(request.getParameter("oid")));
		return "f:/jsp/order/pay.jsp";
	}
	/**
	 * 展示我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到pageBean
		User user=(User) request.getSession().getAttribute("sessionUser");
		PageBean<Order> pageBean=orderService.myOrders(user.getUid(), presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/jsp/order/list.jsp";
	}
	
	/**
	 * 构造url
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request) {
		String url=request.getRequestURI()+"?"+request.getQueryString();
		int index=url.lastIndexOf("&presentPageNumber=");
		if(index!=-1) {
			url=url.substring(0,index);
		}
		return url;
	}
	
	
	/**
	 * 获取当前页码
	 * @param request
	 * @return
	 */
	private int getPresentPageNumber(HttpServletRequest request) {
		int presentPageNumber=1;
		String param=request.getParameter("presentPageNumber");
		if(param!=null&&!param.trim().isEmpty()) {
			try {
				presentPageNumber=Integer.parseInt(param);
			} catch (Exception e) {
				throw new RuntimeException();
			}
			
		}
		return presentPageNumber;
	}
}
