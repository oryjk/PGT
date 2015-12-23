package com.pgt.inventory.scheduling;


import com.pgt.inventory.service.InventoryService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by Administrator on 2015/12/23.
 */
public class InventoryReleaseJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // TODO LOG
        InventoryService inventoryService = (InventoryService)jobExecutionContext.get("inventoryService");
        inventoryService.releaseInventories();
    }
}
