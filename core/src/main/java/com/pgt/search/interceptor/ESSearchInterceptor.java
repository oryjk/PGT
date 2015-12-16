package com.pgt.search.interceptor;

import com.pgt.search.bean.ESSort;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaodong on 15-12-15.
 */
public class ESSearchInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String sortKey =request.getParameter("sortKey");
        String sortOrder = request.getParameter("sortOrder");
        //获取session中的排序关键字
        List<ESSort> esSortList = (List<ESSort>) request.getSession().getAttribute("esSortList");
        //如果这次请求不含有排序的关键字，并且session中有上一次排序的关键字，则清空session中的排序数据
        if(sortKey==null){
            if(esSortList!=null) {
                request.getSession().removeAttribute("esSortList");
            }
             return true;
        }
        String url = request.getRequestURI();
        String newSortUrl =url.substring(0,url.indexOf("sortKey"));
        String oldSortUrl = (String) request.getSession().getAttribute("sortUrl");

        if(StringUtils.isEmpty(oldSortUrl)){
            request.getSession().setAttribute("sortUrl",oldSortUrl);
            if(esSortList!=null){
                request.getSession().removeAttribute("esSortList");
            }
            esSortList = new ArrayList<>();
            ESSort esSort = new ESSort();
            esSort.setPropertyName(sortKey);
            // set default value first.
            esSort.setSortOrder(SortOrder.DESC);
            if (sortOrder != null) {

                if(sortOrder=="ASC"){
                    esSort.setSortOrder(SortOrder.ASC);
                }else{
                    esSort.setSortOrder(SortOrder.DESC);
                }

            }
            esSortList.add(esSort);
            request.setAttribute("esSortList",esSortList);
        }else {

            if (newSortUrl.endsWith(oldSortUrl)) {
                //如果新请求的路径和上次session中排序的其他搜索参数相同，则在list中增加排序的条件
                ESSort esSort = new ESSort();
                esSort.setPropertyName(sortKey);
                // set default value first.
                esSort.setSortOrder(SortOrder.DESC);
                if (sortOrder != null) {

                    if(sortOrder=="ASC"){
                        esSort.setSortOrder(SortOrder.ASC);
                    }else{
                        esSort.setSortOrder(SortOrder.DESC);
                    }

                }
                if (esSortList == null) {
                    esSortList = new ArrayList<>();
                }
                esSortList.add(esSort);
                request.setAttribute("esSortList",esSortList);
            } else {
                //如果不相同，清空排序条件，并重新设置条件则清空则存放此次的路径
                request.getSession().removeAttribute("esSortList");
                request.getSession().setAttribute("sortUrl",newSortUrl);
                esSortList = new ArrayList<>();
                ESSort esSort = new ESSort();
                esSort.setPropertyName(sortKey);
                // set default value first.
                esSort.setSortOrder(SortOrder.DESC);
                if (sortOrder != null) {
                    if(sortOrder=="ASC"){
                        esSort.setSortOrder(SortOrder.ASC);
                    }else{
                        esSort.setSortOrder(SortOrder.DESC);
                    }

                }
                esSortList.add(esSort);
                request.setAttribute("esSortList",esSortList);
            }


        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
