package kas.cartItem.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import kas.cartItem.domain.CartItem;
import kas.cartItem.service.CartItemService;
import kas.user.domain.User;
import kas.weapon.domain.Weapon;

/** 
* @Author 黄子良
* @Time 2020年5月19日 下午11:20:32 
* Description:
*/
public class CartItemServlet extends BaseServlet {
	private CartItemService cartItemService=new CartItemService();
	
	/**
	 * 购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myCart(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		User user=(User)req.getSession().getAttribute("sessionUser");
		String uid=user.getUid();
		//得到当前用户有的购物车
		List<CartItem> cartItemList=cartItemService.myCart(uid);
		//转发结果
		req.setAttribute("cartItemList", cartItemList);
		return "f:/jsp/cart/list.jsp";
	}
	
	/**
	 * 加载多个cartItem
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		//获取参数
		String cartItemIds=req.getParameter("cartItemIds");
		double total=Double.parseDouble(req.getParameter("total"));
		//获取List<CartItem>
		List<CartItem> cartItemsList=cartItemService.loadCartItems(cartItemIds);
		
		//保存并转发
		req.setAttribute("cartItemList", cartItemsList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsp/cart/showitem.jsp";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//封装成购物车条目对象
		Map map=req.getParameterMap();
		CartItem cartItem=CommonUtils.toBean(map, CartItem.class);
		Weapon weapon=CommonUtils.toBean(map, Weapon.class);
		User user=(User) req.getSession().getAttribute("sessionUser");
		cartItem.setUser(user);
		cartItem.setWeapon(weapon);
		cartItemService.add(cartItem);
		//查询当前用户的所有条目（刷新）
		return myCart(req, resp);
	}
	/**
	 * 批量删除条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取cartItemIds参数
		String cartItemIds=req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		return myCart(req, resp);
	}
	
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		String cartItemId=req.getParameter("cartItemId");
		int quantity=Integer.parseInt(req.getParameter("quantity"));
		CartItem cartItem=cartItemService.updateQuantity(cartItemId, quantity);
		
		//给客户端返回一个json对象
		StringBuilder sb=new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		
		resp.getWriter().print(sb);
		return null;
	}
}
