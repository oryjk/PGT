package com.pgt.inventory.scheduling;


import com.pgt.inventory.service.InventoryService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by Administrator on 2015/12/23.
 */
public class InventoryReleaseJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryReleaseJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.debug("-------------------- Start Job Inventory Release Job --------------------");
        try {
            InventoryService inventoryService = (InventoryService)jobExecutionContext.getMergedJobDataMap().get("inventoryService");
            inventoryService.releaseInventories();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.debug("-------------------- end Job Inventory Release Job --------------------");
    }
}
