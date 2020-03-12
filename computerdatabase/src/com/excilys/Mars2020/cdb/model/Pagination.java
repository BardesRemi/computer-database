package com.excilys.Mars2020.cdb.model;

/**
 * Base for Pagination classes
 * @author remi
 *
 */
public class Pagination {
	
	private int actualPageNb;
	private int pageSize;
	private int maxPages;
	
	public Pagination(int maxEntities) {
		this.actualPageNb = 0;
		this.pageSize = 15;
		this.maxPages = maxEntities / this.pageSize;
	}
	
	/**
	 * advancing of 1 pages
	 */
	public void nextPage() {
		if (this.actualPageNb < this.maxPages) {
			this.actualPageNb++;
		}
	}
	
	/**
	 * going back of 1 pages
	 */
	public void PrevPage() {
		if (this.actualPageNb > 0) {
			this.actualPageNb--;
		}
	}

	public int getActualPageNb() {
		return actualPageNb;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getMaxPages() {
		return maxPages;
	}
	
	
}
