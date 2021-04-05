/**
 * 
 */
package io.monitoring.rule.engine;

/**
 * @author vipink
 *
 */
public class Condition {

	private String left;
	private String right;
	private String operation;
	private Condition and;
	private Condition or;

	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Condition(){}
	public Condition(String left, String right, String operation) {
		super();
		this.left = left;
		this.right = right;
		this.operation = operation;
	}
	@Override
	public String toString() {
		return "Condition [left=" + left + ", right=" + right + ", operation="
				+ operation + ", and=" + and + ", or=" + or + "]";
	}
	public void and(Condition and) {
		this.and = and;
	}
	public void or(Condition or) {
		this.or = or;
	}

	public Condition andCondition() {
		return this.and;
	}
	public Condition orCondition() {
		return this.or;
	}
}
