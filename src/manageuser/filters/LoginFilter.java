/**
 * Copyright(C) 2016
 * LoginFilter.java, 07/12/2016 LeThanhPhu
 */
package manageuser.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class Filter xử lý kiểm tra login cho các trang.
 * 
 * @author LeThanhPhu
 *
 */
@WebFilter(urlPatterns = "*.web")
public class LoginFilter implements Filter {
    /**
     * Phương thức thực hiện việc kiểm tra login cho mọi request trừ các request
     * login.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse respone = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        if (Common.checkLogin(session)) {
            chain.doFilter(req, resp);
        } else {
            respone.sendRedirect(Constant.LOGIN_ANNOTATION);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}