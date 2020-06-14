package kas.category.dao;
/** 分类功能数据操作类
* @Author 黄子良
* @Time 2020年5月16日 下午7:46:58 
* Description:
*/

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import Test.c3p0Test;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import kas.category.domain.Category;

public class CategoryDao {
	private QueryRunner qr=new TxQueryRunner();
	
	
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql="select count(*) from category where pid=?";
		Number result=(Number)qr.query(sql, new ScalarHandler(),pid);
		return result==null ? 0 : result.intValue();
	}
	/**
	 * 返回所有一级分类和二级分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> searchAll() throws SQLException{
		//查询出一级分类
		String sql="select * from category where pid is null";
		List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler());
		List<Category>parents=toCategoryList(mapList);
		//通过一级分类作为父分类检索二级分类
		for(Category parent:parents) {
			List<Category> children=searchByParent(parent.getCid());
			parent.setChildren(children);//为父分类设置子分类
		}
		return parents;
	}
	
	/**
	 * 仅仅查询一级分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> searchParents() throws SQLException{
		//查询出一级分类
		String sql="select * from category where pid is null";
		List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler());
		List<Category>parents=toCategoryList(mapList);
		return parents;
	}
	
	/**
	 * 加载一个分类
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public Category load(String cid) throws SQLException {
		String sql="select * from category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(),cid));
		
	}
	
	/**
	 * 修改分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql="update category set cname=?, pid=?, `description`=? where cid=?";
		String pid=null;
		if(category.getParent().getCid()!=null) {
			pid=category.getParent().getCid();
		}
		Object[] params= {category.getCname(),pid,category.getDescription(),category.getCid()};
		qr.update(sql,params);
		}
	
		public void delete(String cid) throws SQLException {
			String sql="delete from category where cid=?";
			qr.update(sql,cid);
		}
		
			
	/*
	 *Map对象转化成category对象
	 */
	private Category toCategory(Map<String,Object> map) {
		Category category=CommonUtils.toBean(map, Category.class);
		String pid=(String)map.get("pid");
		if(pid!=null) {
			/*
			 * 父分类不为空，使用父文类管理分类关系
			 */
			Category parent=new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/**
	 * 返回分类集合
	 * @param mapList
	 * @return
	 */
	private List<Category> toCategoryList(List<Map<String,Object>> mapList){
		List<Category> categoryList =new ArrayList<Category>();
		for(Map<String,Object> map:mapList) {
			Category current=toCategory(map);
			categoryList.add(current);
		}
		return categoryList;
	}
	
	public List<Category> searchByParent(String pid) throws SQLException {
		String sql = "select * from category where pid=? order by orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}
	
	public void add(Category category) throws SQLException {
		String sql="insert into category(cid,cname,pid,`description`) values(?,?,?,?)";
		//分类不同，插入方式不同
		String pid=null;
		if(category.getParent()!=null) {
			pid=category.getParent().getCid();
		}
		Object[] params= {category.getCid(),category.getCname(),pid,category.getDescription()};
		qr.update(sql,params);
	}
}
