package kas.user.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import kas.ip.domain.IPUtil;
import kas.ip.domain.IpManage;
import kas.ip.service.IpManageService;
import kas.user.domain.User;
import kas.user.service.UserException;
import kas.user.service.UserService;

/** 
* @Author 黄子良
* @Time 2020年5月13日 下午3:49:46 
* Description:用户模块控制层
*/
public class UserServlet extends BaseServlet {
	private UserService userService=new UserService();
	private IpManageService ipManageService=new IpManageService();
	/**
	 * 用户名是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateUserName(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			String userName=req.getParameter("userName");
			boolean b=userService.ajaxValidateUserName(userName);
			resp.getWriter().print(b);
			return null;
	}
	/**
	 * 邮箱是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String email=req.getParameter("email");
		boolean b=userService.ajaxValidateEmail(email);
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * 验证码校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String verifyCode=req.getParameter("VerifyCode");
		String vcode=(String) req.getSession().getAttribute("vCode");
		boolean b=verifyCode.equalsIgnoreCase(vcode);
		resp.getWriter().print(b);
		return null;
	}
	
	public String regist(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		//封装表单数据成User对象
  	    User formUser=CommonUtils.toBean(req.getParameterMap(), User.class);
		//校验
  	    Map<String,String> errors=validateRegist(formUser,req.getSession());
		System.out.println(errors.toString());
		if(errors.size()>0) {
			req.setAttribute("form",formUser);
			req.setAttribute("errors", errors);
			return "f:/jsp/user/register.jsp";
		}
		//service完成业务
		userService.regist(formUser);
		//保存成功信息，转发到main/msg.jsp
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请到邮箱激活");
		return "f:/jsp/main/msg.jsp";
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
			//封装表单数据
			User formuser=CommonUtils.toBean(request.getParameterMap(), User.class);
			//校验
	  	    Map<String,String> errors=validateLogin(formuser,request.getSession());
			if(errors.size()>0) {
				request.setAttribute("form",formuser);
				request.setAttribute("errors", errors);
				return "f:/jsp/user/login.jsp";
			}
			//调用service的login方法
			User user=userService.login(formuser);
			//开始判断
			if(user==null){
				request.setAttribute("msg", "用户名或者密码错误");
				request.setAttribute("user", formuser);
				return "f:/jsp/user/login.jsp";
			}else {
				if(!user.isStatus()) {
					request.setAttribute("msg", "账户尚未激活");
					request.setAttribute("user", formuser);
					return "f:/jsp/user/login.jsp";
				}else {//保存成功的用户到session和cookie
					request.getSession().setAttribute("sessionUser", user);
					String userName=user.getUserName();
					userName =URLEncoder.encode(userName,"utf-8");
					Cookie cookie=new Cookie("userName",userName);
					cookie.setMaxAge(60*60*24*5);//保存5天
					response.addCookie(cookie);
					IpManage ipManage=new IpManage();
					String ip=IPUtil.getIpAddress(request);//获取登陆的ip地址
					Timestamp timeStamp=new Timestamp(System.currentTimeMillis());//获取时间戳
					ipManage.setId(CommonUtils.uuid());
					ipManage.setIp(ip);
					ipManage.setUid(user.getUid());
					ipManage.setTime(timeStamp);
					ipManage.setStatus(1);//设置状态为登陆
					ipManageService.insertIpUser(ipManage);
					request.setAttribute("ipAddrUserLogin",ip);
					request.setAttribute("loginTimeUser", timeStamp.toString());
					return "f:/index.jsp";//重点向到主页
				}
			}
		}
	
	/**
	 * 退出登陆
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		User user=(User) request.getSession().getAttribute("sessionUser");
		IpManage ipManage=new IpManage();
		IpManage loginIP=ipManageService.findLastIpUser(user.getUid(), 1);
		String ip=IPUtil.getIpAddress(request);//获取登陆的ip地址
		Timestamp timeStamp=new Timestamp(System.currentTimeMillis());//获取时间戳
		ipManage.setId(CommonUtils.uuid());
		ipManage.setIp(ip);
		ipManage.setUid(user.getUid());
		ipManage.setTime(timeStamp);
		ipManage.setStatus(2);//设置状态为退出
		ipManageService.insertIpUser(ipManage);
		request.setAttribute("ipAddrUserLogout", ip);
		request.setAttribute("loginTimeUser", loginIP.getTime().toString());
		request.setAttribute("logoutTimeUser", timeStamp.toString());
		return "f:/jsp/user/login.jsp";
	}
	public String activation(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		/*
		 * 获取参数激活码
		 * 用激活码调用service类的方法激活
		 * 抛出的异常保存起来，转发到main/msg.jsp
		 */
	String code=request.getParameter("activationCode");
	try {
		userService.activation(code);
		request.setAttribute("code", "success");
	}catch (UserException e) {
		request.setAttribute("msg", e.getMessage());
		request.setAttribute("code", "error");
	}
	return "f:/jsp/main/msg.jsp";
}

	/**
	 * 校验表单的字段
	 * @param formUser
	 * @param session
	 * @return
	 */
	private Map<String,String> validateRegist(User formUser,HttpSession session){
		Map<String,String> errors=new HashMap<String,String>();
		//校验登陆名
		String userName=formUser.getUserName();
		if(userName==null||userName.trim().isEmpty()) {
			errors.put("userName", "用户名不能为空");
		}else if(userName.length()<3||userName.length()>20) {
			errors.put("userName", "用户名长度必须在3-20之间");
		}else if(!userService.ajaxValidateUserName(userName)) {
			errors.put("userName", "用户名已经被注册");
		}
		String userPassword=formUser.getUserPassword();
			if(userPassword==null||userPassword.trim().isEmpty()) {
				errors.put("userPassword", "密码不能为空");
			}else if(userPassword.length()<3||userPassword.length()>20) {
				errors.put("userPassword", "密码长度必须在3-20之间");
			}

		String repeatPassword=formUser.getRepeatPassword();
			if(repeatPassword==null||repeatPassword.trim().isEmpty()) {
				errors.put("repeatPassword", "密码不能为空");
			}else if(repeatPassword.length()<3||repeatPassword.length()>20) {
				errors.put("repeatPassword", "密码长度必须在3-20之间");
			}
			
			String email=formUser.getEmail();
			if(email==null||email.trim().isEmpty()) {
				errors.put("email", "邮箱不能为空");
			}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
				errors.put("email", "邮箱格式错误！");
			}else if(!userService.ajaxValidateEmail(email)){
				errors.put("email","邮箱已经被注册");
			}
			
			String VerifyCode=formUser.getVerifyCode();
			String vcode=(String)session.getAttribute("vCode");
			if(VerifyCode==null||VerifyCode.trim().isEmpty()) {
				errors.put("VerifyCode", "验证码不能为空");
			}else if(!VerifyCode.equalsIgnoreCase(vcode)) {
				errors.put("VerifyCode","验证码又错了");
			}
		return errors;
	}
	
	private Map<String,String> validateLogin(User formUser,HttpSession session){
		Map<String,String> errors=new HashMap<String,String>();
		return errors;
	}
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		//封装pwd.jsp的表单数据
		User formUser=CommonUtils.toBean(request.getParameterMap(), User.class);
		User user=(User)request.getSession().getAttribute("sessionUser");
		//判断用户是否登陆
		if(user==null) {
			request.setAttribute("msg", "未登录无法修改密码");
			
			return "f:/jsp/user/login.jsp";
		}
		try {
			userService.updatePassword(user.getUid(), formUser.getNewPassword(), formUser.getUserPassword());
			request.setAttribute("msg", "修改密码成功");
			request.setAttribute("code", "success");
			return "f:/jsp/main/msg.jsp";
		} catch (UserException e) {
			request.setAttribute("msg",e.getMessage());//保存异常信息
			request.setAttribute("form", formUser);
			return "f:/jsp/user/pwd.jsp";
		}
	}
}
