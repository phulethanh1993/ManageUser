/**
 * Copyright(C) 2016
 * ErrorController.java, 05/12/2016 Le Thanh Phu
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;
import manageuser.utils.Properties;

/**
 * Class xử lý các trường hợp lỗi bất thường của dự án.
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.ERROR_PAGE_ANNOTATION)
public class ErrorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorCode = req.getParameter("errorCode");
        if (errorCode == null) {
            Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
            if (statusCode == 404) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/html/NotFound.html");
                dispatcher.forward(req, resp);
                return;
            }
            errorCode = "ER015";
        }
        String message = Properties.getErrorMessageJapan(errorCode);
        req.setAttribute("message", message);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.SYSTEM_ERROR);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Constant.LOGOUT_ANNOTATION);
    }
}
