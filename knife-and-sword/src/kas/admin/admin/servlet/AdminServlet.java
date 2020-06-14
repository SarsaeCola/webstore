package kas.admin.admin.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import kas.admin.admin.domain.Admin;
import kas.admin.admin.service.AdminService;
import kas.ip.domain.IPUtil;
import kas.ip.domain.IpManage;
import kas.ip.service.IpManageService;

/** 
* @Author 黄子良
* @Time 2020年5月28日 上午8:57:13 
* Description:
*/
public class AdminServlet extends BaseServlet {
	private AdminService adminService=new AdminService();
	private IpManageService ipManageService=new IpManageService();
	public String login(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
			Admin form=CommonUtils.toBean(req.getParameterMap(), Admin.class);
			Admin admin=adminService.login(form);
			if(admin==null) {
				req.setAttribute("msg", "用户名或者密码错误");
				return "f:/adminJsps/login.jsp";
			}
			IpManage ipManage=new IpManage();
			String ip=IPUtil.getIpAddress(req);//获取登陆的ip地址
			Timestamp timeStamp=new Timestamp(System.currentTimeMillis());//获取时间戳
			ipManage.setId(CommonUtils.uuid());
			ipManage.setIp(ip);
			ipManage.setAid(admin.getAid());
			ipManage.setTime(timeStamp);
			ipManage.setStatus(1);//设置状态为登陆
			ipManageService.insertIpAdmin(ipManage);
			req.getSession().setAttribute("ipAddrAdminLogin",ip);
			req.getSession().setAttribute("loginTimeAdmin", timeStamp.toString());
			req.getSession().setAttribute("admin", admin);
			return "r:/adminJsps/content/main.jsp";
		}
	
	public String quit(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Admin admin=(Admin) request.getSession().getAttribute("admin");
		IpManage ipManage=new IpManage();
		IpManage loginIP=ipManageService.findLastIpAdmin(admin.getAid(), 1);
		String ip=IPUtil.getIpAddress(request);//获取登陆的ip地址
		Timestamp timeStamp=new Timestamp(System.currentTimeMillis());//获取时间戳
		ipManage.setId(CommonUtils.uuid());
		ipManage.setIp(ip);
		ipManage.setAid(admin.getAid());
		ipManage.setTime(timeStamp);
		ipManage.setStatus(2);//设置状态为退出
		ipManageService.insertIpAdmin(ipManage);
		request.setAttribute("ipAddrAdminLogout", ip);
		request.setAttribute("loginTimeAdmin", loginIP.getTime().toString());
		request.setAttribute("logoutTimeAdmin", timeStamp.toString());
		
		return "f:/adminJsps/login.jsp";
	}
}
