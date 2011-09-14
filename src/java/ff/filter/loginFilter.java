/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jerry
 */
public class loginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hresp = (HttpServletResponse) response;
        System.out.println("loginFilter++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Begin");
        System.out.println("hreq.getRequestURI():" + hreq.getRequestURI());
        System.out.println("hreq.getPathInfo():" + hreq.getPathInfo());
        System.out.println(hreq.getQueryString());
        System.out.println(hreq.getMethod());
        System.out.println(hreq.getContentType());
        System.out.println("Name:"+hreq.getParameter("name"));
        System.out.println("hreq.getSession().getAttribute(userId):" + hreq.getSession().getAttribute("userId"));
        System.out.println("loginFilter++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++End");
        /*if (hreq.getSession().getAttribute("userId") == null) {
            if (hreq.getRequestURI().equals("/PsychologyTest/") || hreq.getRequestURI().equals("/PsychologyTest/psycholtest") || hreq.getRequestURI().equals("/PsychologyTest/psycholtest/index.html") || hreq.getRequestURI().equals("/PsychologyTest/psycholtest/injectUserId.html") || hreq.getRequestURI().equals("/PsychologyTest/psycholtest/clearSession.html")) {
                chain.doFilter(request, response);
            } else {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println(hreq.getContextPath() + "/psycholtest");
                hresp.sendRedirect(hreq.getContextPath() + "/psycholtest/index.html");
            }
        } else {
            chain.doFilter(request, response);
        }*/
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
