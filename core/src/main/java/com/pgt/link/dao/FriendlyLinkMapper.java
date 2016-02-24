package com.pgt.link.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.link.bean.FriendlyLink;
import org.elasticsearch.common.collect.HppcMaps;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-24.
 */
@Component
public interface FriendlyLinkMapper extends SqlMapper {


    void createFriendlyLink(FriendlyLink friendlyLink);

    void updateFriendlyLink(FriendlyLink friendlyLink);

    void deleteHotFriendlyLinkById(Integer id);

    FriendlyLink queryFriendlyLinkById(Integer id);

    List<FriendlyLink> queryFriendlyLinkByQuery(FriendlyLink friendlyLink);

}
