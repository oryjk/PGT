package com.pgt.help.service;

import java.util.List;

import com.pgt.help.bean.News;

/**
 * 
 * @author xyf on 2015-11-20
 */
public interface NewsService {
	public List<News> findAllNews();

	public void saveNews(String title, String content);

	public void deleteNewsById(Integer id);

	public News findNewsById(Integer id);

	public void updateNews(Integer id, String title, String content);

}
