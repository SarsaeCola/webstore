package kas.page.domain;
/** 
* @Author 黄子良
* @Time 2020年5月17日 下午6:49:56 
* Description:
*/
public class Expression {
	private String name;
	private String operator;
	private String value;
	
	public Expression() {
		super();
	}
	public Expression(String name, String operator, String value) {
		super();
		this.name = name;
		this.operator = operator;
		this.value = value;
	}
	@Override
	public String toString() {
		return "Expression [name=" + name + ", operator=" + operator + ", value=" + value + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
