package com.pgt.domain.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.domain.bean.Domain;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fei on 2016/3/17.
 */
@Component
public interface DomainMapper extends SqlMapper{
    Integer create(Domain domain);
    List<Domain> selectDomain(Domain domain);
}
