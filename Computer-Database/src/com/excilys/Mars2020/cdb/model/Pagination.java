package com.excilys.Mars2020.cdb.model;

import java.util.ArrayList;

import com.excilys.Mars2020.cdb.ui.CLI;

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
		if(this.actualPageNb < this.maxPages) {
			this.actualPageNb++;
		}
	}
	
	/**
	 * going back of 1 pages
	 */
	public void PrevPage() {
		if(this.actualPageNb>0) {
			this.actualPageNb--;
		}
	}
	
	/**
	 * Display the actual page content of type T
	 * @param <T>
	 * @param pageContent
	 */
	public <T> void displayPageContent(ArrayList<T> pageContent) {
		System.out.println("*-------------------------------------------------------------*");
		CLI.displayArrayList(pageContent);
		System.out.println("*-------------------------------------------------------------*");
	}

	public int getActualPageNb() {
		return actualPageNb;
	}

	public void setActualPageNb(int actualPageNb) {
		this.actualPageNb = actualPageNb;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}
	
	
}
