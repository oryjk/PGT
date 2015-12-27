package com.pgt.cart.bean.pagination;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yove on 10/24/2015.
 */
public class InternalPagination {

    private static final int PAGE_NUMBER_OFFSET = 3;

    private long mCapacity;
    private long mCurrentIndex;// start from 0;
    private long mCount;
    private String mKeyword;
    private String mSortFieldName;
    private boolean mAsc = true;
    private boolean mInvalidPagination = false;
    private List<?> mResult;

    public InternalPagination() {
    }

    public InternalPagination(final long pCapacity, final long pCurrentIndex, final long pCount, final String pKeyword, final String
            pSortFieldName, final boolean pAsc, final List<?> pResult) {
        mCapacity = pCapacity;
        mCurrentIndex = pCurrentIndex;
        mCount = pCount;
        mKeyword = pKeyword;
        mSortFieldName = pSortFieldName;
        mAsc = pAsc;
        mResult = pResult;
    }

    public long getNextIndex() {
        if (0 >= mCapacity) {
            throw new InvalidParameterException("mCapacity must greater than zero.");
        }
        long maxIndex = (mCount % mCapacity > 0 ? mCount / mCapacity + 1 : mCount / mCapacity) - 1;
        if (mCurrentIndex > maxIndex) {
            return maxIndex;
        }
        return maxIndex > mCurrentIndex ? mCurrentIndex + 1 : mCurrentIndex;
    }

    public long getPreIndex() {
        if (0 >= mCapacity) {
            throw new InvalidParameterException("mCapacity must greater than zero.");
        }
        long maxIndex = getMaxIndex();
        if (mCurrentIndex > maxIndex) {
            return maxIndex > 0 ? maxIndex - 1 : maxIndex;
        }
        if (mCurrentIndex <= 0) {
            return mCurrentIndex;
        }
        return mCurrentIndex - 1;
    }

    public long getFirstRecordIndex() {
        if (0 >= mCapacity) {
            throw new InvalidParameterException("mCapacity must greater than zero.");
        }
        long first = mCurrentIndex * mCapacity + 1;
        if (first > mCount) {
            return mCount;
        }
        return first;
    }

    public long getLastRecordIndex() {
        if (0 >= mCapacity) {
            throw new InvalidParameterException("mCapacity must greater than zero.");
        }
        long last = (mCurrentIndex + 1) * mCapacity + 1;
        if (last > getMaxIndex() + 1) {
            return getMaxIndex() + 1;
        }
        return last;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("InternalPagination{").append("mCapacity=").append(mCapacity).append(", mCurrentIndex=").append(mCurrentIndex).append(", mCount=").append(mCount)
                .append(", mKeyword='").append(mKeyword).append('\'').append(", mSortFieldName='").append(mSortFieldName).append('\'').append(", mAsc=").append(mAsc).append(", mInvalidPagination=")
                .append(mInvalidPagination).append('}').toString();
    }

    public List<Long> getPageNumbers() {
        long startPage = getCurrentIndex() - PAGE_NUMBER_OFFSET;
        if (startPage < 0l) {
            startPage = 0l;
        }
        long endPage = getCurrentIndex() + PAGE_NUMBER_OFFSET;
        if (endPage > getMaxIndex()) {
            endPage = getMaxIndex();
        }
        int maxPageCount = PAGE_NUMBER_OFFSET * 2 + 1;
        List<Long> pageNumbers = new ArrayList<>(maxPageCount);
        for (int i = new Long(startPage).intValue(); i <= endPage; i++) {
            if (pageNumbers.size() == maxPageCount) {
                break;
            }
            pageNumbers.add(i + startPage);
        }
        return pageNumbers;
    }

    public long getMaxIndex() {
        return (mCount % mCapacity > 0 ? mCount / mCapacity + 1 : mCount / mCapacity) - 1;
    }

    public long getSqlStartIndex() {
        return mCurrentIndex * mCapacity;
    }

    public long getCapacity() {
        return mCapacity;
    }

    public void setCapacity(final long pCapacity) {
        mCapacity = pCapacity;
    }

    public long getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCurrentIndex(final long pCurrentIndex) {
        mCurrentIndex = pCurrentIndex;
    }

    public long getCount() {
        return mCount;
    }

    public void setCount(final long pCount) {
        mCount = pCount;
    }

    public String getKeyword() {
        return mKeyword;
    }

    public void setKeyword(final String pKeyword) {
        mKeyword = pKeyword;
    }

    public String getSortFieldName() {
        return mSortFieldName;
    }

    public void setSortFieldName(final String pSortFieldName) {
        mSortFieldName = pSortFieldName;
    }

    public boolean isAsc() {
        return mAsc;
    }

    public void setAsc(final boolean pAsc) {
        mAsc = pAsc;
    }

    public boolean isInvalidPagination() {
        return mInvalidPagination;
    }

    public void setInvalidPagination(final boolean pInvalidPagination) {
        mInvalidPagination = pInvalidPagination;
    }

    public List<?> getResult() {
        return mResult;
    }

    public void setResult(final List<?> pResult) {
        mResult = pResult;
    }
}
