package com.pgt.cart.filter;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderType;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.PriceOrderService;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.constant.UserConstant;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 10/27/2015.
 */
public class ShoppingCartHandlerInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartHandlerInterceptor.class);

    @Resource(name = "shoppingCartService")
    private ShoppingCartService mShoppingCartService;

    @Resource(name = "priceOrderService")
    private PriceOrderService mPriceOrderService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        // check session user existence
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        // no order for user without login state
        if (user == null) {
            LOGGER.debug("No user found from session, skip merge order.");
            return true;
        }
        LOGGER.debug("User: {} found from session.", user.getId());

        Order so = (Order) request.getSession().getAttribute(CartConstant.CURRENT_ORDER);
        // session order existence
        if (so != null) {
            LOGGER.debug("Session order found.");
            so.setUserId(user.getId().intValue());
            // login user with persisted order
            if (RepositoryUtils.idIsValid(so.getId())) {
                LOGGER.debug("Session order found with valid id: {}, skip merge order.", so.getId());
                return true;
            }
        }

        // get the persist order in initial status
        Order po = getShoppingCartService().loadInitialOrder(user.getId().intValue());
        // no available order
        if (po == null) {
            LOGGER.debug("Load initial order failed for user: {}", user.getId());
            // session order is empty too
            if (so == null) {
                // let's create a new order and put to session
                so = new Order(user.getId().intValue(), OrderType.B2C_ORDER);
            }
            getShoppingCartService().persistInitialOrder(so);
            request.getSession().setAttribute(CartConstant.CURRENT_ORDER, so);
            LOGGER.debug("Persist session order: {} for user: {}", so.getId(), user.getId());
        } else {
            LOGGER.debug("Load initial order: {} for user: {}", po.getId(), user.getId());
            if (so != null && !so.emptyOrder()) {
                // merge items to persisted order from session order if session order is not an empty order
                getShoppingCartService().mergeOrder(so, po);
                LOGGER.debug("Merged session order: {} and persisted order: {}", so.getId(), po.getId());
                getPriceOrderService().priceOrder(po);
                LOGGER.debug("Repriced order: {}", po.getId());
                getShoppingCartService().persistInitialOrder(po);
                LOGGER.debug("Persist initial order: {}", po.getId());
            }
            request.getSession().setAttribute(CartConstant.CURRENT_ORDER, po);
        }
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                                final Exception ex) throws Exception {

    }


    public ShoppingCartService getShoppingCartService() {
        return mShoppingCartService;
    }

    public void setShoppingCartService(final ShoppingCartService pShoppingCartService) {
        mShoppingCartService = pShoppingCartService;
    }

    public PriceOrderService getPriceOrderService() {
        return mPriceOrderService;
    }

    public void setPriceOrderService(final PriceOrderService pPriceOrderService) {
        mPriceOrderService = pPriceOrderService;
    }
}
