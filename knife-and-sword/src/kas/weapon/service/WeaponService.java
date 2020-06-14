package kas.weapon.service;
/** 
* @Author 黄子良
* @Time 2020年5月17日 下午5:11:57 
* Description:
*/

import java.sql.SQLException;

import kas.page.domain.PageBean;
import kas.weapon.dao.WeaponDao;
import kas.weapon.domain.Weapon;

public class WeaponService {
	private WeaponDao weaponDao=new WeaponDao();
	//分类查
	public PageBean<Weapon> findByCategory(String cid,int presentPageNumber){
		try {
			return weaponDao.findByCategory(cid, presentPageNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//名字查
	public PageBean<Weapon> findByWeaponName(String wname,int presentPageNumber){
		try {
			return weaponDao.findByWeaponName(wname, presentPageNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//制造者查
	public PageBean<Weapon> findByAuthor(String author,int presentPageNumber){
		try {
			return weaponDao.findByAuthor(author, presentPageNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//条件组合查询
	public PageBean<Weapon> findByCombination(Weapon criteria,int presentPageNumber){
		try {
			return weaponDao.findByCombination(criteria, presentPageNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载武器详细信息
	 * @param wid
	 * @return
	 */
	public Weapon load(String wid) {
		try {
			return weaponDao.findByWid(wid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 返回指定分类下的武器数目
	 * @param cid
	 * @return
	 */
	public int findWeaponCountByCategory(String cid) {
		try {
			return weaponDao.findWeaponCountByCategory(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 新增武器
	 * @param weapon
	 */
	public void add(Weapon weapon) {
		try {
			weaponDao.add(weapon);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 编辑武器
	 * @param weapon
	 */
	public void edit(Weapon weapon) {
		try {
			weaponDao.edit(weapon);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除武器
	 * @param wid
	 */
	public void delete(String wid) {
		try {
			weaponDao.delete(wid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
