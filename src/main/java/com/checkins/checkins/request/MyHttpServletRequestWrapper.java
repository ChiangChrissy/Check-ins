package com.checkins.checkins.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private String name;

    public MyHttpServletRequestWrapper(HttpServletRequest request, String name) {
        super(request);
        this.name = name;
    }
    public MyHttpServletRequestWrapper(HttpServletRequest request){
        super(request);
    }



//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(getInputStream()));
//    }

    @Override
    public String getParameter(String name) {
        if ("username2".equals(name)) {
            return this.name;
        }
        return super.getParameter(name);
    }
}

