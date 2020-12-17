/**
 * Copyright(C) 2016
 * DetailUserController.java, 05/12/2016 Le Thanh Phu
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

import manageuser.entities.UserInfor;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class xử lý view chi tiết 1 User.
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.DETAIL_USER_ANNOTATION)
public class DetailUserController extends HttpServlet {
    private TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("userId");
        if (!Common.isStringNumbericPositive(id)) {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
            dispatcher.forward(req, resp);
            return;
        }
        int userId = Integer.parseInt(id);
        if (!tblUserLogicImpl.isUser(userId)) {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
            dispatcher.forward(req, resp);
            return;
        }

        UserInfor userInfor = tblUserLogicImpl.getUserInforById(userId);
        if (userInfor == null) {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER015");
            dispatcher.forward(req, resp);
            return;
        } else {
            req.setAttribute("userInfor", userInfor);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM005 + "?userId=" + userId);
        dispatcher.forward(req, resp);
    }

}
