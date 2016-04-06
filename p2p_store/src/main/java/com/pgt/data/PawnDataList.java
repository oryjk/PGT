package com.pgt.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiang on 16-4-6.
 */
@Component
@Scope("singleton")
public class PawnDataList {
    public List<Map<String,Object>> pawnDataList;

    public List<Map<String, Object>> getPawnDataList() {
        return pawnDataList;
    }

    public void setPawnDataList(List<Map<String, Object>> pawnDataList) {
        this.pawnDataList = pawnDataList;
    }
}
