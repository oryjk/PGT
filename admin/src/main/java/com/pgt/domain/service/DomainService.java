package com.pgt.domain.service;

import com.pgt.domain.bean.Domain;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fei on 2016/3/17.
 */
@Service
public interface DomainService {
    Integer create(Domain domain);
    List<Domain> selectDomain(Domain domain);
}
