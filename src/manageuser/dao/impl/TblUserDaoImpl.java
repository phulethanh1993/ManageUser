/**
 * Copyright(C) 2016
 * TblUserDaoImpl.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class thực thi Interface {@link TblUserDao}
 * 
 * @author Le Thanh Phu
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#getTotalUsers(int, java.lang.String)
     */
    @Override
    public int getTotalUsers(int groupId, String fullName) {
        Connection connection = getConnection();
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT COUNT(group_id) FROM tbl_user");
        sqlQuery.append(" WHERE");
        if (groupId != 0) {
            sqlQuery.append(" group_id IN (?) AND");
        }
        sqlQuery.append(" full_name LIKE ? AND role = ?");

        int param = 1;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int numberOfUser = 0;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            if (groupId != 0) {
                statement.setInt(param++, groupId);
            }
            fullName = Common.escapeSQLSpecialChar(fullName);
            statement.setNString(param++, "%" + fullName + "%");
            statement.setInt(param++, Constant.IS_USER);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfUser = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        } finally {
            close(connection, statement, resultSet);
        }
        return numberOfUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#getListUsers(int, int, int,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
            String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
        Connection connection = getConnection();
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(
                "SELECT tu.user_id, tu.full_name, DATE_FORMAT(tu.birthday,'%Y/%m/%d') AS birthday, mg.group_name, tu.email, tu.tel, mj.name_level, DATE_FORMAT(du.end_date,'%Y/%m/%d') AS end_date, du.total");
        sqlQuery.append(" FROM tbl_user tu");
        sqlQuery.append(" INNER JOIN mst_group mg ON(tu.group_id = mg.group_id)");
        sqlQuery.append(" LEFT JOIN tbl_detail_user_japan du ON(du.user_id = tu.user_id)");
        sqlQuery.append(" LEFT JOIN mst_japan mj ON(du.code_level = mj.code_level)");
        sqlQuery.append(" WHERE");
        if (groupId != 0) {
            sqlQuery.append(" tu.group_id IN (?) AND");
        }
        sqlQuery.append(" tu.full_name LIKE ? AND tu.role = ? ");
        sqlQuery.append(" ORDER BY");
        sqlQuery.append(" tu.full_name " + sortByFullName);
        sqlQuery.append(", mj.name_level " + sortByCodeLevel);
        sqlQuery.append(", du.end_date " + sortByEndDate);
        sqlQuery.append(" LIMIT ?");
        sqlQuery.append(" OFFSET ?");
        List<UserInfor> listUserInfor = new ArrayList<>();
        int param = 1;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            if (groupId != 0) {
                statement.setInt(param++, groupId);
            }
            fullName = Common.escapeSQLSpecialChar(fullName);
            statement.setNString(param++, "%" + fullName + "%");
            statement.setInt(param++, Constant.IS_USER);
            statement.setInt(param++, limit);
            statement.setInt(param++, offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserInfor userInfor = new UserInfor();
                userInfor.setUserId(resultSet.getInt("user_id"));
                userInfor.setFullName(resultSet.getString("full_name"));
                userInfor.setBirthdayString(resultSet.getString("birthday"));
                userInfor.setGroupName(resultSet.getString("group_name"));
                userInfor.setEmail(resultSet.getString("email"));
                userInfor.setTel(resultSet.getString("tel"));
                userInfor.setNameLevel(resultSet.getString("name_level"));
                userInfor.setEndDateString(resultSet.getString("end_date"));
                userInfor.setTotal(resultSet.getString("total"));
                listUserInfor.add(userInfor);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        } finally {
            close(connection, statement, resultSet);
        }
        return listUserInfor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#isAdmin(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean isAdmin(String loginName, String password) {
        Connection connection = getConnection();
        String sqlQuery = "SELECT u.user_id, u.password, u.salt FROM tbl_user AS u WHERE u.login_name = ? AND u.role = ?";
        int param = 1;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(sqlQuery);
            loginName = Common.escapeSQLSpecialChar(loginName);
            statement.setString(param++, loginName);
            statement.setInt(param++, Constant.IS_ADMIN);
            result = statement.executeQuery();

            if (result.next()) {
                String salt = result.getString("salt");
                String passwordInDB = result.getString("password");
                String securePassword = Common.getSHA1Secure(password, salt);
                if (securePassword.equals(passwordInDB)) {
                    result.close();
                    close(connection);
                    return true;
                }

            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        } finally {
            close(connection, statement, result);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#existsEmailExcept(int, java.lang.String)
     */
    @Override
    public boolean existsEmailExcept(int userId, String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            String sqlQuery = "SELECT u.email FROM tbl_user u WHERE u.email = ? AND NOT u.user_id = ?";

            statement = connection.prepareStatement(sqlQuery);
            email = Common.escapeSQLSpecialChar(email);
            int param = 1;
            statement.setString(param++, email);
            statement.setInt(param, userId);
            result = statement.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(connection, statement, result);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#existsLoginName(java.lang.String)
     */
    @Override
    public boolean existsLoginName(String loginName) {
        String sqlQuery = "SELECT u.login_name FROM tbl_user u WHERE u.login_name = ?";
        Connection connection = getConnection();

        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            loginName = Common.escapeSQLSpecialChar(loginName);
            statement.setString(1, loginName);

            result = statement.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(connection, statement, result);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#insertUser(manageuser.entities.TblUser,
     * java.sql.Connection)
     */
    @Override
    public boolean insertUser(TblUser tblUser, Connection connection) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("INSERT INTO tbl_user");
        sqlQuery.append(
                " (group_id, login_name, password, full_name, full_name_kana, email, tel, birthday, salt, role) VALUES");
        sqlQuery.append(" (?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            int param = 1;
            statement.setString(param++, tblUser.getGroupId());
            statement.setString(param++, tblUser.getLoginName());
            statement.setString(param++, tblUser.getPassword());
            statement.setString(param++, tblUser.getFullName());
            statement.setString(param++, tblUser.getFullNameKana());
            statement.setString(param++, tblUser.getEmail());
            statement.setString(param++, tblUser.getTel());
            statement.setString(param++, tblUser.getBirthday());
            statement.setString(param++, tblUser.getSalt());
            statement.setInt(param++, Constant.IS_USER);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(statement);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#getUserById(int)
     */
    @Override
    public TblUser getUserById(int userId) {
        Connection connection = getConnection();
        String sqlQuery = "SELECT * FROM tbl_user AS u WHERE u.user_id = ? AND role = ?";
        int param = 1;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        TblUser userInfor = new TblUser();
        try {
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(param++, userId);
            statement.setInt(param++, Constant.IS_USER);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userInfor.setUserId(resultSet.getInt("user_id"));
                userInfor.setGroupId(resultSet.getString("group_id"));
                userInfor.setLoginName(resultSet.getString("login_name"));
                userInfor.setPassword(resultSet.getString("password"));
                userInfor.setFullName(resultSet.getString("full_name"));
                userInfor.setFullNameKana(resultSet.getString("full_name_kana"));
                userInfor.setBirthday(resultSet.getString("birthday"));
                userInfor.setEmail(resultSet.getString("email"));
                userInfor.setTel(resultSet.getString("tel"));
                userInfor.setBirthday(resultSet.getString("birthday"));
                userInfor.setSalt(resultSet.getString("salt"));
                userInfor.setRole(resultSet.getInt("role"));
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return null;
        } finally {
            close(connection, statement, resultSet);
        }
        return userInfor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#getUserInforById(int)
     */
    @Override
    public UserInfor getUserInforById(int userId) {
        Connection connection = getConnection();
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(
                "SELECT tu.user_id, tu.group_id, tu.login_name, tu.full_name, tu.full_name_kana, DATE_FORMAT(tu.birthday,'%Y/%m/%d') AS birthday, mg.group_name, tu.email, tu.tel, tu.role, mj.code_level, mj.name_level, DATE_FORMAT(du.start_date,'%Y/%m/%d') AS start_date, DATE_FORMAT(du.end_date,'%Y/%m/%d') AS end_date, du.total");
        sqlQuery.append(" FROM tbl_user tu");
        sqlQuery.append(" INNER JOIN mst_group mg ON(tu.group_id = mg.group_id)");
        sqlQuery.append(" LEFT JOIN tbl_detail_user_japan du ON(du.user_id = tu.user_id)");
        sqlQuery.append(" LEFT JOIN mst_japan mj ON(du.code_level = mj.code_level)");
        sqlQuery.append(" WHERE");
        sqlQuery.append(" tu.user_id = ? ");
        int param = 1;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserInfor userInfor = new UserInfor();
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            statement.setInt(param++, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userInfor.setUserId(resultSet.getInt("user_id"));
                userInfor.setLoginName(resultSet.getString("login_name"));
                userInfor.setGroupId(resultSet.getString("group_id"));
                userInfor.setFullName(resultSet.getString("full_name"));
                userInfor.setFullNameKana(resultSet.getString("full_name_kana"));
                userInfor.setBirthdayString(resultSet.getString("birthday"));
                userInfor.setGroupName(resultSet.getString("group_name"));
                userInfor.setEmail(resultSet.getString("email"));
                userInfor.setTel(resultSet.getString("tel"));
                userInfor.setCodeLevel(resultSet.getString("code_level"));
                userInfor.setNameLevel(resultSet.getString("name_level"));
                userInfor.setStartDateString(resultSet.getString("start_date"));
                userInfor.setEndDateString(resultSet.getString("end_date"));
                userInfor.setTotal(resultSet.getString("total"));
                userInfor.setRole(resultSet.getInt("role"));
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return null;
        } finally {
            close(connection, statement, resultSet);
        }
        return userInfor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#updateUser(manageuser.entities.TblUser,
     * java.sql.Connection)
     */
    @Override
    public boolean updateUser(TblUser tblUser, Connection connection) {
        StringBuilder sqlQuery = new StringBuilder();
        boolean updatePassword = Common.isNullOrEmpty(tblUser.getPassword());
        sqlQuery.append("UPDATE tbl_user SET");
        sqlQuery.append(" group_id=?,");
        sqlQuery.append(" full_name=?,");
        sqlQuery.append(" full_name_kana=?,");
        sqlQuery.append(" email=?,");
        sqlQuery.append(" tel=?,");
        sqlQuery.append(" birthday=?");
        if (!updatePassword) {
            sqlQuery.append(", password=?,");
            sqlQuery.append(" salt=?");
        }
        sqlQuery.append(" WHERE user_id=?;");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            int param = 1;
            statement.setString(param++, tblUser.getGroupId());
            statement.setString(param++, tblUser.getFullName());
            statement.setString(param++, tblUser.getFullNameKana());
            statement.setString(param++, tblUser.getEmail());
            statement.setString(param++, tblUser.getTel());
            statement.setString(param++, tblUser.getBirthday());
            if (!updatePassword) {
                statement.setString(param++, tblUser.getPassword());
                statement.setString(param++, tblUser.getSalt());
            }
            statement.setInt(param++, tblUser.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(statement);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblUserDao#deleteUser(int, java.sql.Connection)
     */
    @Override
    public boolean deleteUser(int userId, Connection connection) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("DELETE FROM tbl_user");
        sqlQuery.append(" WHERE user_id=?;");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            int param = 1;
            statement.setInt(param++, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(statement);
        }
        return true;
    }
}
