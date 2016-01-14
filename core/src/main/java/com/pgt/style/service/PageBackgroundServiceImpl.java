package com.pgt.style.service;

import com.pgt.base.service.TransactionService;
import com.pgt.common.bean.Media;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;
import com.pgt.style.dao.PageBackgroundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by xiaodong on 16-1-13.
 */

@Service
public class PageBackgroundServiceImpl extends TransactionService implements PageBackgroundService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PageBackgroundServiceImpl.class);

    @Autowired
    private PageBackgroundMapper pageBackgroundMapper;

    @Autowired
    private MediaService mediaService;

    @Override
    public List<PageBackground> queryPageBackground(PageBackgroundQuery pageBackgroundQuery) {
        return pageBackgroundMapper.queryPageBackground(pageBackgroundQuery);
    }

    @Override
    public void deletePageBackgroundByKes(Integer[] ids) {
        pageBackgroundMapper.deletePageBackgroundByKes(ids);
    }

    @Override
    public void deletePageBackgroundById(Integer id) {

        TransactionStatus transactionStatus = ensureTransaction();
        try {
            Media headerMedia = mediaService.queryPageBackgroundHeaderMedia(id);
            if (!ObjectUtils.isEmpty( headerMedia)) {
                mediaService.deleteMedia( headerMedia.getId());
            }
            Media footerMedia = mediaService.queryPageBackgroundFooterMedia(id);
            if (!ObjectUtils.isEmpty(footerMedia)) {
                mediaService.deleteMedia(footerMedia.getId());
            }
            Media middleMedia = mediaService.queryPageBackgroundMiddleMedia(id);
            if (!ObjectUtils.isEmpty(middleMedia)) {
                mediaService.deleteMedia(middleMedia.getId());
            }
            pageBackgroundMapper.deletePageBackgroundById(id);
        } catch (Exception e) {
            LOGGER.error("Can not delete pageBackground");
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }

    }

    @Override
    public void updatePageBackground(PageBackground pageBackground) {
        pageBackgroundMapper.updatePageBackground(pageBackground);
    }

    @Override
    public void createPageBackground(PageBackground pageBackground) {
        pageBackgroundMapper.createPageBackground(pageBackground);
    }

    @Override
    public Integer queryPageBackgroundCount(PageBackgroundQuery pageBackgroundQuery) {
         return pageBackgroundMapper.queryPageBackgroundCount(pageBackgroundQuery);
    }
}
