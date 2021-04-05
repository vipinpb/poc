/**
 * 
 */
package io.monitoring.metrics;

import java.util.Arrays;

/**
 * @author vipink
 *
 */
public class QueryResponse<T> {

	/**
	 * 
	 */
	public QueryResponse() {}

	private int count;
	private boolean hasMore;
	private T items[];
	private int limit;
	private int offset;
	private int totalResults;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public T[] getItems() {
		return items;
	}
	public void setItems(T[] items) {
		this.items = items;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	@Override
	public String toString() {
		return "QueryResponse [count=" + count + ", hasMore=" + hasMore
				+ ", items=" + Arrays.toString(items) + ", limit=" + limit
				+ ", offset=" + offset + ", totalResults=" + totalResults + "]";
	}
	
	public QueryResponse(int count, boolean hasMore, T[] items, int limit,
			int offset, int totalResults) {
		super();
		this.count = count;
		this.hasMore = hasMore;
		this.items = items;
		this.limit = limit;
		this.offset = offset;
		this.totalResults = totalResults;
	}
	
	
}
