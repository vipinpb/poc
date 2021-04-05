/**
 * 
 */
package io.monitoring.rule.engine;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author vipink
 *
 */
@Service
public class SpaceRuleParser implements RuleParser {

	/**
	 * 
	 */
	public SpaceRuleParser() {}

	/* (non-Javadoc)
	 * @see oracle.monitoring.rule.engine.RuleParser#parse(java.lang.String)
	 */
	@Override
	public Condition parse(String exp) throws RuleParserException {
		Map<String,String> labelMap = new HashMap<>();
		int labelIndex = exp.indexOf('{');
		if (labelIndex >= 0 ) {
			String metricName = exp.substring(0, labelIndex);
			String labels = exp.substring(labelIndex+1, exp.indexOf('}'));
			String label[] = labels.split(",");
			for (String l: label) {
				String nameValue [] = l.split("=");
				labelMap.put(nameValue[0].toLowerCase(),nameValue[1].replaceAll("\"", "").toLowerCase());
			}
			exp = metricName +exp.substring(exp.indexOf('}')+1);
		}
		
		String tokens[] = exp.split(" ");
		// replace variables if any ${test}
		// build condition out of tokens
		ConditionBuilder builder = new ConditionBuilder();
		for (String token: tokens) {
			builder.push(token);
		}
		
		return builder.build();
	}

}
