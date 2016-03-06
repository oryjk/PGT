package com.pgt.cart.controller;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ShoppingCartConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Yove on 10/28/2015.
 */
public class TransactionBaseController {

	protected static final String REDIRECT = "redirect:";

	protected static final String REDIRECT_LOGIN = "redirect:/user/login?redirect=";

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionBaseController.class);

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Autowired
	private ReloadableResourceBundleMessageSource mMessageSource;

	@Resource(name = "shoppingCartConfiguration")
	private ShoppingCartConfiguration mShoppingCartConfiguration;

	protected TransactionStatus ensureTransaction () {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	protected TransactionStatus ensureTransaction (int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = getTransactionManager().getTransaction(def);
		def.setPropagationBehavior(pPropagationBehavior);
		return status;
	}

	protected int getDefaultOrderType () {
		return getShoppingCartConfiguration().getDefaultOrderType();
	}

	protected int getMaxItemCount () {
		return getShoppingCartConfiguration().getMaxItemCount4Cart();
	}

	protected User getCurrentUser (HttpServletRequest pRequest) {
		return (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
	}

	protected Order getCurrentOrder (HttpServletRequest pRequest, boolean pCreateIfAbsent) {
		User currentUser = getCurrentUser(pRequest);

		if (currentUser == null) {
			// get order from session if it's anonymous user
			Order order = (Order) pRequest.getSession().getAttribute(CartConstant.CURRENT_ORDER);
			if (pCreateIfAbsent && order == null) {
				order = new Order();
				pRequest.getSession().setAttribute(CartConstant.CURRENT_ORDER, order);
			}
			return order;
		} else {
			// login user will ignore session order but use request order
			Order order = (Order) pRequest.getAttribute(CartConstant.CURRENT_ORDER);
			if (pCreateIfAbsent && order == null) {
				LOGGER.debug("Get empty order from session, re-generate order.");
				order = new Order(getDefaultOrderType());
				if (currentUser != null && currentUser.getId() != null) {
					order.setUserId(currentUser.getId().intValue());
				}
				pRequest.setAttribute(CartConstant.CURRENT_ORDER, order);
			}
			return order;
		}
	}

	protected String getMessageValue (String pKey, String pDefaultMessage) {
		if (StringUtils.isNotBlank(pKey)) {
			return mMessageSource.getMessage(pKey, null, pDefaultMessage, Locale.getDefault());
		} else {
			return StringUtils.EMPTY;
		}
	}

	protected String getRedirectView (String pViewName) {
		return new StringBuilder(REDIRECT).append(pViewName).toString();
	}

	public DataSourceTransactionManager getTransactionManager () {
		return mTransactionManager;
	}

	public void setTransactionManager (final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

	public ReloadableResourceBundleMessageSource getMessageSource () {
		return mMessageSource;
	}

	public void setMessageSource (final ReloadableResourceBundleMessageSource pMessageSource) {
		mMessageSource = pMessageSource;
	}

	public ShoppingCartConfiguration getShoppingCartConfiguration () {
		return mShoppingCartConfiguration;
	}

	public void setShoppingCartConfiguration (final ShoppingCartConfiguration pShoppingCartConfiguration) {
		mShoppingCartConfiguration = pShoppingCartConfiguration;
	}
}
