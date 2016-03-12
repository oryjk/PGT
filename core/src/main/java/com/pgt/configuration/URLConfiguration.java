package com.pgt.configuration;

/**
 * Created by carlwang on 11/19/15.
 */
public class URLConfiguration {
    private String categoryPage;
    private String homePage;
    private String loginPage;
    private String logoutPage;
    private String registerPage;
    private String myAccountPage;
    private String addressBookPage;
    private String shoppingCartPage;
    private String shippingPage;
    private String pdpPage;
    private String plpPage;
    private String helpCenterPage;
    private String pawnPersonInfoPage;
    private String orderHistoryPage = "/myAccount/orderHistory";
    private String yeepayPage = "/user/yeepayAccountInfo";
    private String myaccountPage = "/userinformation/query";
    private String createTenderPage = "/tender/create";
    private String b2cStoreUrl = "";
    private String p2pStoreUrl = "";
    private String adminStoreUrl = "";
    private String mobileStoreUrl = "";

    public String getCategoryPage() {
        return categoryPage;
    }

    public String getPawnPersonInfoPage() {
        return pawnPersonInfoPage;
    }

    public void setPawnPersonInfoPage(String pawnPersonInfoPage) {
        this.pawnPersonInfoPage = pawnPersonInfoPage;
    }

    public void setCategoryPage(String categoryPage) {
        this.categoryPage = categoryPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLogoutPage() {
        return logoutPage;
    }

    public void setLogoutPage(String logoutPage) {
        this.logoutPage = logoutPage;
    }

    public String getRegisterPage() {
        return registerPage;
    }

    public void setRegisterPage(String registerPage) {
        this.registerPage = registerPage;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getMyAccountPage() {
        return myAccountPage;
    }

    public void setMyAccountPage(String myAccountPage) {
        this.myAccountPage = myAccountPage;
    }

    public String getAddressBookPage() {
        return addressBookPage;
    }

    public void setAddressBookPage(String addressBookPage) {
        this.addressBookPage = addressBookPage;
    }

    public String getShoppingCartPage() {
        return shoppingCartPage;
    }

    public void setShoppingCartPage(String shoppingCartPage) {
        this.shoppingCartPage = shoppingCartPage;
    }

    public String getShippingPage() {
        return shippingPage;
    }

    public void setShippingPage(String shippingPage) {
        this.shippingPage = shippingPage;
    }

    public String getPdpPage() {
        return pdpPage;
    }

    public void setPdpPage(String pdpPage) {
        this.pdpPage = pdpPage;
    }

    public String getPlpPage() {
        return plpPage;
    }

    public void setPlpPage(String plpPage) {
        this.plpPage = plpPage;
    }

    public String getHelpCenterPage() {
        return helpCenterPage;
    }

    public void setHelpCenterPage(String helpCenterPage) {
        this.helpCenterPage = helpCenterPage;
    }

    public String getOrderHistoryPage() {
        return orderHistoryPage;
    }

    public void setOrderHistoryPage(String orderHistoryPage) {
        this.orderHistoryPage = orderHistoryPage;
    }

    public String getYeepayPage() {
        return yeepayPage;
    }

    public void setYeepayPage(String yeepayPage) {
        this.yeepayPage = yeepayPage;
    }

    public String getMyaccountPage() {
        return myaccountPage;
    }

    public void setMyaccountPage(String myaccountPage) {
        this.myaccountPage = myaccountPage;
    }

    public String getCreateTenderPage() {
        return createTenderPage;
    }

    public void setCreateTenderPage(String createTenderPage) {
        this.createTenderPage = createTenderPage;
    }

    public String getB2cStoreUrl() {
        return b2cStoreUrl;
    }

    public void setB2cStoreUrl(String b2cStoreUrl) {
        this.b2cStoreUrl = b2cStoreUrl;
    }

    public String getP2pStoreUrl() {
        return p2pStoreUrl;
    }

    public void setP2pStoreUrl(String p2pStoreUrl) {
        this.p2pStoreUrl = p2pStoreUrl;
    }

    public String getAdminStoreUrl() {
        return adminStoreUrl;
    }

    public void setAdminStoreUrl(String adminStoreUrl) {
        this.adminStoreUrl = adminStoreUrl;
    }

    public String getMobileStoreUrl() {
        return mobileStoreUrl;
    }

    public void setMobileStoreUrl(String mobileStoreUrl) {
        this.mobileStoreUrl = mobileStoreUrl;
    }
}
