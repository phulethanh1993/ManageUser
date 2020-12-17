/**
 * Copyright(C) 2016
 * LoginController.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Class xử lý cho màn hình ADM001
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.LOGIN_ANNOTATION)
public class LoginController extends HttpServlet {
    private ValidateUser validateUser = new ValidateUser();

    /**
     * Phương thức xử lý cho màn hình ADM001 khi có một request dưới dạng post.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
         * String loginNameSession = (String) session.getAttribute("loginName");
         * if (loginNameSession != null && loginNameSession != loginName) {
         * resp.sendRedirect(Constant.ERROR_PAGE_ANNOTATION +
         * "?errorCode=ER001A"); return; }
         */

        String loginName = req.getParameter("loginName");
        String password = req.getParameter("password");
        List<String> listError = validateUser.validateLogin(loginName, password);

        if (listError.size() == 0) {
            HttpSession session = req.getSession(true);
            session.setAttribute("loginName", loginName);
            resp.sendRedirect(Constant.LIST_USER_ANNOTATION);
        } else {
            req.setAttribute("listError", listError);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM001);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Phương thức xử lý cho màn hình ADM001 khi có một request dưới dạng get.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (Common.checkLogin(session)) {
            resp.sendRedirect(Constant.LIST_USER_ANNOTATION);
            return;
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM001);
        dispatcher.forward(req, resp);
    }
}
