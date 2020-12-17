/**
 * Copyright(C) 2016
 * SuccessController.java, 13/12/2016 Le Thanh Phu
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.utils.Properties;

/**
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.SUCCESS_ANNOTATION)
public class SuccessController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("accept") == null) {
            resp.sendRedirect(Constant.LOGOUT_ANNOTATION);
            return;
        } else {
            session.removeAttribute("accept");
        }
        String type = req.getParameter("type");
        String message = "";
        if (Constant.INSERT_SUCCESS.equals(type)) {
            message = Properties.getMessageJapan("MSG001");
        } else if (Constant.EDIT_SUCCESS.equals(type)) {
            message = Properties.getMessageJapan("MSG002");
        } else if (Constant.DELETE_SUCCESS.equals(type)) {
            message = Properties.getMessageJapan("MSG003");
        } else {
            message = Properties.getErrorMessageJapan("ER015");
            req.setAttribute("message", message);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.SYSTEM_ERROR);
            dispatcher.forward(req, resp);
            return;
        }
        req.setAttribute("message", message);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM006);
        dispatcher.forward(req, resp);

    }
}
