/**
 * Copyright(C) 2016
 * TblDetailUserJapanImpl.java, 20/12/2016 LeThanhPhu
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapan;

/**
 * Class thực thi Interface {@link TblDetailUserJapanDao}
 * 
 * @author LeThanhPhu
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblDetailUserJapanDao#insertOrUpdateIfExistsTUDJ(
     * manageuser.entities.TblDetailUserJapan, java.sql.Connection)
     */
    @Override
    public boolean insertOrUpdateIfExistsTUDJ(TblDetailUserJapan tblDetailUserJapan, Connection connection) {
        boolean isUpdate = !(tblDetailUserJapan.getUserId() == 0);
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("INSERT INTO tbl_detail_user_japan");
        sqlQuery.append(" (user_id, code_level, start_date, end_date, total) VALUES");
        // Trường hợp insert (update) sau khi đã tồn tại bản ghi ở bảng
        // tbl_user
        if (isUpdate) {
            sqlQuery.append(" (?,?,?,?,?)");
            sqlQuery.append(" ON DUPLICATE KEY UPDATE");
            sqlQuery.append(" code_level=?,");
            sqlQuery.append(" start_date=?,");
            sqlQuery.append(" end_date=?,");
            sqlQuery.append(" total=?;");
            // Trường hợp insert cùng với bản ghi ở bảng tbl_user
        } else {
            sqlQuery.append(" (LAST_INSERT_ID(),?,?,?,?);");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            int param = 1;
            if (isUpdate) {
                statement.setInt(param++, tblDetailUserJapan.getUserId());
            }
            statement.setString(param++, tblDetailUserJapan.getCodeLevel());
            statement.setString(param++, tblDetailUserJapan.getStartDate());
            statement.setString(param++, tblDetailUserJapan.getEndDate());
            statement.setString(param++, tblDetailUserJapan.getTotal());
            if (isUpdate) {
                statement.setString(param++, tblDetailUserJapan.getCodeLevel());
                statement.setString(param++, tblDetailUserJapan.getStartDate());
                statement.setString(param++, tblDetailUserJapan.getEndDate());
                statement.setString(param++, tblDetailUserJapan.getTotal());
            }
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
     * @see
     * manageuser.dao.TblDetailUserJapanDao#updateDetailUserJapan(manageuser.
     * entities.TblDetailUserJapan, java.sql.Connection)
     */
    @Override
    public boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan, Connection connection) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("UPDATE tbl_detail_user_japan SET");
        sqlQuery.append(" code_level=?, start_date=?, end_date=?, total=?");
        sqlQuery.append(" WHERE user_id=?;");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery.toString());
            int param = 1;
            statement.setString(param++, tblDetailUserJapan.getCodeLevel());
            statement.setString(param++, tblDetailUserJapan.getStartDate());
            statement.setString(param++, tblDetailUserJapan.getEndDate());
            statement.setString(param++, tblDetailUserJapan.getTotal());
            statement.setInt(param, tblDetailUserJapan.getUserId());
            System.out.println(statement.executeUpdate());
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
     * @see manageuser.dao.TblDetailUserJapanDao#getDetailUserJapanByUserId(int)
     */
    @Override
    public TblDetailUserJapan getDetailUserJapanByUserId(int userId) {
        Connection connection = getConnection();
        String sqlQuery = "SELECT * FROM tbl_detail_user_japan AS tu WHERE tu.user_id = ?;";
        int param = 1;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        TblDetailUserJapan tblDetailUserJapan = null;

        try {
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(param, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tblDetailUserJapan = new TblDetailUserJapan();
                tblDetailUserJapan.setDetailUserJapanId(resultSet.getInt("detail_user_japan_id"));
                tblDetailUserJapan.setUserId(resultSet.getInt("user_id"));
                tblDetailUserJapan.setCodeLevel(resultSet.getString("code_level"));
                tblDetailUserJapan.setStartDate(resultSet.getString("start_date"));
                tblDetailUserJapan.setEndDate(resultSet.getString("end_date"));
                tblDetailUserJapan.setTotal(resultSet.getString("total"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            tblDetailUserJapan = null;
        } finally {
            close(connection, statement, resultSet);
        }
        return tblDetailUserJapan;

    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.TblDetailUserJapanDao#deleteTblDetailUserJapan(int,
     * java.sql.Connection)
     */
    @Override
    public boolean deleteDetailUserJapan(int userId, Connection connection) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("DELETE FROM tbl_detail_user_japan");
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
