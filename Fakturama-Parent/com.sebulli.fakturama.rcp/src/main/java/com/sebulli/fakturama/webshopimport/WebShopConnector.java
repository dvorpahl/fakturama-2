/**
 * 
 */
package com.sebulli.fakturama.webshopimport;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.HttpEntity;

/**
 * WebShopConfigVO contains all the necessary information
 * for connection to a web shop.
 *
 */
public class WebShopConnector {
    /**
     * Main URL for the shop.
     */
    protected String shopURL = "";
    protected String user;
    protected String password;
    protected Boolean useAuthorization;
    protected String authorizationUser;
    protected String authorizationPassword;

    /**
     * The URL for shop this.
     */
    protected String scriptURL;

    // Configuration of the web shop request
    private boolean getProducts, getOrders;

    // List of all orders which are out of sync with the web shop.
    private Properties orderstosynchronize = null;

    public HttpPost createPostRequest(final String action) {
        return createPostRequest(action, null);
    }

    public HttpPost createPostRequest(final String action, final Map<String, String> params) {
        final HttpPost httpPost = new HttpPost(this.getScriptURL());
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("username", this.getUser());
        builder.addTextBody("password", this.getPassword());
        builder.addTextBody("action", action);
        if (params != null && !params.isEmpty()) {
            params.forEach((key, value) -> builder.addTextBody(key, value));
        }

        final HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        if (this.getUseAuthorization() != null && this.getUseAuthorization().booleanValue()) {
            final String encodedPassword = Base64.getEncoder().encodeToString((this.getAuthorizationUser() + ":" + this.getAuthorizationPassword()).getBytes());
            httpPost.setHeader("Authorization", "Basic " + encodedPassword);
        }
        return httpPost;
    }

    public HttpGet createPublicGetRequest(final String url) {
        final HttpGet httpGet = new HttpGet(url);
        if (this.getUseAuthorization() != null && this.getUseAuthorization().booleanValue()) {
            final String encodedPassword = Base64.getEncoder().encodeToString((this.getAuthorizationUser() + ":" + this.getAuthorizationPassword()).getBytes());
            httpGet.setHeader("Authorization", "Basic " + encodedPassword);
        }
        return httpGet;
    }

    public URLConnection createConnection() throws IOException {
        URLConnection conn = null;
        final URL url = new URL(scriptURL);
        conn = url.openConnection();
        conn.setDoInput(true);
        conn.setConnectTimeout(4000);
        if (!scriptURL.toLowerCase().startsWith("file://")) {
            conn.setDoOutput(true);

            // Use password for password protected web shops
            if (useAuthorization) {
                final String encodedPassword = Base64.getEncoder().encodeToString((authorizationUser + ":" + authorizationPassword).getBytes());
                conn.setRequestProperty("Authorization", "Basic " + encodedPassword);
            }
        }
        return conn;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public WebShopConnector withUser(final String user) {
        this.user = user;
        return this;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public WebShopConnector withPassword(final String password) {
        this.password = password;
        return this;
    }

    /**
     * @return the useAuthorization
     */
    public Boolean getUseAuthorization() {
        return useAuthorization;
    }

    /**
     * @param useAuthorization
     *            the useAuthorization to set
     */
    public WebShopConnector withUseAuthorization(final Boolean useAuthorization) {
        this.useAuthorization = useAuthorization;
        return this;
    }

    /**
     * @return the authorizationUser
     */
    public String getAuthorizationUser() {
        return authorizationUser;
    }

    /**
     * @param authorizationUser
     *            the authorizationUser to set
     */
    public WebShopConnector withAuthorizationUser(final String authorizationUser) {
        this.authorizationUser = authorizationUser;
        return this;
    }

    /**
     * @return the authorizationPassword
     */
    public String getAuthorizationPassword() {
        return authorizationPassword;
    }

    /**
     * @param authorizationPassword
     *            the authorizationPassword to set
     */
    public WebShopConnector withAuthorizationPassword(final String authorizationPassword) {
        this.authorizationPassword = authorizationPassword;
        return this;
    }

    /**
     * @return the getProducts
     */
    public boolean isGetProducts() {
        return getProducts;
    }

    /**
     * @param getProducts
     *            the getProducts to set
     */
    public void setGetProducts(final boolean getProducts) {
        this.getProducts = getProducts;
    }

    /**
     * @return the getOrders
     */
    public boolean isGetOrders() {
        return getOrders;
    }

    /**
     * @param getOrders
     *            the getOrders to set
     */
    public void setGetOrders(final boolean getOrders) {
        this.getOrders = getOrders;
    }

    /**
     * @return the orderstosynchronize
     */
    public Properties getOrderstosynchronize() {
        return orderstosynchronize;
    }

    /**
     * @param orderstosynchronize
     *            the orderstosynchronize to set
     */
    public void setOrderstosynchronize(final Properties orderstosynchronize) {
        this.orderstosynchronize = orderstosynchronize;
    }

    /**
     * @return the scriptURL
     */
    public String getScriptURL() {
        return scriptURL;
    }

    /**
     * @param scriptURL
     *            the scriptURL to set
     */
    public void setShopURL(final String shopURL) {
        this.shopURL = shopURL;
    }

    /**
     * @return the shopURL
     */
    public String getShopURL() {
        return shopURL;
    }

    /**
     * @param shopURL
     *            the shopURL to set
     */
    public WebShopConnector withScriptURL(final String scriptURL) {
        this.scriptURL = scriptURL;
        return this;
    }

    /**
     * Prepare the web shop import to request products and orders.
     */
    public void prepareGetProductsAndOrders() {
        setGetProducts(true);
        setGetOrders(true);
    }

    /**
     * Prepare the web shop import to change the state of an order.
     */
    public void prepareChangeState() {
        setGetProducts(false);
        setGetOrders(false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
