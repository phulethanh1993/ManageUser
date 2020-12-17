/**
 * Copyright(C) 2016
 * TblUserLogicImpl.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.logics.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;

/**
 * Class thực thi Interface {@link TblUserLogic}
 * 
 * @author Le Thanh Phu
 *
 */
public class TblUserLogicImpl implements TblUserLogic {
    private TblUserDaoImpl tblUserDaoImpl = new TblUserDaoImpl();
    private TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();

    @Override
    public boolean isAdmin(String loginName, String password) {
        return tblUserDaoImpl.isAdmin(loginName, password);
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#isUser(int)
     */
    @Override
    public boolean isUser(int userId) {
        TblUser tblUser = tblUserDaoImpl.getUserById(userId);
        if (tblUser == null) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#getTotalUsers(int, java.lang.String)
     */
    @Override
    public int getTotalUsers(int groupId, String fullName) {
        int totalUsers = tblUserDaoImpl.getTotalUsers(groupId, fullName);
        return totalUsers;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#getListUsers(int, int, int,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
            String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
        if (fullName == null) {
            fullName = "";
        }
        List<UserInfor> listUser = null;
        listUser = tblUserDaoImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
                sortByCodeLevel, sortByEndDate);
        return listUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#existsEmailExcept(int,
     * java.lang.String)
     */
    @Override
    public boolean existsEmailExcept(int userId, String email) {
        return tblUserDaoImpl.existsEmailExcept(userId, email);
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#existsLoginName(java.lang.String)
     */
    @Override
    public boolean existsLoginName(String loginName) {

        return tblUserDaoImpl.existsLoginName(loginName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * manageuser.logics.TblUserLogic#createUser(manageuser.entities.UserInfor)
     */
    @Override
    public boolean createUser(UserInfor userInfor) {

        TblUser tblUser = new TblUser();
        tblUser.setGroupId(userInfor.getGroupId());
        tblUser.setLoginName(userInfor.getLoginName());
        tblUser.setPassword(userInfor.getPassword());
        tblUser.setFullName(userInfor.getFullName());
        tblUser.setFullNameKana(userInfor.getFullNameKana());
        tblUser.setEmail(userInfor.getEmail());
        tblUser.setTel(userInfor.getTel());
        tblUser.setBirthday(userInfor.getBirthdayString());
        tblUser.setSalt(userInfor.getSalt());

        TblDetailUserJapan tblDetailUserJapan = null;
        if (!"N0".equals(userInfor.getCodeLevel()) && userInfor.getCodeLevel() != null) {
            tblDetailUserJapan = new TblDetailUserJapan();
            tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
            tblDetailUserJapan.setStartDate(userInfor.getStartDateString());
            tblDetailUserJapan.setEndDate(userInfor.getEndDateString());
            tblDetailUserJapan.setTotal(userInfor.getTotal());
        }

        Connection connection = tblUserDaoImpl.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        if (existsEmailExcept(userInfor.getUserId(), userInfor.getEmail())
                || existsLoginName(userInfor.getLoginName())) {
            return false;
        } else if (!tblUserDaoImpl.insertUser(tblUser, connection)) {
            tblUserDaoImpl.rollbackAndClose(connection);
            return false;
        } else if (tblDetailUserJapan != null) {
            if (!tblDetailUserJapanDaoImpl.insertOrUpdateIfExistsTUDJ(tblDetailUserJapan, connection)) {
                tblUserDaoImpl.rollbackAndClose(connection);
                return false;
            }
        }
        tblUserDaoImpl.commitAndClose(connection);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#getUserInforById(int)
     */
    @Override
    public UserInfor getUserInforById(int userId) {
        return tblUserDaoImpl.getUserInforById(userId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#updateUserInfor(manageuser.entities.
     * UserInfor)
     */
    @Override
    public boolean updateUserInfor(UserInfor userInfor) {

        TblUser tblUser = new TblUser();
        tblUser.setUserId(userInfor.getUserId());
        tblUser.setGroupId(userInfor.getGroupId());
        tblUser.setLoginName(userInfor.getLoginName());
        tblUser.setPassword(userInfor.getPassword());
        tblUser.setFullName(userInfor.getFullName());
        tblUser.setFullNameKana(userInfor.getFullNameKana());
        tblUser.setEmail(userInfor.getEmail());
        tblUser.setTel(userInfor.getTel());
        tblUser.setBirthday(userInfor.getBirthdayString());
        tblUser.setSalt(userInfor.getSalt());

        TblDetailUserJapan tblDetailUserJapan = null;
        if (!"N0".equals(userInfor.getCodeLevel()) && userInfor.getCodeLevel() != null) {
            tblDetailUserJapan = new TblDetailUserJapan();
            tblDetailUserJapan.setUserId(userInfor.getUserId());
            tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
            tblDetailUserJapan.setStartDate(userInfor.getStartDateString());
            tblDetailUserJapan.setEndDate(userInfor.getEndDateString());
            tblDetailUserJapan.setTotal(userInfor.getTotal());
        }

        Connection connection = tblUserDaoImpl.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        if (!tblUserDaoImpl.updateUser(tblUser, connection)) {
            tblUserDaoImpl.rollbackAndClose(connection);
            return false;
        }

        if (tblDetailUserJapan != null) {
            if (!tblDetailUserJapanDaoImpl.insertOrUpdateIfExistsTUDJ(tblDetailUserJapan, connection)) {
                tblUserDaoImpl.rollbackAndClose(connection);
                return false;
            }
        } else {
            if (!tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userInfor.getUserId(), connection)) {
                tblUserDaoImpl.rollbackAndClose(connection);
                return false;
            }
        }
        tblUserDaoImpl.commitAndClose(connection);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.TblUserLogic#deleteUserInfor(int)
     */
    @Override
    public boolean deleteUserInfor(int userId) {
        Connection connection = tblUserDaoImpl.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        if (!tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userId, connection)) {
            tblUserDaoImpl.rollbackAndClose(connection);
            return false;
        }
        if (!tblUserDaoImpl.deleteUser(userId, connection)) {
            tblUserDaoImpl.rollbackAndClose(connection);
            return false;
        }
        tblUserDaoImpl.commitAndClose(connection);
        return true;
    }

}
