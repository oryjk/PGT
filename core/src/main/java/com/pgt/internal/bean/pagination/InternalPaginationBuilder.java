package com.pgt.internal.bean.pagination;

import java.util.List;

public class InternalPaginationBuilder {

	private long mCapacity;
	private long mCurrentIndex;
	private long mCount;
	private String mKeyword;
	private String mSortFieldName;
	private boolean mAsc = true;
	private List<?> mResult;

	public InternalPaginationBuilder setCapacity(final long pCapacity) {
		mCapacity = pCapacity;
		if (mCapacity <= 0) {
			mCapacity = 5;
		}
		return this;
	}

	public InternalPaginationBuilder setCurrentIndex(final long pCurrentIndex) {
		mCurrentIndex = pCurrentIndex;
		if (mCurrentIndex < 0) {
			mCurrentIndex = 0;
		}
		return this;
	}

	public InternalPaginationBuilder setCount(final long pCount) {
		mCount = pCount;
		return this;
	}

	public InternalPaginationBuilder setKeyword(final String pKeyword) {
		mKeyword = pKeyword;
		return this;
	}

	public InternalPaginationBuilder setSortFieldName(final String pSortFieldName) {
		mSortFieldName = pSortFieldName;
		return this;
	}

	public InternalPaginationBuilder setAsc(final boolean pAsc) {
		mAsc = pAsc;
		return this;
	}

	public InternalPaginationBuilder setResult(final List<?> pResult) {
		mResult = pResult;
		return this;
	}

	public InternalPagination createInternalPagination() {
		return new InternalPagination(mCapacity, mCurrentIndex, mCount, mKeyword, mSortFieldName, mAsc, mResult);
	}
	public static InternalPagination createDefaultInternalPagination() {
		return new InternalPagination(20l, 0l, 0l, null, null, true, null);
	}
}