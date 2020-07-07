package com.excilys.mars2020.cdb.model;

/**
 * Base for Pagination classes
 * @author remi
 *
 */
public class Pagination {
	
	private int maxEntities;
	private int actualPageNb = 0;
	private int pageSize;
	private int maxPages;
	
	private Pagination(Builder builder) {
		this.maxEntities = builder.maxEntities;
		this.actualPageNb = builder.actualPageNb;
		this.pageSize = builder.pageSize;
		this.maxPages = builder.maxPages;
		
		if(this.pageSize == 0 && this.maxPages == 0) {
			this.pageSize = 15;
			this.maxPages = this.maxEntities / this.pageSize;
		}
		else if(this.pageSize * this.maxPages < this.maxEntities) {
			this.maxPages = this.maxEntities / this.pageSize;
		}
		if(this.actualPageNb > this.maxPages) {
			this.actualPageNb = this.maxPages;
		}
		else if(this.actualPageNb < 0) {
			this.actualPageNb = 0;
		}
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
	
	public int getMaxEntities() {
		return maxEntities;
	}
	
	public static class Builder{
		
		private final int maxEntities;
		private int actualPageNb;
		private int pageSize;
		private int maxPages;
		
		public Builder(int maxEntities) {
			this.maxEntities = maxEntities;
		}
		
		public Builder actualPangeNb(int actualPageNb) {
			this.actualPageNb = actualPageNb;
			return this;
		}
		
		public Builder pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}
		
		public Builder maxPages(int maxPages) {
			this.maxPages = maxPages;
			return this;
		}
		
		public Pagination build() {
			return new Pagination(this);
		}
		
		
	}

	@Override
	public String toString() {
		return "Pagination [maxEntities=" + maxEntities + ", actualPageNb=" + actualPageNb + ", pageSize=" + pageSize
				+ ", maxPages=" + maxPages + "]";
	}
	
	
}
