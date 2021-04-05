/**
 * 
 */
package io.monitoring.common;

/**
 * This would specify where metrics are stored
 * @author vipink
 *
 */
public enum StoreType {
	MEMORY(0),EHCACHE(1),ORACLE(2),ELASTIC(3),HSQLDB(4);
	private int type;

	private StoreType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
