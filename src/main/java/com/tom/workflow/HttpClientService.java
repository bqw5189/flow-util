package com.tom.workflow;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by tom on 16/4/18.
 */
public class HttpClientService {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientService.class);

    private Map<String, String> cookies = null;
    private String username;
    private String password;

    public HttpClientService(String username, String password) {
        this.username = username;
        this.password = password;

        login();
    }

    private void login(){
        if (null == cookies) {
            String url = Main.ROOT_URL + "/login.aspx";
            final Hashtable<String, String> param = new Hashtable<String, String>();
            param.put("username", username);
            param.put("password", password);
            try {
                Connection.Response response = Jsoup.connect(url).data(param).timeout(10 * 1000).execute().method(Connection.Method.POST);
                response.cookies().put("CookiesKey_CurrentUserId", username);
                logger.debug("statusCode:{}", response.statusCode());

                logger.debug("statusMessage:{}", response.statusMessage());
                logger.debug("cookies={}", response.cookies());

                cookies = response.cookies();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("cookies:{}", cookies);
    }

    public Document get(String detailViewUrl) {
        Document document = null;
        try {
            document = Jsoup.connect(detailViewUrl).cookies(cookies).get();
            logger.debug("get url:{}, page:{}", detailViewUrl, document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
