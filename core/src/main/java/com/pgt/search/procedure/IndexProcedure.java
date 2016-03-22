package com.pgt.search.procedure;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Created by carlwang on 3/20/16.
 */
public interface IndexProcedure {

    List<Pair<String,String>> buildSourceList();
}
