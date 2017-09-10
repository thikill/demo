package com.fontdb.demo.web.dto;

import java.util.List;

public class JqGridDto<T> {

	/**
	 * Total pages for the query
	 */
	private int total;

	/**
	 * Total number of records for the query
	 */
	private long records;

	/**
	 * An array that contains the actual objects
	 */
	private List<T> rows;

	public JqGridDto() {
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
