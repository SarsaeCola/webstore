package kas.admin.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import kas.order.domain.Order;
import kas.order.service.OrderService;
import kas.page.domain.PageBean;
import kas.user.domain.User;

/** 
* @Author 黄子良
* @Time 2020年5月30日 下午4:07:45 
* Description:
*/
public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService=new OrderService();
	
	/**
	 * 展示我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到pageBean
		PageBean<Order> pageBean=orderService.findAll(presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/adminJsps/content/order/list.jsp";
	}
	
	/**
	 * 展示我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByStatus(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到status
		int status=Integer.parseInt(request.getParameter("status"));
		//得到pageBean
		PageBean<Order> pageBean=orderService.findByStatus(status,presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/adminJsps/content/order/list.jsp";
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
		return "f:/adminJsps/content/order/description.jsp";
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
			return "f:/adminJsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);//设置状态为交易取消
		request.setAttribute("code", "success");
		request.setAttribute("msg", "下次遇到喜欢的东西就买了吧，毕竟不会有人喜欢你了");
		return "f:/adminJsps/msg.jsp";
		
	}
	
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deliver(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		String oid=request.getParameter("oid");
		int status=orderService.findStatus(oid);
		if(status!=2) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "订单状态异常，发货失败");
			return "f:/adminJsps/msg.jsp";
		}
		orderService.updateStatus(oid,3);//设置状态为已经发货
		request.setAttribute("code", "success");
		request.setAttribute("msg", "您的订单已经发货，请及时收货");
		return "f:/adminJsps/msg.jsp";
		
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
