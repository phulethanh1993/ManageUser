/**
 * Copyright(C) 2016
 * CharSetEncodingFilter.java, 07/12/2016 LeThanhPhu
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

/**
 * Class Filter xử lý mã hóa UTF-8 cho các trang.
 * 
 * @author LeThanhPhu
 *
 */
@WebFilter(urlPatterns = "*")
public class CharSetEncodingFilter implements Filter {
    /**
     * Phương thức thực hiện việc set mã hóa UTF-8 cho mọi trang.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
        FilterChain chain) throws IOException, ServletException {
    req.setCharacterEncoding("UTF-8");
    chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
