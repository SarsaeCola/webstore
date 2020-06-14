package kas.admin.category.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import kas.category.domain.Category;
import kas.category.service.CategoryService;
import kas.weapon.service.WeaponService;


/** 
* @Author 黄子良
* @Time 2020年5月28日 上午11:55:21 
* Description:
*/
public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService=new CategoryService();
	private WeaponService weaponService=new WeaponService();
	public String findAll(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		request.setAttribute("parents", categoryService.searchAll());
		return "f:/adminJsps/content/category/list.jsp";
	}
	
	/**添加一级分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		Category parent=CommonUtils.toBean(request.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());
		categoryService.add(parent);
		return findAll(request, response);
	}
	
	/**添加二级分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		String pid=request.getParameter("pid");
		List<Category> parents=categoryService.searchParents();
		request.setAttribute("pid", pid);
		request.setAttribute("parents", parents);
		
		return "f:/adminJsps/content/category/add2.jsp";
	}
	
	public String addChild(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		//封装数据
		Category child=CommonUtils.toBean(request.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());
		String pid=request.getParameter("pid");
		Category parent=new Category();
		parent.setCid(pid);
		child.setParent(parent);
		//添加子分类
		categoryService.add(child);
		return findAll(request, response);
	}
	
	/**
	 * 修改一级分类
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		String cid=request.getParameter("cid");//获取页面cid
		Category parent=categoryService.load(cid);
		request.setAttribute("parent", parent);//从数据库获取信息并转发用于后续处理
		return "f:/adminJsps/content/category/edit.jsp";
	}
	
	public String editParent(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		Category parent=CommonUtils.toBean(request.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(request, response);
	}
	
	/**
	 * 修改二级分类
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		String cid=request.getParameter("cid");
		Category child=categoryService.load(cid);
		request.setAttribute("child", child);
		request.setAttribute("parent", categoryService.searchParents());
		return "f:/adminJsps/content/category/edit2.jsp";
	}
	
	public String editChild(HttpServletRequest request,HttpServletResponse response)
				throws SQLException,IOException {
		Category child=CommonUtils.toBean(request.getParameterMap(), Category.class);
		String pid=request.getParameter("pid");
		Category parent=new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.edit(child);
		return findAll(request, response);
	}
	
	/**
	 * 删除父分类
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		//有子分类则删除失败，没有则删除成功
		String cid=request.getParameter("cid");
		int count=categoryService.findChildrenCountByParent(cid);
		if(count>0) {
			request.setAttribute("msg", "该分类存在子分类，删除失败");
			return "f:/adminJsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(request, response);
		}
	}
	
	public String deleteChild(HttpServletRequest request,HttpServletResponse response)
			throws SQLException,IOException {
		String cid=request.getParameter("cid");
		int count=weaponService.findWeaponCountByCategory(cid);
		if(count>0) {
			request.setAttribute("msg", "该分类下存在武器条目，删除操作无法进行");
			return "f:/adminJsps/content/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(request, response);
		}
	}
}
	
