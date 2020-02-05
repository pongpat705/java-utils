package com.maoz.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.codec.Charsets;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author  Pongpat Phokeed
 * @version 1.0
 * @since   2019/10/17
 */
public class ShootingStar {

    private Logger log = Logger.getLogger(this.getClass().getName());

    private Charset charset = Charsets.UTF_8;

    private PropertyNamingStrategy propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE;

    private Boolean isEscapeNonAscii = false;

    private Boolean hasProxy = false;

    private HttpsURLConnection cons;
    private HttpURLConnection con;

    private URL url = null;

    private String proxyHost;
    private int proxyPort;
    private String proxyUser;
    private String proxyPass;

    private String host;



    private ObjectMapper oMapper = new ObjectMapper();

    public ShootingStar() {

    }

    /**
     * This constructor initial ShootingStar by set charset, propertyNamingStrategy and EscapeNonAscii
     */
    public ShootingStar(Charset charset, PropertyNamingStrategy propertyNamingStrategy, Boolean isEscapeNonAscii) {
        this.charset = charset;
        this.propertyNamingStrategy = propertyNamingStrategy;
        this.isEscapeNonAscii = isEscapeNonAscii;
    }

    /**
     * This method initial ShootingStar Proxy's  by set proxyHost, proxyPort, proxyUser and proxyPass
     */
    public void setProxy(String proxyHost, int proxyPort, String proxyUser, String proxyPass){
        this.hasProxy = true;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPass = proxyPass;
    }

    private void buildHttps() throws Exception {

        if(isEscapeNonAscii){
            JsonWriteFeature jsonWriteFeature = JsonWriteFeature.ESCAPE_NON_ASCII;
            oMapper.configure(jsonWriteFeature.mappedFeature(), true);
        }

        oMapper.setPropertyNamingStrategy(propertyNamingStrategy);

        if (hasProxy){
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            Authenticator authenticator = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(proxyUser,
                            proxyPass.toCharArray()));
                }
            };
            Authenticator.setDefault(authenticator);
            //proxy
            cons = (HttpsURLConnection) url.openConnection();
        } else {
            cons = (HttpsURLConnection) url.openConnection();
        }
        cons.setDoOutput(true);
        cons.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
        cons.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return s.equals(host);
            }
        });

    }

    private void buildHttp() throws Exception {

        if(isEscapeNonAscii){
            JsonWriteFeature jsonWriteFeature = JsonWriteFeature.ESCAPE_NON_ASCII;
            oMapper.configure(jsonWriteFeature.mappedFeature(), true);
        }

        oMapper.setPropertyNamingStrategy(propertyNamingStrategy);

        if (hasProxy){
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            Authenticator authenticator = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(proxyUser,
                            proxyPass.toCharArray()));
                }
            };
            Authenticator.setDefault(authenticator);
            //proxy
            con = (HttpURLConnection) url.openConnection();
        } else {
            con = (HttpURLConnection) url.openConnection();
        }
        con.setDoOutput(true);

    }

    /**
     * This method is used to send a httpRequest by these config parameters
     * @param object objectData to be sent
     * @param httpMethod  httpMethod to be used make a request
     * @param url  url address to make a request with
     * @param type  type of response object
     * @param headers  http headers to be config
     * @return response object data
     */
    public <T> Object shootHttp (T object, String httpMethod, String url, Class<T> type, Map<String, String> headers) throws IllegalAccessException, InstantiationException {

        Object t = null;
        try {
            this.url = new URL(url);
            buildHttp();

            con.setRequestMethod(httpMethod);
            headers.keySet().forEach(key -> con.setRequestProperty(key, headers.get(key)));
            if("POST".equals(httpMethod)){
                String reqBody = oMapper.writeValueAsString(object);
                reqBody = new String(reqBody.getBytes(charset), charset);

                OutputStream os = con.getOutputStream();
                byte[] input = reqBody.getBytes(charset);
                os.write(input);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            StringBuilder response = new StringBuilder();
            if (HttpURLConnection.HTTP_OK != con.getResponseCode()) {
                log.info(" Service error = "+ con.getResponseCode());
                response.append(br.lines().collect(Collectors.joining()));
                log.info("response -> "+response.toString());
            } else {

                response.append(br.lines().collect(Collectors.joining()));

                log.info(" Response Status : " + con.getResponseCode());
                log.info(" response body : " + response.toString());

                t = oMapper.readValue(response.toString(), type);
            }

        } catch (ProtocolException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * This method is used to send a httpRequest by these config parameters
     * @param object objectData to be sent
     * @param httpMethod  httpMethod to be used make a request
     * @param url  url address to make a request with
     * @param type  type of response object
     * @param headers  http headers to be config
     * @return response object data
     */
    public <T> Object shootHttps (T object, String httpMethod, String url, Class<T> type, Map<String, String> headers) {

        Object t = null;
        try {
            this.url = new URL(url);
            this.host = this.url.getHost();
            buildHttps();

            cons.setRequestMethod(httpMethod);
            headers.keySet().forEach(key -> cons.setRequestProperty(key, headers.get(key)));
            if("POST".equals(httpMethod)){
                String reqBody = oMapper.writeValueAsString(object);
                reqBody = new String(reqBody.getBytes(charset), charset);

                OutputStream os = cons.getOutputStream();
                byte[] input = reqBody.getBytes(charset);
                os.write(input);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(cons.getInputStream(), charset));
            StringBuilder response = new StringBuilder();
            response.append(br.lines().collect(Collectors.joining()));
            if (HttpURLConnection.HTTP_OK != cons.getResponseCode()) {
                log.info(" Service error ");
                log.info(" Service error = "+ cons.getResponseCode());
                response.append(br.lines().collect(Collectors.joining()));
                log.info("response -> "+response.toString());
            } else {
                log.info(" Response Status : " + cons.getResponseCode());
                log.info(" response body : " + response.toString());

                t = oMapper.readValue(response.toString(), type);
            }

        } catch (ProtocolException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }


}
