package kas.category.domain;

import java.util.List;

/** 分类功能类
* @Author 黄子良
* @Time 2020年5月16日 下午7:46:00 
* Description:
*/
public class Category {
	private String cid;
	private String cname;
	private Category parent;//父分类
	private String description;
	private List<Category> children;//子分类
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
	
}
