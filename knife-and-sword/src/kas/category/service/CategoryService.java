package kas.category.service;
/** 分类功能服务类
* @Author 黄子良
* @Time 2020年5月16日 下午7:47:50 
* Description:
*/

import java.sql.SQLException;
import java.util.List;

import kas.category.dao.CategoryDao;
import kas.category.domain.Category;

public class CategoryService {
	private CategoryDao categoryDao=new CategoryDao();
	
	/**
	 * 查询指定分类下的子分类数目
	 * @param pid
	 * @return
	 */
	public int findChildrenCountByParent(String pid) {
		try {
			return categoryDao.findChildrenCountByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 修改分类
	 * @param category
	 */
	public void edit(Category category) {
		try {
			categoryDao.edit(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(String cid) {
		try {
			categoryDao.delete(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载分类
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		try {
			return categoryDao.load(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 增加分类
	 * @param category
	 */
	public void add(Category category) {
		try {
			categoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> searchAll(){
		try {
			return categoryDao.searchAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 仅查询一级分类
	 * @return
	 */
	public List<Category> searchParents(){
		try {
			return categoryDao.searchParents();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询指定父分类下的所有子分类
	 * @param pid
	 * @return
	 */
	public List<Category> findChildren(String pid) {
	try {
		return categoryDao.searchByParent(pid);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}	
	}
}
