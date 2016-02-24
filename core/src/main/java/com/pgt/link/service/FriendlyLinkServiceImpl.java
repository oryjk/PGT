package com.pgt.link.service;

import com.pgt.link.bean.FriendlyLink;
import com.pgt.link.dao.FriendlyLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-24.
 */
@Service
@Transactional
public class FriendlyLinkServiceImpl implements  FriendlyLinkService {

    @Autowired
    private FriendlyLinkMapper friendlyLinkMapper;

    @Override
    public void createFriendlyLink(FriendlyLink friendlyLink) {
        friendlyLinkMapper.createFriendlyLink(friendlyLink);
    }

    @Override
    public void updateFriendlyLink(FriendlyLink friendlyLink) {
        friendlyLinkMapper.updateFriendlyLink(friendlyLink);
    }

    @Override
    public void deleteHotFriendlyLinkById(Integer id) {
           friendlyLinkMapper.deleteHotFriendlyLinkById(id);
    }

    @Override
    public FriendlyLink queryFriendlyLinkById(Integer id) {
        return friendlyLinkMapper.queryFriendlyLinkById(id);
    }

    @Override
    public List<FriendlyLink> queryFriendlyLinkByQuery(FriendlyLink friendlyLink) {
        return friendlyLinkMapper.queryFriendlyLinkByQuery(friendlyLink);
    }


}
