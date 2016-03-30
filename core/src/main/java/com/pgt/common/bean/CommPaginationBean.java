package com.pgt.common.bean;

import com.pgt.utils.PaginationBean;

public class CommPaginationBean extends PaginationBean {

	private long totalPage;// 计算总页数

	private long currentPage;// 当前页数

	private long startPageIndex;

	private long endPageIndex;

	private long currentIndex;


	public CommPaginationBean() {

	}


	@Override
	public long getCurrentIndex() {
		return currentIndex;
	}

	@Override
	public void setCurrentIndex(long currentIndex) {
		this.currentIndex = currentIndex;
	}

	public CommPaginationBean(long capacity, long currentIndex, long totalAmount) {

		this.setCapacity(capacity);
		this.setCurrentIndex(currentIndex);   
		this.setTotalAmount(totalAmount);

		this.setCurrentPage((currentIndex/capacity)+1);
		
		// 计算总页数
		if (this.getTotalAmount() % this.getCapacity() > 0) {

			this.totalPage = this.getTotalAmount() / this.getCapacity() + 1;

		} else {

			this.totalPage = this.getTotalAmount() / this.getCapacity();
		}

		if (this.totalPage < 6L) {   

			// 当总页数小于6
			this.startPageIndex = 1L;
			this.endPageIndex = this.totalPage;

		} else {

			// 当总页数大于10
			this.startPageIndex = this.getCurrentPage() - 2L;
			this.endPageIndex = this.getCurrentPage() + 3L;

			if (this.startPageIndex < 1L) {
				// 当开始页小于1时
				this.startPageIndex = 1L;
			}

			if (this.endPageIndex > this.totalPage) {

				// 当结束页大于最后一页时
				this.endPageIndex = this.totalPage;
			}
		}

	     
		
	}



	public Long getTotalPage() {

		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public long getStartPageIndex() {

		return startPageIndex;
	}

	public void setStartPageIndex(long startPageIndex) {
		this.startPageIndex = startPageIndex;
	}

	public long getEndPageIndex() {

		return endPageIndex;
	}


	public void setEndPageIndex(long endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

}
