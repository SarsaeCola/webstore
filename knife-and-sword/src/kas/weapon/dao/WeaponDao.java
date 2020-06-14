package kas.weapon.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import kas.category.domain.Category;
import kas.page.domain.Expression;
import kas.page.domain.PageBean;
import kas.page.domain.PageConstances;
import kas.weapon.domain.Weapon;


/** 
* @Author 黄子良
* @Time 2020年5月17日 下午5:20:05 
* Description:
*/
public class WeaponDao {
	
	
	
	private QueryRunner qr=new TxQueryRunner();
	
	public PageBean<Weapon> findByCombination(Weapon criteria,int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		expressions.add(new Expression("wname", "like","%"+criteria.getWname()+"%"));
		expressions.add(new Expression("author", "like","%"+criteria.getAuthor()+"%"));
		return findByCriteria(expressions, presentPageNumber);
	}
	
	/**
	 * 按制造者模糊查询
	 * @param author
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Weapon> findByAuthor(String author,int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		expressions.add(new Expression("author", "like","%"+author+"%"));
		return findByCriteria(expressions, presentPageNumber);
	}
	
	/**
	 * 按名字模糊查询
	 * @param wName
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Weapon> findByWeaponName(String wName,int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		expressions.add(new Expression("wname", "like","%"+wName+"%"));
		return findByCriteria(expressions, presentPageNumber);
	}
	/**
	 * 通过分类查询
	 * @param cid
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Weapon> findByCategory(String cid,int presentPageNumber) throws SQLException{
		List<Expression> expressions=new ArrayList<Expression>();
		expressions.add(new Expression("cid", "=", cid));
		return findByCriteria(expressions, presentPageNumber);
	}
	
	/**
	 * 通用查询方法
	 * @param expList
	 * @param presentPageNumber
	 * @return
	 * @throws SQLException 
	 */
	private PageBean<Weapon> findByCriteria(List<Expression> expList,int presentPageNumber) throws SQLException{
		//得到每页记录数
		int pageRecord=PageConstances.WEAPON_PAGE_SIZE;
		/**
		 * 得到总记录数
		 */
		 //通过expression生成select语句
		StringBuilder whereSql=new StringBuilder(" where 1=1");
		List<Object> params=new ArrayList<Object>();//对应sql语句中的占位符
		for(Expression exp:expList) {
			/*
			 * 添加查询条件
			 */
			whereSql.append(" and ").append(exp.getName())
			.append(" ").append(exp.getOperator())
			.append(" ");
			if(!exp.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(exp.getValue());
			}
		}
		String sql="select count(*) from weapon"+whereSql;
		Number number=(Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int totalRecord=number.intValue();//得到总记录数
		/*
		 * 得到BeanList
		 */
		sql="select * from weapon"+whereSql+" order by orderBy limit ?,?";
		params.add((presentPageNumber-1)*pageRecord);//计算当前页首行记录的下标
		params.add(pageRecord);
		List<Weapon> beanList=qr.query(sql, new BeanListHandler<Weapon>(Weapon.class),params.toArray());
		/*
		 * 创建PageBean
		 * 缺失的url由servlet提供
		 */
		PageBean<Weapon> pageBean=new PageBean<Weapon>();
		pageBean.setBeanList(beanList);
		pageBean.setPresentPageNumber(presentPageNumber);
		pageBean.setPageRecord(pageRecord);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setTotalPage(pageBean.getTotalPage());
		
		return pageBean;
	}
	
	public Weapon findByWid(String wid) throws SQLException {
		String sql="select * from weapon where wid=?";
		Map<String, Object> map=qr.query(sql, new MapHandler(),wid);
		Weapon weapon=CommonUtils.toBean(map, Weapon.class);
		//数据库中的weapon表有cid唯一映射对应category
		Category category=CommonUtils.toBean(map, Category.class);
		sql="select * from category where cid=?";
		Map<String, Object> categoryMap=qr.query(sql, new MapHandler(),category.getCid());
		String pid=(String)categoryMap.get("pid");
		if(pid!=null) {
			/*
			 * 父分类不为空，使用父文类管理分类关系
			 */
			Category parent=new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		weapon.setCategory(category);
		return weapon;
	}
	
	public int findWeaponCountByCategory(String cid) throws SQLException {
		String sql="select count(*) from weapon where cid=?";
		Number count=(Number)qr.query(sql, new ScalarHandler(),cid);
		return count==null ? 0 : count.intValue();
		
	}

	/**
	 * 添加武器
	 * @param weapon
	 * @throws SQLException
	 */
	public void add(Weapon weapon) throws SQLException {
		// TODO Auto-generated method stub
		String sql="insert into weapon(wid,author,"
				+ "wname,"
				+ "price,currentPrice,"
				+ "discount,material,"
				+ "cid,bimg,simg) values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params= {weapon.getWid(),weapon.getAuthor(),weapon.getWname(),weapon.getPrice(),weapon.getCurrentPrice(),
				weapon.getDiscount(),weapon.getMaterial(),weapon.getCategory().getCid(),
				weapon.getBimg(),weapon.getSimg()
		};
		qr.update(sql,params);
	}
	
	/**
	 * 删除武器
	 * @param weapon
	 * @throws SQLException
	 */
	public void delete(String wid) throws SQLException {
		String sql="delete from weapon where wid=?";
		qr.update(sql,wid);
	}
	
	/**
	 * 编辑武器
	 * @param weapon
	 * @throws SQLException
	 */
	public void edit(Weapon weapon) throws SQLException {
		String sql="update weapon set author=?,wname=?,price=?,currentPrice=?,"
				+ "discount=?,material=?,cid=? where wid=?";
		Object[] params= {weapon.getAuthor(),weapon.getWname(),weapon.getPrice(),weapon.getCurrentPrice(),
				weapon.getDiscount(),weapon.getMaterial(),weapon.getCategory().getCid(),weapon.getWid()
		};
		qr.update(sql,params);
	}
}
