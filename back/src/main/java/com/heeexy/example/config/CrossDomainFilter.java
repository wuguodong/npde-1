package com.heeexy.example.config;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kemi on 2016/3/1.
 */
public class CrossDomainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        String[] allowDomain = {"http://172.26.152.237:8399", "http://39.98.236.58:8399","http://127.0.0.1:8705","http://127.0.0.1:9520"};
        Set allowedOrigins = new HashSet(Arrays.asList(allowDomain));
        String originHeader = ((HttpServletRequest) request).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            resp.addHeader("Access-Control-Allow-Origin", originHeader);
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization");
            resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        }

        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }
}
