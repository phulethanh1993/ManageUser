/**
 * Copyright(C) 2016
 * LogoutController.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;

/**
 * Class xử lý khi User logout khỏi hệ thống.
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.LOGOUT_ANNOTATION)
public class LogoutController extends HttpServlet {
    /**
     * Phương thức xử lý khi người dùng click vào logout. Thực hiện xóa session
     * và chuyển tiếp về trang Index.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    resp.sendRedirect(Constant.LOGIN_ANNOTATION);
    }
}
