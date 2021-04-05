/**
 * 
 */
package io.monitoring.rule.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vipink
 *
 */
public class ConditionBuilder {

	List<Condition> condList = new ArrayList<>();
	Condition con = new Condition();
	
	public void push(String token) {
		
		if (con.getLeft() == null ) {
			con.setLeft(token);
		} else if (con.getOperation() == null ) {
			con.setOperation(token);
		} else if (con.getRight() == null ) {
			con.setRight(token);
		} else {
			if (token.equals("&&")) {
				condList.add(con);
				con = new Condition();
				condList.get(0).and(con);
			} else if (token.equals("||")) {
				condList.add(con);
				con = new Condition();
				condList.get(0).or(con);
			}
		}
	}
	
	public Condition build() {
		return condList.size() > 0 ? condList.get(0) : con;
	}
}
