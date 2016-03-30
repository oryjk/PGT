package com.tencent.service;

import com.pgt.integration.wechat.bean.UnifiedOrderReqData;
import com.tencent.common.Configure;

/**
 * Created by Administrator on 2016/3/23.
 */
public class UnifiedOrderService  extends BaseService{

    public UnifiedOrderService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.UNIFIED_ORDER);
    }

    /**
     * 请求支付服务
     * @param reqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderReqData reqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(reqData);

        return responseString;
    }
}
