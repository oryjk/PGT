package com.pgt.inventory.scheduling;


import com.pgt.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2015/12/23.
 */
public class InventoryReleaseJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryReleaseJob.class);
    private InventoryService inventoryService;


    public void work() {
        LOGGER.debug("-------------------- Start Job Inventory Release Job --------------------");
        try {
            getInventoryService().releaseInventories();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.debug("-------------------- end Job Inventory Release Job --------------------");
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
}
