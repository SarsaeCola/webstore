package kas.admin.weapon;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import kas.category.domain.Category;
import kas.category.service.CategoryService;
import kas.page.domain.PageBean;
import kas.weapon.domain.Weapon;
import kas.weapon.service.WeaponService;

/** 
* @Author 黄子良
* @Time 2020年5月29日 上午10:34:11 
* Description:
*/
public class AdminWeaponServlet extends BaseServlet {
	private WeaponService weaponService=new WeaponService();
	private CategoryService categoryService=new CategoryService();

	/**
	 * 显示分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			List<Category> parents=categoryService.searchAll();
			req.setAttribute("parents", parents);
			return "f:/adminJsps/content/weapon/left.jsp";
		}
	
	
	/**
	 * 删除武器
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String wid = req.getParameter("wid");
		
		/*
		 * 删除图片
		 */
		Weapon weapon = weaponService.load(wid);
		String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
		new File(savepath, weapon.getBimg()).delete();//删除文件
		new File(savepath, weapon.getSimg()).delete();//删除文件
		
		weaponService.delete(wid);//删除数据库的记录
		
		req.setAttribute("msg", "删除武器成功！");
		return "f:/adminJsps/msg.jsp";
	}
	
	/**
	 * 修改武器
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 把表单数据封装到Weapon对象中
		 * 2. 封装cid到Category中
		 * 3. 把Category赋给Weapon
		 * 4. 调用service完成工作
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		Map map = req.getParameterMap();
		Weapon weapon = CommonUtils.toBean(map, Weapon.class);
		Category category = CommonUtils.toBean(map, Category.class);
		weapon.setCategory(category);
		
		weaponService.edit(weapon);
		req.setAttribute("msg", "修改武器成功！");
		return "f:/adminJsps/msg.jsp";
	}
	
	/**
	 * 加载武器
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取wid，得到Weapon对象，保存之
		 */
		String wid = req.getParameter("wid");
		Weapon weapon = weaponService.load(wid);
		req.setAttribute("weapon", weapon);
		
		/*
		 * 2. 获取所有一级分类，保存之
		 */
		req.setAttribute("parents", categoryService.searchParents());
		/*
		 * 3. 获取当前武器所属的一级分类下所有2级分类
		 */
		String pid = weapon.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));
		
		/*
		 * 4. 转发到description.jsp显示
		 */
		return "f:/adminJsps/content/weapon/description.jsp";
	}
	
	/**
	 * 添加武器：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取所有一级分类，保存之
		 * 2. 转发到add.jsp，该页面会在下拉列表中显示所有一级分类
		 */
		List<Category> parents = categoryService.searchParents();
		req.setAttribute("parents", parents);
		return "f:/adminJsps/content/weapon/add.jsp";
	}
	
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取pid
		 * 2. 通过pid查询出所有2级分类
		 * 3. 把List<Category>转换成json，输出给客户端
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		String json = toJson(children);
		resp.getWriter().print(json);
		return null;
	}
	
	// {"cid":"fdsafdsa", "cname":"fdsafdas"}
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	// [{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa", "cname":"fdsafdas"}]
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	


	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getPresentPageNumber(HttpServletRequest req) {
		int presentPageNumber = 1;
		String param = req.getParameter("presentPageNumber");
		if(param != null && !param.trim().isEmpty()) {
			try {
				presentPageNumber = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return presentPageNumber;
	}
	
	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在presentPageNumber参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&presentPageNumber=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	
	/**
	 * 按分类查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到presentPageNumber：如果页面传递，使用页面的，如果没传，presentPageNumber=1
		 */
		int presentPageNumber = getPresentPageNumber(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String cid = req.getParameter("cid");
		/*
		 * 4. 使用presentPageNumber和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Weapon> pageBean = weaponService.findByCategory(cid, presentPageNumber);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsp/weapon/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		return "f:/adminJsps/content/weapon/list.jsp";
	}
	
	/**
	 * 按作者查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到presentPageNumber：如果页面传递，使用页面的，如果没传，presentPageNumber=1
		 */
		int presentPageNumber = getPresentPageNumber(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author = req.getParameter("author");
		/*
		 * 4. 使用presentPageNumber和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Weapon> pageBean = weaponService.findByAuthor(author, presentPageNumber);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsp/weapon/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		return "f:/adminJsps/content/weapon/list.jsp";
	}
	
	
	/**
	 * 按图名查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到presentPageNumber：如果页面传递，使用页面的，如果没传，presentPageNumber=1
		 */
		int presentPageNumber = getPresentPageNumber(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String wname = req.getParameter("wname");
		/*
		 * 4. 使用presentPageNumber和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Weapon> pageBean = weaponService.findByWeaponName(wname, presentPageNumber);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsp/weapon/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		return "f:/adminJsps/content/weapon/list.jsp";
	}
	
	/**
	 * 多条件组合查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到presentPageNumber：如果页面传递，使用页面的，如果没传，presentPageNumber=1
		 */
		int presentPageNumber = getPresentPageNumber(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		Weapon criteria = CommonUtils.toBean(req.getParameterMap(), Weapon.class);
		/*
		 * 4. 使用presentPageNumber和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Weapon> pageBean = weaponService.findByCombination(criteria, presentPageNumber);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsp/weapon/list.jsp
		 */
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		return "f:/adminJsps/content/weapon/list.jsp";
	}
}
