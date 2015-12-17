package com.pgt.shipping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.bean.Store;
import com.pgt.address.service.AddressInfoService;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.shipping.bean.ShippingAddress;
import com.pgt.shipping.bean.ShippingMethod;
import com.pgt.shipping.bean.ShippingVO;
import com.pgt.shipping.service.ShippingService;
import com.pgt.user.bean.User;

/**
 * @author ethanli
 *
 */
@RestController
@RequestMapping("/checkout")
public class ShippingController {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(ShippingController.class);
	@Autowired
	private URLConfiguration	urlConfiguration;
	@Autowired
	private AddressInfoService	addressInfoService;
	@Autowired
	private ShippingService		shippingService;
	@Autowired
	private ShoppingCartService	shoppingCartService;
	@Autowired
	private CityService			cityService;

	@RequestMapping(value = "/shipping", method = { RequestMethod.GET })
	public ModelAndView shipping(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			String redirectUrl = "redirect:" + urlConfiguration.getLoginPage() + "?redirect="
					+ urlConfiguration.getShippingPage();
			mav.setViewName(redirectUrl);
			user = new User();
			mav.addObject(user);
			return mav;
		}
		Order order = (Order) session.getAttribute(CartConstant.CURRENT_ORDER);
		if (order == null || order.getCommerceItemCount() == 0) {
			String redirectUrl = "redirect:" + urlConfiguration.getShoppingCartPage();
			mav.setViewName(redirectUrl);
			return mav;
		}
		if (order.getShippingVO() == null) {
			ShippingVO shipping = getShippingService().findShippingByOrderId(String.valueOf(order.getId()));
			order.setShippingVO(shipping);
		}
		List<AddressInfo> addressInfoList = getAddressInfoService().queryAddressByUserId(user.getId().intValue());
		if (user.getDefaultAddressId() != null) {
			AddressInfo defaultAddress = getAddressInfoService().findAddress(user.getDefaultAddressId());
			mav.addObject("defaultAddress", defaultAddress);
		}
		List<Integer> productIds = getShippingService().getProductIdsFromOrder(order);
		if (productIds != null && !productIds.isEmpty()) {
			List<Store> stores = getShippingService().findStoreByProductIds(productIds);
			mav.addObject("storeList", stores);
		}
		List<Province> provinceList = getCityService().getAllProvince();
		mav.addObject("provinceList", provinceList);
		mav.setViewName(urlConfiguration.getShippingPage());
		mav.addObject("addressInfoList", addressInfoList);
		return mav;
	}

	@RequestMapping(value = "/addAddressToOrder", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> addAddressToOrder(@RequestParam("addressInfoId") String addressInfoId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = (Order) session.getAttribute(CartConstant.CURRENT_ORDER);
		if (order == null) {
			LOGGER.error("Need create a order for current user firslty when add shipping address.");
			map.put("redirectUrl", urlConfiguration.getShoppingCartPage());
			map.put("success", "false");
			return map;
		}
		if (StringUtils.isBlank(addressInfoId)) {
			LOGGER.error("Need parameter 'addressInfoId' when adding address to order-{}.", order.getId());
			map.put("success", "false");
			return map;
		}
		AddressInfo addressInfo = getAddressInfoService().findAddress(Integer.parseInt(addressInfoId));
		ShippingAddress address = getShippingService().copyAddress(addressInfo);
		getShippingService().addAddress(address, order);
		map.put("success", "true");
		// map.put("savedAddress", address.getProvince() + address.getCity() +
		// address.getDistrict() + " "
		// + address.getAddress() + " " + address.getPhone() + " " +
		// address.getName());
		return map;
	}

	@RequestMapping(value = "/addPickup", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> addPickup(@Validated ShippingMethod shippingMethod, BindingResult bindingResult,
			HttpSession session) {
		LOGGER.debug("Starting to add a pick up shipping.");
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = (Order) session.getAttribute(CartConstant.CURRENT_ORDER);
		if (order == null) {
			LOGGER.error("Need create a order for current user firslty when adding pick up shipping.");
			map.put("redirectUrl", urlConfiguration.getShoppingCartPage());
			map.put("success", "false");
			return map;
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			map.put("success", "false");
			map.put("errors", errors);
			return map;
		}
		getShippingService().addShippingMethod(shippingMethod, order);
		map.put("success", "true");
		return map;
	}

	@RequestMapping(value = "/redirectToPayment", method = RequestMethod.GET)
	public ModelAndView redirectToPayment(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Order order = (Order) session.getAttribute(CartConstant.CURRENT_ORDER);
		if (order == null) {
			LOGGER.error("Need create a order for current user firslty when add shipping address.");
			mav.setViewName("redirect:" + urlConfiguration.getShoppingCartPage());
			return mav;
		}
		if (!hasShippingOnOrder(order)) {
			mav.setViewName("redirect:" + urlConfiguration.getShippingPage());
			return mav;
		} else {
			order.setStatus(OrderStatus.FILLED_SHIPPING);
			getShoppingCartService().updateOrder(order);
		}
		mav.setViewName("redirect:/payment/gateway");
		return mav;
	}

	public boolean hasShippingOnOrder(Order order) {
		if (order == null) {
			return false;
		}
		if (order.getShippingVO() == null) {
			return false;
		}
		if (order.getShippingVO().getShippingAddress() != null) {
			return true;
		}
		if (order.getShippingVO().getShippingMethod() != null) {
			return true;
		}
		return false;
	}

	public AddressInfoService getAddressInfoService() {
		return addressInfoService;
	}

	public void setAddressInfoService(AddressInfoService addressInfoService) {
		this.addressInfoService = addressInfoService;
	}

	public ShippingService getShippingService() {
		return shippingService;
	}

	public void setShippingService(ShippingService shippingService) {
		this.shippingService = shippingService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public ShoppingCartService getShoppingCartService() {
		return shoppingCartService;
	}

	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

}
