/**
 * Copyright(C) 2016
 * ListUserController.java, 18/12/2016 Le Thanh Phu
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

import manageuser.entities.MstGroup;
import manageuser.entities.UserInfor;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class xử lý cho màn hình ADM002
 * 
 * @author Le Thanh Phu
 *
 */
@WebServlet(urlPatterns = "/" + Constant.LIST_USER_ANNOTATION)
public class ListUserController extends HttpServlet {
    private TblUserLogicImpl userLogic = new TblUserLogicImpl();
    private MstGroupLogicImpl groupLogic = new MstGroupLogicImpl();

    /**
     * Phương thức xử lý cho màn hình ADM002 khi có một request dưới dạng get.
     * Trong đó sẽ gọi doPost
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * Phương thức xử lý cho màn hình ADM002 khi có một request dưới dạng post.
     * Thực hiện xử lý đầu vào, gọi các hàm logic lấy dữ liệu trả về cho người
     * sử dụng.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String type = req.getParameter("type");
        if (type == null) {
            type = "default";
        }
        String searchName = "";
        String[] listSortType = new String[] { "ASC", "DESC", "DESC" };
        String sortType = "full_name";
        int groupIdInt = 0;
        int currentPageInt = 1;

        switch (type) {
        // Trường hợp click search
        case "search":
            searchName = req.getParameter("name");
            String groupId = req.getParameter("group_id");

            if (searchName == null) {
                searchName = "";
            }
            groupIdInt = Common.convertGroupId(groupId);
            session.setAttribute("sortType", sortType);
            session.setAttribute("listSortType", listSortType);
            session.setAttribute("searchName", searchName);
            session.setAttribute("groupId", groupIdInt);
            session.setAttribute("currentPage", currentPageInt);
            break;
        // Trường hợp click sort
        case "sort":
            sortType = req.getParameter("order");
            if (!Common.checkWhiteList(sortType)) {
                sortType = "full_name";
            }
            listSortType = (String[]) session.getAttribute("listSortType");
            switch (sortType) {
            case "full_name":
                if ("ASC".equals(listSortType[0])) {
                    listSortType[0] = "DESC";
                } else {
                    listSortType[0] = "ASC";
                }
                listSortType[1] = "DESC";
                listSortType[2] = "DESC";
                break;
            case "code_level":
                if ("ASC".equals(listSortType[1])) {
                    listSortType[1] = "DESC";
                } else {
                    listSortType[1] = "ASC";
                }
                listSortType[0] = "ASC";
                listSortType[2] = "DESC";
                break;
            case "end_date":
                if ("ASC".equals(listSortType[2])) {
                    listSortType[2] = "DESC";
                } else {
                    listSortType[2] = "ASC";
                }
                listSortType[0] = "ASC";
                listSortType[1] = "DESC";
                break;
            }
            session.setAttribute("currentPage", 1);
            session.setAttribute("orderType", sortType);
            session.setAttribute("listSortType", listSortType);

            searchName = (String) session.getAttribute("searchName");
            groupIdInt = (Integer) session.getAttribute("groupId");
            break;
        // Trường hợp click paging
        case "paging":
            String currentPage = req.getParameter("page");
            currentPageInt = Common.convertPageNumber(currentPage);
            session.setAttribute("currentPage", currentPageInt);
            searchName = (String) session.getAttribute("searchName");
            listSortType = (String[]) session.getAttribute("listSortType");
            sortType = (String) session.getAttribute("sortType");
            groupIdInt = (Integer) session.getAttribute("groupId");
            break;
        // Trường hợp click back ở màn hình ADM003.
        case "back":
            // Trường hợp click top ở các trang.
        case "top":
            searchName = (String) session.getAttribute("searchName");
            listSortType = (String[]) session.getAttribute("listSortType");
            sortType = (String) session.getAttribute("sortType");
            groupIdInt = (Integer) session.getAttribute("groupId");
            currentPageInt = (Integer) session.getAttribute("currentPage");
            break;
        // Trường hợp mới vào màn hình và các trường hợp ngoại lệ khác
        default:
            session.setAttribute("sortType", sortType);
            session.setAttribute("listSortType", listSortType);
            session.setAttribute("searchName", searchName);
            session.setAttribute("groupId", groupIdInt);
            session.setAttribute("currentPage", currentPageInt);
            break;
        }

        int pageLimit = Common.getPageRange();
        int offSet = Common.getOffset(currentPageInt, pageLimit);
        int limit = Common.getLimit();

        int totalUser = 0;
        int totalPage = 0;
        totalUser = userLogic.getTotalUsers(groupIdInt, searchName);
        List<MstGroup> listGroup = groupLogic.getAllGroup();
        totalPage = Common.getTotalPage(totalUser, limit);
        List<UserInfor> listUser = userLogic.getListUsers(offSet, limit, groupIdInt, searchName, sortType,
                listSortType[0], listSortType[1], listSortType[2]);

        List<Integer> listPaging = Common.getListPaging(totalUser, limit, currentPageInt);

        req.setAttribute("searchGroupId", groupIdInt);
        req.setAttribute("searchName", searchName);
        req.setAttribute("listSortType", listSortType);
        req.setAttribute("listGroup", listGroup);
        req.setAttribute("listUser", listUser);
        req.setAttribute("listPaging", listPaging);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("currentPage", currentPageInt);
        req.setAttribute("pageLimit", pageLimit);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/" + Constant.ADM002);
        dispatcher.forward(req, resp);
    }
}