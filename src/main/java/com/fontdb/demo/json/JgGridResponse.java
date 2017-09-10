package com.fontdb.demo.json;

import java.util.List;




/**
 * A simple POJO that maps to the JSON structure of a JqGrid. 
 * <p>The property names of this POJO must match the property names of your JqGrid's jsonReader.
 * @param <T>
 * 
 * @see <a href="http://www.trirand.com/jqgridwiki/doku.php?id=wiki:retrieving_data#json_data">JSON Data</a>
 *
 */
public class JgGridResponse<T> {

	/**
	 * Current page of the query
	 */
	private int page;
	
	/**
	 * Total pages for the query
	 */
	private int total;
	
	/**
	 * Total number of records for the query
	 */
	private int records;
	
	/**
	 * An array that contains the actual objects
	 */
	private List<T> rows;

	
	public JgGridResponse() {
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
