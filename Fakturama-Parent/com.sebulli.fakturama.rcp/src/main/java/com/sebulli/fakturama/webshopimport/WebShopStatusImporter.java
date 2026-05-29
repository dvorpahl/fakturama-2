/**
 * 
 */
package com.sebulli.fakturama.webshopimport;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.webshopimport.type.ObjectFactory;
import com.sebulli.fakturama.webshopimport.type.Webshopexport;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.MarshalException;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Importer for the different WebShop states.
 */
public class WebShopStatusImporter implements IRunnableWithProgress {

    @Inject
    @Translation
    private Messages msg;

    //	private String productImagePath = "";
    private int worked = 0;

    private WebShopConnector connector;
    private ExecutionResult runResult;

    private IProgressMonitor localMonitor;
    private Webshopexport webshopexport = null;

    @Override
    public void run(final IProgressMonitor pMonitor) throws InvocationTargetException, InterruptedException {
        localMonitor = pMonitor;

        if (connector == null) {
            setRunResult("no connection information provided");
            return;
        }
        final String shopURL = connector.getScriptURL();

        // Check empty URL http://shop.fakturama.info/admin/fakturama2_connector.php
        if (shopURL.isEmpty()) {
            //T: Status message importing data from web shop
            setRunResult(msg.importWebshopErrorUrlnotset);
            return;
        }

        // Connect to web shop
        //T: Status message importing data from web shop
        localMonitor.beginTask(msg.importWebshopInfoConnection, 100);
        //T: Status message importing data from web shop
        localMonitor.subTask(msg.importWebshopInfoConnected + " " + shopURL);
        setProgress(10);
        // 1. We need to create JAXBContext instance

        try {
            // Send user name, password and a list of unsynchronized orders to
            // the shop
            HttpClientBuilder.create();
            if (!connector.getScriptURL().startsWith("file://")) {

                // create post Request with all necessary information in it
                final HttpPost httpPost = connector.createPostRequest("get_status");
                try (CloseableHttpClient client = HttpClients.createDefault(); CloseableHttpResponse response = client.execute(httpPost)) {
                    localMonitor.subTask(msg.importWebshopInfoLoading);
                    setProgress(20);
                    final JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { ObjectFactory.class }, null);
                    // 2. Use JAXBContext instance to create the Unmarshaller.
                    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                    webshopexport = (Webshopexport) unmarshaller.unmarshal(response.getEntity().getContent());
                } catch (final Exception e) {
                    setRunResult(msg.importWebshopErrorCantconnect);
                }
            } else {
                final URLConnection connection = connector.createConnection();
                if (connection != null) {
                    ((HttpURLConnection) connection).setRequestMethod("POST");
                    final String postString = new StringBuilder("username=").append(URLEncoder.encode(connector.getUser(), "UTF-8")).append("&password=")
                            .append(URLEncoder.encode(connector.getPassword(), "UTF-8")).append("&action=get_status").toString();
                    //this.webShopImportManager.log.debug("POST-String: " + postString);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(postString.length()));

                    final OutputStream outputStream = connection.getOutputStream();
                    final OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    setProgress(20);

                    writer.write(postString);
                    writer.flush();
                    writer.close();

                }

                setProgress(30);
                // Start a connection in an extra thread
                final InterruptConnection interruptConnection = new InterruptConnection(connection);
                new Thread(interruptConnection).start();
                while (!localMonitor.isCanceled() && !interruptConnection.isFinished() && !interruptConnection.isError()) {

                }

                // If the connection was interrupted and not finished: return
                if (!interruptConnection.isFinished()) {
                    ((HttpURLConnection) connection).disconnect();
                    if (interruptConnection.isError()) {
                        //T: Status error message importing data from web shop
                        setRunResult(msg.importWebshopErrorCantconnect);
                    }
                    return;
                }

                // If there was an error, return with error message
                if (interruptConnection.isError()) {
                    ((HttpURLConnection) connection).disconnect();
                    //T: Status message importing data from web shop
                    setRunResult(msg.importWebshopErrorCantread);
                    return;
                }
                final JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { ObjectFactory.class }, null);
                // 2. Use JAXBContext instance to create the Unmarshaller.
                final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                localMonitor.subTask(msg.importWebshopInfoLoading);
                webshopexport = (Webshopexport) unmarshaller.unmarshal(interruptConnection.getInputStream());

            }
            setProgress(40);
            // parse the XML stream
            if (!localMonitor.isCanceled()) {
                if (webshopexport.getWebshop() == null) {
                    //T: Status message importing data from web shop
                    setRunResult(msg.importWebshopErrorNodata + "\n" + shopURL);
                    return;
                }

                // Get the error elements and add them to the run result list
                //ndList = document.getElementsByTagName("error");
                if (StringUtils.isNotEmpty(webshopexport.getError())) {
                    setRunResult(webshopexport.getError());
                }
            }
            // else cancel the download process

            //            // Interpret the imported data (and load the product images)
            //            if (getRunResult().isEmpty()) {
            //                // If there is no error - interpret the data.
            //            	setWebshopexport(webshopexport);
            //            }

            localMonitor.done();
        } catch (final MarshalException mex) {
            //T: Status message importing data from web shop
            setRunResult(msg.importWebshopErrorNodata + "\n" + shopURL + "\n" + mex.getMessage());
        } catch (final UnmarshalException e) {
            setRunResult(msg.importWebshopErrorCantopen + "\n" + shopURL + "\n" + "Message: " + e.getCause() + "\n", e);

            if (webshopexport != null) {
                setRunResult(getRunResult().getErrorMessage() + "\n\n" + webshopexport, e);
            }
        } catch (final Exception e) {
            //T: Status message importing data from web shop
            setRunResult(msg.importWebshopErrorCantopen + "\n" + shopURL + "\n" + "Message: " + e.getLocalizedMessage() + "\n", e);

            if (webshopexport != null) {
                setRunResult(getRunResult().getErrorMessage() + "\n\n" + webshopexport, e);
            }
        }
    }

    /**
     * Sets the progress of the job in percent
     * 
     * @param percent
     */
    protected void setProgress(final int percent) {
        if (percent > worked) {
            localMonitor.worked(percent - worked);
            worked = percent;
        }
    }

    /**
     * @return the runResult
     */
    public ExecutionResult getRunResult() {
        return runResult;
    }

    private void setRunResult(final String runResult) {
        setRunResult(runResult, null);
    }

    private void setRunResult(final String runResult, final Exception error) {
        this.runResult = new ExecutionResult(runResult, 1);
        if (error != null) {
            this.runResult.setException(error);
        }
    }

    /**
     * @return the webshopexport
     */
    public Webshopexport getWebshopexport() {
        return webshopexport;
    }

    /**
     * @return the connector
     */
    public WebShopConnector getConnector() {
        return connector;
    }

    /**
     * @param connector
     *            the connector to set
     */
    public void setConnector(final WebShopConnector connector) {
        this.connector = connector;
    }

}
