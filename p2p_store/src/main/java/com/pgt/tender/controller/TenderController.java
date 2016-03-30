package com.pgt.tender.controller;

import com.pgt.cart.bean.Favourite;
import com.pgt.cart.bean.FavouriteType;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.UserFavouriteService;
import com.pgt.constant.UserConstant;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.user.bean.User;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 2/22/16.
 */

@RequestMapping("/tender")
@RestController
public class TenderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TenderController.class);

	@Autowired
	private TenderSearchEngineService tenderSearchEngineService;

	@Resource(name = "userFavouriteService")
	private UserFavouriteService mFavouriteService;

	@RequestMapping(value = "/{tenderId}", method = RequestMethod.GET)
	public ModelAndView getTenderDetailPage(@PathVariable("tenderId") Integer tenderId, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelAndView modelAndView) {

		LOGGER.debug("The method query tenderDetail");
		if (ObjectUtils.isEmpty(tenderId)) {
			LOGGER.debug("the tenderId is empty");
			return modelAndView;
		}
		SearchResponse tenderResponse = tenderSearchEngineService.findTenderById(tenderId);
		SearchHit[] tenders = tenderResponse.getHits().getHits();
		if (ObjectUtils.isEmpty(tenders)) {
			LOGGER.debug("The tender is exist");
			return modelAndView;
		}
		Map ESTender = tenders[0].getSource();
		LOGGER.debug("The query tender id is {}.", tenderId);
		modelAndView.addObject("ESTender", ESTender);
		modelAndView.setViewName("/detail/detail");

		// query favourites
		User currentUser = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (currentUser != null) {
			List<Favourite> tenderFavourites = getFavouriteService().queryFavourites(currentUser.getId().intValue(), FavouriteType.P2P_TENDER);
			if (CollectionUtils.isNotEmpty(tenderFavourites)) {
				modelAndView.addObject(CartConstant.TENDER_FAVOURITES, tenderFavourites);
			}
			List<Favourite> productFavourites = getFavouriteService().queryFavourites(currentUser.getId().intValue(), FavouriteType.P2P_PRODUCT);
			if (CollectionUtils.isNotEmpty(productFavourites)) {
				modelAndView.addObject(CartConstant.PROD_FAVOURITES, productFavourites);
			}
		}
		return modelAndView;
	}

	public UserFavouriteService getFavouriteService() {
		return mFavouriteService;
	}

	public void setFavouriteService(final UserFavouriteService pFavouriteService) {
		mFavouriteService = pFavouriteService;
	}
}