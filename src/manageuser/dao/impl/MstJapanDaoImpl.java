/**
 * Copyright(C) 2016
 * MstJapanDaoImpl.java, 13/12/2016 LeThanhPhu
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapan;

/**
 * Class thực thi Interface {@link MstJapanDao}
 * 
 * @author LeThanhPhu
 *
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

    /* (non-Javadoc)
     * @see manageuser.dao.MstJapanDao#getAllMstGroup()
     */
    @Override
    public List<MstJapan> getAllMstGroup() {
        Connection connection = getConnection();
        String sqlGetAllMstJapan = "SELECT * FROM mst_japan ORDER BY code_level ASC";
        List<MstJapan> allMstJapan = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sqlGetAllMstJapan);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int param = 1;
                MstJapan mstJapan = new MstJapan();
                mstJapan.setCodeLevel(resultSet.getString(param++));
                mstJapan.setNameLevel(resultSet.getString(param));
                allMstJapan.add(mstJapan);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return allMstJapan;
        } finally {
            close(connection, statement, resultSet);
        }
        return allMstJapan;
    }

}
