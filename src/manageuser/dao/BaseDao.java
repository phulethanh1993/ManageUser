/**
 * Copyright(C) 2016
 * BaseDao.java, 19/12/2016 Le Thanh Phu
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Interface chứa các phương thức cơ bản để thao tác với Database như connect và
 * đóng connect
 * 
 * @author Le Thanh Phu
 *
 */
public interface BaseDao {
    /**
     * Phương thức tạo một Connection tới Database
     * 
     * @return Một Connection tới Database
     * 
     */
    public Connection getConnection();

    /**
     * Phương thức đóng một Connection đã tồn tại tới Database.
     * 
     * @param connection
     *            Connection cần đóng.
     */
    public void close(Connection connection);

    /**
     * Phương thức đóng một Statment đã tồn tại.
     * 
     * @param statement
     *            Statement cần đóng.
     */
    public void close(Statement statement);

    /**
     * Phương thức đóng một ResultSet đã tồn tại.
     * 
     * @param resultSet
     *            ResultSet cần đóng.
     */
    public void close(ResultSet resultSet);

    /**
     * Phương thức commit một Connection.
     * 
     * @param connection
     *            Connection cần commit và đóng.
     */
    public void commit(Connection connection);

    /**
     * Phương thức rollback bất kỳ thay đổi nào được thực hiện bởi Connection.
     * 
     * @param connection
     *            Connection muốn rollback.
     */
    public void rollback(Connection connection);

    /**
     * Phương thức thực hiện commit một Connection sau đó đóng Connection đó
     * lại.
     * 
     * @param connection
     *            Connection cần commit và close
     */
    public void commitAndClose(Connection connection);

    /**
     * Phương thức rollback bất kỳ thay đổi nào được thực hiện trên một
     * Connection sau đó đóng Connection đó lại.
     * 
     * @param connection
     *            Connection cần rollback và close.
     */
    public void rollbackAndClose(Connection connection);

    /**
     * Phương thức thực hiện close resultSet, statement và connection
     * 
     * @param connection
     *            Connection cần đóng.
     * 
     * @param statement
     *            Statement cần đóng.
     * @param resultSet
     *            ResultSet cần đóng.
     */
    public void close(Connection connection, Statement statement, ResultSet resultSet);
}
