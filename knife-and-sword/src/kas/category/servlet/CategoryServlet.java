package kas.category.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import kas.category.domain.Category;
import kas.category.service.CategoryService;

/** 分类功能web层
* @Author 黄子良
* @Time 2020年5月16日 下午7:49:41 
* Description:
*/
public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService=new CategoryService();
	
	public String searchAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			List<Category> parents=categoryService.searchAll();
			req.setAttribute("parents", parents);
			return "f:/jsp/main/left.jsp";
		}
}
