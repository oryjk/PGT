package com.pgt.help.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pgt.help.bean.News;
import com.pgt.help.dao.NewsMapper;
import com.pgt.help.service.NewsService;
/**
 * 
 * @author xyf
 * on 2015-11-20
 */
@Service
public class NewsServiceImp implements NewsService{
	private News news;
	private NewsMapper newsDao;

	public NewsMapper getNewsDao() {
		return newsDao;
	}

	@Autowired
	public void setNewsDao(NewsMapper newsDao) {
		this.newsDao = newsDao;
	}
    /**
     * 
     * @return 返回当前系统时间的字符串
     */
	public String now() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date).toString();

	}

	public List<News> findAllNews() {
		return newsDao.findAll();
	}

	public void saveNews(String title, String content) {
		news = new News();
		news.setContent(content);
		news.setTitle(title);

		news.setCreateTime(now());
		newsDao.save(news);
	}

	public void deleteNewsById(Integer id) {
		newsDao.deleteById(id);
	}

	public News findNewsById(Integer id) {
		return newsDao.findById(id);

	}

	public void updateNews(Integer id, String title, String content) {
		news = newsDao.findById(id);

		news.setTitle(title);
		news.setContent(content);
		news.setCreateTime(now());
		newsDao.update(news);
	}

}
