package com.pgt.inventory.service;

import com.pgt.search.service.TenderSearchEngineService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carlwang on 3/10/16.
 */
@Service
public class TenderInventoryService extends InventoryService {
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;

    @Override
    public void modifyProductInventoryIndex(List<Pair<Integer, Integer>> inventoryStatistic) {
        tenderSearchEngineService.modifyProductInventory(inventoryStatistic);
    }
}
