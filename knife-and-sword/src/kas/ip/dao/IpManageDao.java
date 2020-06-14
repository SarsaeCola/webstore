package kas.ip.dao;
/** 
* @Author 黄子良
* @Time 2020年6月11日 下午2:56:03 
* Description:
*/

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;


import cn.itcast.jdbc.TxQueryRunner;

import kas.ip.domain.IpManage;


public class IpManageDao {
	private QueryRunner qr=new TxQueryRunner();
	
	/**
	 * 插入ip记录
	 * @param ip
	 * @throws SQLException
	 */
	public void insertIpUser(IpManage ip) throws SQLException {
		String sql="insert into ipManage ("
				+ "id,uid,ip,time,status) "
				+ "values(?,?,inet_aton(?),?,?)";
		Object[] params= {
				ip.getId(),ip.getUid().getUid(),ip.getIp(),
				ip.getTime(),ip.getStatus()
		};
		qr.update(sql,params);
		}
	
	public void insertIpAdmin(IpManage ip) throws SQLException {
		String sql="insert into ipManage ("
				+ "id,ip,time,aid,status) "
				+ "values(?,inet_aton(?),?,?,?)";
		Object[] params= {
				ip.getId(),ip.getIp(),
				ip.getTime(),ip.getAid().getAid(),ip.getStatus()
		};
		qr.update(sql,params);
		}
	
	/**
	 * 查询最新ip记录
	 * @param uid
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public IpManage findLastIpUser(String uid,int status) throws SQLException {
		String sql="select id,uid,inet_ntoa(ip),time,status from ipManage where uid=? and status=? order by time desc limit 1";
		Map<String, Object> map=qr.query(sql, new MapHandler(),uid,status);
		IpManage ipManage=new IpManage();
		ipManage.setId(map.get("id").toString());
		ipManage.setIp(map.get("inet_ntoa(ip)").toString());
		ipManage.setTime(Timestamp.valueOf(map.get("time").toString()));
		ipManage.setUid(map.get("uid").toString());
		ipManage.setStatus(Integer.parseInt(map.get("status").toString()));
		return ipManage;
	}
	
	public IpManage findLastIpAdmin(String aid,int status) throws SQLException {
		String sql="select id,inet_ntoa(ip),time,aid,status from ipManage where aid=? and status=? order by time desc limit 1";
		Map<String, Object> map=qr.query(sql, new MapHandler(),aid,status);
		IpManage ipManage=new IpManage();
		ipManage.setId(map.get("id").toString());
		ipManage.setIp(map.get("inet_ntoa(ip)").toString());
		ipManage.setTime(Timestamp.valueOf(map.get("time").toString()));
		ipManage.setAid(map.get("aid").toString());
		ipManage.setStatus(Integer.parseInt(map.get("status").toString()));
		return ipManage;
	}
}

