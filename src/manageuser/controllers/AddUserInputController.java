/**
 * Copyright(C) 2016
 * AddUserInputController.java, 05/12/2016 Le Thanh Phu
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.UserInfor;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Class xử lý thêm, edit, xóa User.
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = { "/" + Constant.ADD_USER_ANNOTATION, "/" + Constant.VALIDATE_USER_ANNOTATION,
        "/" + Constant.EDIT_USER_ANNOTATION })
public class AddUserInputController extends HttpServlet {
    private MstGroupLogicImpl groupLogic = new MstGroupLogicImpl();
    private MstJapanLogicImpl mstJapanLogic = new MstJapanLogicImpl();
    private TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
    private ValidateUser validateUser = new ValidateUser();

    /**
     * Được gọi bởi server (thông qua phương thức service) cho phép một servlet
     * xử lý một request dưới dạng GET.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("edit".equals(req.getParameter("type"))) {
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
        }

        setDataLogic(req, resp);
        UserInfor userInfor = setValue(req, resp);
        if (userInfor == null) {
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
            dispatcher.forward(req, resp);
            return;
        }
        req.setAttribute("userInfor", userInfor);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM003);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        int userId = 0;
        if ("edit".equals(type)) {
            String id = req.getParameter("userId");
            if (!Common.isStringNumbericPositive(id)) {
                RequestDispatcher dispatcher = req
                        .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
                dispatcher.forward(req, resp);
                return;
            }
            userId = Integer.parseInt(id);
            if (!tblUserLogicImpl.isUser(userId)) {
                RequestDispatcher dispatcher = req
                        .getRequestDispatcher("/" + Constant.ERROR_PAGE_ANNOTATION + "?errorCode=ER013");
                dispatcher.forward(req, resp);
                return;
            }
        }
        UserInfor userInfor = setValue(req, resp);
        userInfor.setUserId(userId);

        List<String> listError = validateUser.validateUserInfor(userInfor);
        if (listError.size() > 0) {
            setDataLogic(req, resp);
            req.setAttribute("listError", listError);
            req.setAttribute("userInfor", userInfor);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM003);
            dispatcher.forward(req, resp);
        } else {
            HttpSession session = req.getSession(false);
            String currentTimeMillis = System.currentTimeMillis() + "";
            String ssid = Common.getSHA1Secure(currentTimeMillis, "");

            if ("add".equals(type)) {
                String salt = Common.convertBytesToHexString(Common.getSecureRandomBytes(16));
                userInfor.setSalt(salt);
                userInfor.setPassword(Common.getSHA1Secure(userInfor.getPassword(), salt));
                userInfor.setPasswordConfirm("");
                session.setAttribute(ssid, userInfor);
                resp.sendRedirect(Constant.ADD_COMFIRM_ANNOTATION + "?ssid=" + ssid + "&type=add");
            } else {
                session.setAttribute(ssid, userInfor);
                resp.sendRedirect(Constant.EDIT_COMFIRM_ANNOTATION + "?ssid=" + ssid + "&type=edit");
            }
        }
    }

    /**
     * Được gọi bởi server (thông qua phương thức service) cho phép một servlet
     * xử lý một request dưới dạng POST.
     */
    private void setDataLogic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int startYear = 1900;
        int endYear = Common.getYearNow();
        List<Integer> listYears = Common.getListYear(startYear, endYear);
        List<Integer> listYearsEndDate = Common.getListYear(startYear, endYear + 1);
        List<Integer> listMonths = Common.getListMonth();
        List<Integer> listDays = Common.getListDay();
        List<MstGroup> listGroup = groupLogic.getAllGroup();

        MstGroup groupDefault = new MstGroup();
        groupDefault.setGroupId(0);
        groupDefault.setGroupName("選択してください");
        listGroup.add(0, groupDefault);

        List<MstJapan> listMstJapan = mstJapanLogic.getAllMstJapan();
        MstJapan mstJapanDefault = new MstJapan();
        mstJapanDefault.setCodeLevel("N0");
        mstJapanDefault.setNameLevel("選択してください");
        listMstJapan.add(0, mstJapanDefault);
        req.setAttribute("listGroup", listGroup);
        req.setAttribute("listMstJapan", listMstJapan);
        req.setAttribute("listYears", listYears);
        req.setAttribute("listYearsEndDate", listYearsEndDate);
        req.setAttribute("listMonths", listMonths);
        req.setAttribute("listDays", listDays);
    }

    /**
     * Phương thức set giá trị default cho các select box.
     * 
     * @param req
     *            Một {@link HttpServletRequest}
     * @param resp
     *            Một {@link HttpServletResponse}
     * @return Một {@link UserInforADM003} với các thuộc tính default cho các
     *         select box.
     */
    private UserInfor setValue(HttpServletRequest req, HttpServletResponse resp) {
        String type = req.getParameter("type");
        String method = req.getMethod();
        UserInfor userInfor = new UserInfor();

        if ("back".equals(type)) {
            String ssid = req.getParameter("ssid");
            HttpSession session = req.getSession(false);
            userInfor = (UserInfor) session.getAttribute(ssid);
            if (userInfor == null) {
                userInfor = new UserInfor();
                setDefaultValue(userInfor);
            } else {
                userInfor.setPassword("");
                userInfor.setPasswordConfirm("");
            }
        } else if ("add".equals(type)) {
            if ("POST".equals(method)) {
                getRequestValue(req, userInfor);
            } else if ("GET".equals(method)) {
                setDefaultValue(userInfor);
            }
        } else if ("edit".equals(type)) {
            if ("POST".equals(method)) {
                getRequestValue(req, userInfor);
            } else if ("GET".equals(method)) {
                String id = req.getParameter("userId");
                int userId = Integer.parseInt(id);

                userInfor = tblUserLogicImpl.getUserInforById(userId);
                userInfor.setBirthday(Common.toDateArrayInt(userInfor.getBirthdayString()));
                String startDateString = userInfor.getStartDateString();
                String endDateString = userInfor.getEndDateString();
                if (Common.isNullOrEmpty(startDateString) || Common.isNullOrEmpty(endDateString)) {
                    List<Integer> defaultStartDate = Common.getCurrentYearMonthDay();
                    List<Integer> defaultEndDate = new ArrayList<>(defaultStartDate);
                    defaultEndDate.set(0, defaultStartDate.get(0) + 1);
                    userInfor.setStartDate(defaultStartDate);
                    userInfor.setEndDate(defaultEndDate);
                } else {
                    userInfor.setStartDate(Common.toDateArrayInt(userInfor.getStartDateString()));
                    userInfor.setEndDate(Common.toDateArrayInt(userInfor.getEndDateString()));
                }
            }
        } else {
            setDefaultValue(userInfor);
        }

        return userInfor;
    }

    /**
     * Phương thức get các giá trị từ request và set các thuộc tính cho
     * userInfor
     * 
     * @param req
     *            Request method.
     * @param userInfor
     *            Đối tượng UserInfor cần set các thuộc tính.
     */
    private void getRequestValue(HttpServletRequest req, UserInfor userInfor) {
        userInfor.setLoginName(req.getParameter("loginName"));
        userInfor.setGroupId(req.getParameter("groupId"));
        userInfor.setFullName(req.getParameter("name"));
        userInfor.setFullNameKana(req.getParameter("nameKana"));

        String[] birthdayString = req.getParameterValues("birthday");
        List<Integer> birthday = Common.toDateArrayInt(birthdayString);

        userInfor.setBirthday(birthday);
        userInfor.setEmail(req.getParameter("email"));
        userInfor.setTel(req.getParameter("tel"));
        userInfor.setPassword(req.getParameter("password"));
        userInfor.setPasswordConfirm(req.getParameter("passwordConfirm"));
        userInfor.setCodeLevel(req.getParameter("codeLevel"));

        String[] startDateStringArray = req.getParameterValues("startDate");
        String[] endDateStringArray = req.getParameterValues("endDate");

        List<Integer> startDate = Common.toDateArrayInt(startDateStringArray);
        List<Integer> endDate = Common.toDateArrayInt(endDateStringArray);
        userInfor.setStartDate(startDate);
        userInfor.setEndDate(endDate);
        userInfor.setTotal(req.getParameter("total"));
        userInfor.setNameLevel(req.getParameter("nameLevel"));
        userInfor.setGroupName(req.getParameter("groupName"));
    }

    /**
     * Phương thức set các giá trị mặc định cho user infor để hiển thị.
     * 
     * @param userInfor
     *            Đối tượng UserInfor cần set.
     */
    private void setDefaultValue(UserInfor userInfor) {
        List<Integer> defaultDate = Common.getCurrentYearMonthDay();

        userInfor.setBirthday(defaultDate);
        userInfor.setStartDate(defaultDate);

        List<Integer> defaultEndDate = new ArrayList<>(defaultDate);
        defaultEndDate.set(0, defaultDate.get(0) + 1);
        userInfor.setEndDate(defaultEndDate);
        userInfor.setLoginName("");
        userInfor.setGroupId("0");
        userInfor.setFullName("");
        userInfor.setFullNameKana("");
        userInfor.setEmail("");
        userInfor.setTel("");
        userInfor.setPassword("");
        userInfor.setPasswordConfirm("");
        userInfor.setCodeLevel("N0");
        userInfor.setTotal("");
    }
}
