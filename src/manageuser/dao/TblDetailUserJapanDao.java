/**
 * Copyright(C) 2016
 * TblDetailUserJapanDao.java, 20/12/2016 LeThanhPhu
 */
package manageuser.dao;

import java.sql.Connection;

import manageuser.entities.TblDetailUserJapan;

/**
 * Interface chứa các phương thức thao tác với bảng tbl_detail_user_japan
 * 
 * @author LeThanhPhu
 *
 */
public interface TblDetailUserJapanDao {
    /**
     * Phương thức ghi các thông tin tương ứng của một user vào bảng
     * tbl_detail_user_japan
     * 
     * @param tblDetailUserJapan
     *            Một đối tượng chứa các thông tin cần thiết để ghi vào bảng
     *            tbl_detail_user_japan
     * @param connection
     *            Một connection kết nối tới DB.
     * @return true nếu insert thành công.<br />
     *         false trong các trường hợp còn lại.
     */
    public boolean insertOrUpdateIfExistsTUDJ(TblDetailUserJapan tblDetailUserJapan,
        Connection connection);

    /**
     * Phương thức cập nhât các thông tin tương ứng của một user vào bảng
     * tbl_detail_user_japan
     * 
     * @param tblDetailUserJapan
     *            Một đối tượng chứa các thông tin cần thiết để ghi vào bảng
     *            tbl_detail_user_japan
     * @param connection
     *            Một connection kết nối tới DB.
     * @return true nếu insert thành công.<br />
     *         false trong các trường hợp còn lại.
     */
    public boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan,
        Connection connection);

    /**
     * Phương thức lấy ra các thông tin ở bảng tbl_detail_user_japan tương ứng
     * với user id truyền vào
     * 
     * @param userId
     *            Id của user muốn lấy thông tin.
     * @return Một đối tượng {@link TblDetailUserJapan} chứa các thông tin của
     *         bản ghi có user id truyền vào.
     */
    public TblDetailUserJapan getDetailUserJapanByUserId(int userId);

    /**
     * Phương thức xóa bản ghi trong bảng tbl_detail_user_japan tương ứng với
     * user id truyền vào.
     * 
     * @param userId
     *            id của user muốn xóa trình độ tiếng nhật.
     * @param connection
     *            Một connection tới DB.
     * @return true nếu xóa thành công.<br />
     *         false trong trường hợp còn lại.
     */
    public boolean deleteDetailUserJapan(int userId, Connection connection);
}
