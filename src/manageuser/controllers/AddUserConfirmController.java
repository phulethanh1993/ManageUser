/**
 * Copyright(C) 2016
 * AddUserConfirmController.java, 13/12/2016 Le Thanh Phu
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
 * Class xử lý cho màn hình ADM004
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = { "/" + Constant.ADD_COMFIRM_ANNOTATION, "/addUserOK.web",
        "/" + Constant.EDIT_COMFIRM_ANNOTATION })
public class AddUserConfirmController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssid = req.getParameter("ssid");
        HttpSession session = req.getSession(false);
        UserInfor userInfor = (UserInfor) session.getAttribute(ssid);
        if (userInfor == null) {
            resp.sendRedirect(Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER015");
            return;
        }
        userInfor.setBirthdayString(Common.toDateString(userInfor.getBirthday()));
        userInfor.setEndDateString(Common.toDateString(userInfor.getEndDate()));
        userInfor.setStartDateString(Common.toDateString(userInfor.getStartDate()));
        req.setAttribute("userInfor", userInfor);
        req.setAttribute("ssid", ssid);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM004);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssid = req.getParameter("ssid");
        String caseInput = req.getParameter("type");
        HttpSession session = req.getSession(false);
        UserInfor userInfor = (UserInfor) session.getAttribute(ssid);
        TblUserLogicImpl impl = new TblUserLogicImpl();
        if ("add".equals(caseInput)) {
            if (impl.createUser(userInfor)) {
                session.removeAttribute("ssid");
                session.setAttribute("accept", "true");
                resp.sendRedirect(Constant.SUCCESS_ANNOTATION + "?type=" + Constant.INSERT_SUCCESS);
                return;
            }
        } else if ("edit".equals(caseInput)) {
            if (impl.updateUserInfor(userInfor)) {
                session.removeAttribute("ssid");
                session.setAttribute("accept", "true");
                resp.sendRedirect(Constant.SUCCESS_ANNOTATION + "?type=" + Constant.EDIT_SUCCESS);
                return;
            }
        }
        session.removeAttribute(ssid);
        session.setAttribute("accept", "true");
        resp.sendRedirect(Constant.SUCCESS_ANNOTATION + "?type=" + Constant.INSERT_ERROR);
    }

}
