package com.pgt.data;

import com.pgt.category.bean.Category;
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
    public List<Category> pawnDataList;
    public List<Category> getPawnDataList() {
        return pawnDataList;
    }
    public void setPawnDataList(List<Category> pawnDataList) {
        this.pawnDataList = pawnDataList;
    }
}
