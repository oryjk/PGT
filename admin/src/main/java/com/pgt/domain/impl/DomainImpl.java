package com.pgt.domain.impl;

import com.pgt.domain.bean.Domain;
import com.pgt.domain.dao.DomainMapper;
import com.pgt.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fei on 2016/3/17.
 */
@Component
public class DomainImpl implements DomainService{
    @Autowired
    DomainMapper domainDao;

    public  Integer create(Domain domain){
        try {
            domainDao.create(domain);
            return domain.getId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  0;
    }

    public List<Domain> selectDomain(Domain domain){
       return domainDao.selectDomain(domain);
    }
}
