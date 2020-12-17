/**
 * Copyright(C) 2016
 * DeleteUserController.java, 01/12/2016 LeThanhPhu
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

import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class xử lý khi xóa User
 * 
 * @author LeThanhPhu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.DELETE_USER_ANNOTATION)
public class DeleteUserController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("userId");
        if (!Common.isStringNumbericPositive(id)) {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
            dispatcher.forward(req, resp);
            return;
        }
        int userId = Integer.parseInt(id);
        TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
        if (!tblUserLogicImpl.isUser(userId)) {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
            dispatcher.forward(req, resp);
            return;
        }
        if (tblUserLogicImpl.deleteUserInfor(userId)) {
            HttpSession session = req.getSession();
            session.setAttribute("accept", "true");
            resp.sendRedirect(Constant.SUCCESS_ANNOTATION + "?type=" + Constant.DELETE_SUCCESS);
            return;
        } else {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER015");
            dispatcher.forward(req, resp);
        }
    }

}
