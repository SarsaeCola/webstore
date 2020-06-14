package kas.weapon.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.servlet.BaseServlet;
import kas.page.domain.PageBean;
import kas.weapon.domain.Weapon;
import kas.weapon.service.WeaponService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;


public class WeaponServlet extends BaseServlet {
	private WeaponService weaponService=new WeaponService();
	
	public String findByCategory(HttpServletRequest request,HttpServletResponse response) {
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到pageBean
		String cid=request.getParameter("cid");
		PageBean<Weapon> pageBean=weaponService.findByCategory(cid, presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/jsp/weapon/list.jsp";
	}
	
	/**
	 * 按照作者查
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByAuthor(HttpServletRequest request,HttpServletResponse response) {
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到pageBean
		String author=request.getParameter("author");
		PageBean<Weapon> pageBean=weaponService.findByCategory(author, presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/jsp/weapon/list.jsp";
	}
	
	public String findByWeaponName(HttpServletRequest request,HttpServletResponse response) {
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到pageBean
		String wname=request.getParameter("wname");
		PageBean<Weapon> pageBean=weaponService.findByWeaponName(wname, presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/jsp/weapon/list.jsp";
	}
	
	/**
	 * 对应superSearch.jsp的表单进行组合查询
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByCombination(HttpServletRequest request,HttpServletResponse response) {
		//得到presentPageNumber
		int presentPageNumber=getPresentPageNumber(request);
		//得到url
		String url=getUrl(request);
		//得到pageBean
		Weapon criteria=CommonUtils.toBean(request.getParameterMap(), Weapon.class);
		PageBean<Weapon> pageBean=weaponService.findByCombination(criteria, presentPageNumber);
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
		return "f:/jsp/weapon/list.jsp";
	}
	/**
	 * 截取url
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
	
	public String load(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		String wid=request.getParameter("wid");
		Weapon weapon=weaponService.load(wid);
		request.setAttribute("weapon", weapon);
		return "f:/jsp/weapon/description.jsp";
	}
}
