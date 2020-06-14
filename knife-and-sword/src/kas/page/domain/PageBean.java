package kas.page.domain;

import java.util.List;

/** 分页Bean
* @param <T>
 * @Author 黄子良
* @Time 2020年5月17日 下午6:25:55 
* Description:
*/
public class PageBean<T> {
	private int presentPageNumber;//当前页码pc
	private int totalRecord;//总记录数tr
	private int pageRecord;//每页记录数ps
	private int totalPage;//总页数
	private String url;//请求的路径和参数
	private List<T> beanList;
	
	//返回总页数
	public int getTotalPage() {
		int totalPage=totalRecord/pageRecord;
		return totalRecord%pageRecord ==0?totalPage:totalPage+1;
	}

	public int getPresentPageNumber() {
		return presentPageNumber;
	}

	public void setPresentPageNumber(int presentPageNumber) {
		this.presentPageNumber = presentPageNumber;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
