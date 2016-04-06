package com.pgt.procedure;

import com.pgt.search.procedure.IndexProcedure;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carlwang on 4/6/16.
 */
@Service
public class IndexProcedureImp implements IndexProcedure {
    @Override
    public List<Pair<String, String>> buildSourceList() {
        return null;
    }
}
