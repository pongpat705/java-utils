package com.maoz.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.codec.Charsets;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
            cons = (HttpsURLConnection) url.openConnection(proxy);
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

    private void buildHttpsIgnore() throws Exception {

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
            cons = (HttpsURLConnection) url.openConnection(proxy);
        } else {
            cons = (HttpsURLConnection) url.openConnection();
        }
        cons.setDoOutput(true);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, new SecureRandom());
        SSLContext.setDefault(ctx);

        cons.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
        cons.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

    }

    private void buildHttpsIgnore12() throws Exception {

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
            cons = (HttpsURLConnection) url.openConnection(proxy);
        } else {
            cons = (HttpsURLConnection) url.openConnection();
        }
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        ctx.init(new KeyManager[0], new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, new SecureRandom());
        SSLContext.setDefault(ctx);

        cons.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
        cons.setDoOutput(true);
        cons.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
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
            con = (HttpURLConnection) url.openConnection(proxy);
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
    public <T> T shootHttp (Object object, String httpMethod, String url, Class<T> type, Map<String, String> headers) throws IllegalAccessException, InstantiationException {

        Object t = null;
        try {
            this.url = new URL(url);
            buildHttp();

            con.setRequestMethod(httpMethod);
            headers.keySet().forEach(key -> con.setRequestProperty(key, headers.get(key)));
            if("POST".equals(con.getRequestMethod())){
                String reqBody = oMapper.writeValueAsString(object);
                log.info("reqBOdy "+ reqBody);
                reqBody = new String(reqBody.getBytes(charset), charset);
                byte[] input = reqBody.getBytes(charset);
                con.setRequestProperty("Content-Length", String.valueOf(input.length));

                OutputStream os = con.getOutputStream();
                os.write(input);
            }
            InputStream ips = con.getInputStream();
            InputStreamReader ipsR = new InputStreamReader(ips, charset);
            BufferedReader br = new BufferedReader(ipsR);
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

        return (T) t;
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
    public <T> T shootHttps (Object object, String httpMethod, String url, Class<T> type, Map<String, String> headers) {

        Object t = null;
        try {
            this.url = new URL(url);
            this.host = this.url.getHost();
            buildHttps();

            cons.setRequestMethod(httpMethod);
            headers.keySet().forEach(key -> cons.setRequestProperty(key, headers.get(key)));
            if("POST".equals(cons.getRequestMethod())){
                String reqBody = oMapper.writeValueAsString(object);
                log.info("reqBody "+ reqBody);
                reqBody = new String(reqBody.getBytes(charset), charset);
                byte[] input = reqBody.getBytes(charset);
                cons.setRequestProperty("Content-Length", String.valueOf(input.length));

                OutputStream os = cons.getOutputStream();
                os.write(input);
            }
            InputStream ips = cons.getInputStream();
            InputStreamReader ipsR = new InputStreamReader(ips, charset);
            BufferedReader br = new BufferedReader(ipsR);
            StringBuilder response = new StringBuilder();
            log.info("response "+ response.toString());
            if (HttpURLConnection.HTTP_OK != cons.getResponseCode()) {
                log.info(" Service error ");
                log.info(" Service error = "+ cons.getResponseCode());
                response.append(br.lines().collect(Collectors.joining()));
                log.info("response -> "+response.toString());
            } else {
                response.append(br.lines().collect(Collectors.joining()));
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

        return (T) t;
    }

    public <T> T shootHttpsIgnore (Object object, String httpMethod, String url, Class<T> type, Map<String, String> headers) {

        Object t = null;
        try {
            this.url = new URL(url);
            this.host = this.url.getHost();
            buildHttpsIgnore();

            cons.setRequestMethod(httpMethod);
            headers.keySet().forEach(key -> cons.setRequestProperty(key, headers.get(key)));
            if("POST".equals(cons.getRequestMethod())){
                String reqBody = oMapper.writeValueAsString(object);
                log.info("reqBody "+ reqBody);
                reqBody = new String(reqBody.getBytes(charset), charset);
                byte[] input = reqBody.getBytes(charset);
                cons.setRequestProperty("Content-Length", String.valueOf(input.length));

                OutputStream os = cons.getOutputStream();
                os.write(input);
            }
            InputStream ips = cons.getInputStream();
            InputStreamReader ipsR = new InputStreamReader(ips, charset);
            BufferedReader br = new BufferedReader(ipsR);
            StringBuilder response = new StringBuilder();
            log.info("response "+ response.toString());
            if (HttpURLConnection.HTTP_OK != cons.getResponseCode()) {
                log.info(" Service error ");
                log.info(" Service error = "+ cons.getResponseCode());
                response.append(br.lines().collect(Collectors.joining()));
                log.info("response -> "+response.toString());
            } else {
                response.append(br.lines().collect(Collectors.joining()));
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

        return (T) t;
    }

    public <T> T shootHttpsIgnore12 (Object object, String httpMethod, String url, Class<T> type, Map<String, String> headers) {

        Object t = null;
        try {
            this.url = new URL(url);
            this.host = this.url.getHost();
            buildHttpsIgnore12();

            cons.setRequestMethod(httpMethod);
            headers.keySet().forEach(key -> cons.setRequestProperty(key, headers.get(key)));
            if("POST".equals(cons.getRequestMethod())){
                String reqBody = oMapper.writeValueAsString(object);
                log.info("reqBody "+ reqBody);
                reqBody = new String(reqBody.getBytes(charset), charset);
                byte[] input = reqBody.getBytes(charset);
                cons.setRequestProperty("Content-Length", String.valueOf(input.length));

                OutputStream os = cons.getOutputStream();

                os.write(input);
            }
            InputStream ips = cons.getInputStream();
            InputStreamReader ipsR = new InputStreamReader(ips, charset);
            BufferedReader br = new BufferedReader(ipsR);
            StringBuilder response = new StringBuilder();
            log.info("response "+ response.toString());
            if (HttpURLConnection.HTTP_OK != cons.getResponseCode()) {
                log.info(" Service error ");
                log.info(" Service error = "+ cons.getResponseCode());
                response.append(br.lines().collect(Collectors.joining()));
                log.info("response -> "+response.toString());
            } else {
                response.append(br.lines().collect(Collectors.joining()));
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

        return (T) t;
    }

}
