package io.monitoring.metrics;

public enum Mode {
	HEALTHY(0),WARN(1);
	private int value;
	private Mode(int mode) {
		value = mode;
	}
	
	public int get() {
		return value;
	}
}
