package com.pgt.link.service;

import com.pgt.link.bean.FriendlyLink;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-24.
 */
public interface FriendlyLinkService {

    void createFriendlyLink(FriendlyLink friendlyLink);

    void updateFriendlyLink(FriendlyLink friendlyLink);

    void deleteHotFriendlyLinkById(Integer id);

    FriendlyLink queryFriendlyLinkById(Integer id);

    List<FriendlyLink> queryFriendlyLinkByQuery(FriendlyLink friendlyLink);


}
