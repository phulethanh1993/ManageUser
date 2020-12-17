/**
 * Copyright(C) 2016
 * MstGroupImpl.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroup;

/**
 * Class thực thi Interface {@link MstGroupDao}
 * 
 * @author Le Thanh Phu
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

    /* (non-Javadoc)
     * @see manageuser.dao.MstGroupDao#getAllMstGroup()
     */
    @Override
    public List<MstGroup> getAllMstGroup() {
        Connection connection = getConnection();
        String sqlGetAllGroup = "SELECT * FROM mst_group ORDER BY group_id ASC";
        List<MstGroup> allMstGroup = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet allGroup = null;
        try {
            statement = connection.prepareStatement(sqlGetAllGroup);
            allGroup = statement.executeQuery();
            while (allGroup.next()) {
                int param = 1;
                MstGroup mstGroup = new MstGroup();
                mstGroup.setGroupId(allGroup.getInt(param++));
                mstGroup.setGroupName(allGroup.getString(param));
                allMstGroup.add(mstGroup);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return allMstGroup;
        } finally {
            close(connection, statement, allGroup);
        }
        return allMstGroup;
    }

}
