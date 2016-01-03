package com.pgt.utils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * 
 * Example:
 * Please refer main method.
 * 
 * first page currentIndex is 0.
 * 
 * @author Samli
 *
 */
public class PaginationBean implements Mapable {

	public static final int DEFAULT_CAPACITY = 5;
	public static final int DEFAULT_SPAN_SIZE = 2;

	private long capacity;
	private long currentIndex;// start from 0;
	private long totalAmount;
	private String sortFiledName;
	private boolean asc;
	private int spanSize = DEFAULT_SPAN_SIZE;

	public int getSpanSize() {
		return spanSize;
	}

	public void setSpanSize(int spanSize) {
		this.spanSize = spanSize;
	}

	public boolean isFirstPage() {
		return currentIndex == 0;
	}


	public boolean isLastPage() {
		return currentIndex == getMaxIndex();
	}


	public boolean isShowCurrentPage() {
		if (isFirstPage() && isLastPage()) {
			return true;
		}
		if (isFirstPage()) {
			return false;
		}
		if (isLastPage()) {
			return false;
		}
		return true;
 	}


	public List<Long> getLeftSpan() {
		List<Long> result = new ArrayList<Long>();
		if (currentIndex > 0) {
			for (long i = currentIndex - 1; i > 0; i--) {
				if (currentIndex - i > spanSize) {
					break;
				}
				result.add(i);
			}
			Collections.reverse(result);
		}
		return result;
	}

	public List<Long> getRightSpan() {
		List<Long> result = new ArrayList<Long>();
		if (currentIndex < getMaxIndex()) {
			for (long i = currentIndex + 1; i < getMaxIndex(); i++) {
				if (i == 0 || i - currentIndex > spanSize) {
					break;
				}
				result.add(i);
			}
		}
		return result;
	}

	public boolean isShowLeftDots() {
		if (currentIndex - getSpanSize() > 0) {
			return true;
		}
		return false;
	}


	public boolean isShowRightDots() {
		if (currentIndex + getSpanSize() < getMaxIndex()) {
			return true;
		}
		return false;
	}

	public long getNextIndex() {
		if (0 >= capacity) {
			throw new InvalidParameterException("capacity must greater than zero.");
		}
		long maxIndex = (totalAmount % capacity > 0 ? totalAmount / capacity + 1 : totalAmount / capacity) - 1;
		if (currentIndex > maxIndex) {
			return maxIndex;
		}
		return maxIndex > currentIndex ? currentIndex + 1 : currentIndex;
	}

	public long getPreIndex() {
		if (0 >= capacity) {
			throw new InvalidParameterException("capacity must greater than zero.");
		}
		long maxIndex = getMaxIndex();
		if (currentIndex > maxIndex) {
			return maxIndex > 0 ? maxIndex - 1 : maxIndex;
		}
		if (currentIndex <= 0) {
			return currentIndex;
		}
		return currentIndex - 1;
	}
	
	public long getMaxIndex() {
		return (totalAmount % capacity > 0 ? totalAmount / capacity + 1 : totalAmount / capacity) - 1;
	}

	public long getSqlStartIndex() {
		return currentIndex * capacity;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public long getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(long currentIndex) {
		this.currentIndex = currentIndex;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	public Map<String, Object> getMapValue() {
		try {
			return BeanUtils.toMap(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			return new HashMap<String, Object>();
		}
	}

	public static void main(String[] args) {
		// 0-6
		// 7-13
		// 13-20
		// 20-27

		PaginationBean page = new PaginationBean();
		page.setTotalAmount(15);
		page.setCurrentIndex(0);
		page.setCapacity(7);
		page.setSortFiledName("NAME");
		System.out.println(page.getCurrentIndex());
		long currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getNextIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);


		System.out.println("------------------------");
		page.setTotalAmount(15);
		page.setCurrentIndex(3);
		page.setCapacity(7);
		currentIndex = page.getCurrentIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		currentIndex = page.getPreIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getPreIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getPreIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getPreIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		currentIndex = page.getPreIndex();
		System.out.println(currentIndex + " -- " + page.getSqlStartIndex());
		page.setCurrentIndex(currentIndex);
		Map<String, Object> value = page.getMapValue();
		for (String key: value.keySet()) {
			System.out.println(key + ": " + value.get(key));
		}

		System.out.println("------------------------------");
		PaginationBean bean = new PaginationBean();
		bean.setTotalAmount(50);
		bean.setCapacity(7);
		bean.setCurrentIndex(0);
	}

	public String getSortFiledName() {
		return sortFiledName;
	}

	public void setSortFiledName(String sortFiledName) {
		this.sortFiledName = sortFiledName;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
