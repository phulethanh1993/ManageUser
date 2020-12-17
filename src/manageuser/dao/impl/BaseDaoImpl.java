/**
 * Copyright(C) 2016
 * BaseDaoImpl.java, 19/12/2016 Le Thanh Phu
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import manageuser.dao.BaseDao;
import manageuser.utils.Properties;

/**
 * Class thực thi Interface {@link BaseDao}
 * 
 * @author Le Thanh Phu
 *
 */
public class BaseDaoImpl implements BaseDao {

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#getConnection()
     */
    @Override
    public Connection getConnection() {
        String url = Properties.getConfig("url");
        String userName = Properties.getConfig("userName");
        String password = Properties.getConfig("password");
        Connection connection = null;
        try {
            Class.forName(Properties.getConfig("classSqlName"));
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#close(java.sql.Connection)
     */
    @Override
    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#close(java.sql.Statement)
     */
    @Override
    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#close(java.sql.ResultSet)
     */
    @Override
    public void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#commit(java.sql.Connection)
     */
    @Override
    public void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#rollback(java.sql.Connection)
     */
    @Override
    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#commitAndClose(java.sql.Connection)
     */
    @Override
    public void commitAndClose(Connection connection) {
        commit(connection);
        close(connection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#rollbackAndClose(java.sql.Connection)
     */
    @Override
    public void rollbackAndClose(Connection connection) {
        rollback(connection);
        close(connection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.dao.BaseDao#close(java.sql.Connection,
     * java.sql.Statement, java.sql.ResultSet)
     */
    @Override
    public void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }

}
