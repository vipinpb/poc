/**
 * 
 */
package oracle.monitoring;

import io.monitoring.rule.engine.TSDBRule;
import io.monitoring.service.impl.RuleHelper;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author vipink
 *
 */
//@SpringBootTest
public class TestRuleEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<TSDBRule> rules = RuleHelper.ruleList();
	}

}
