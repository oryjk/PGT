package com.pgt.help.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.help.bean.News;

/**
 * 
 * @author xyf on 2015-11-20
 */
public interface NewsMapper extends SqlMapper {

	public List<News> findAll();

	public void save(News news);

	public void deleteById(Integer id);

	public void update(News news);

	public News findById(Integer id);
}
