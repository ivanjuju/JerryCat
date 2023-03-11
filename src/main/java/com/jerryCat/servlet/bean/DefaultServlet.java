package com.jerryCat.servlet.bean;

import com.jerryCat.net.JerryRequest;
import com.jerryCat.net.JerryResponse;
import com.jerryCat.net.JerryServlet;

/**
 * @author ivanzhu
 */
public class DefaultServlet implements JerryServlet {

    public void doGet(JerryRequest request, JerryResponse response) throws Exception {
        String uri = request.getUri();
        int i = uri.indexOf("?");
        String name = uri.substring(0, i == -1 ? uri.length() : i);
        response.write("404 - no this servlet : " + name);
    }

    public void doPost(JerryRequest request, JerryResponse response) throws Exception {
        doGet(request, response);
    }
}
