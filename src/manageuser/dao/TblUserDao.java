/**
 * Copyright(C) 2016
 * TblUserDao.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.dao;

import java.sql.Connection;
import java.util.List;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * Interface chứa các phương thức thao tác với bảng tbl_user
 * 
 * @author Le Thanh Phu
 *
 */
public interface TblUserDao {

    /**
     * Phương thức tính tổng số bản ghi thỏa mãn điều kiện tìm kiếm.
     * 
     * @param groupId
     *            Điều kiện tìm kiếm theo group.
     * @param fullName
     *            Điều kiện tìm kiếm theo tên.
     * @return Tổng số bản ghi thỏa mãn cả hai điều kiện tìm kiếm.
     */
    public int getTotalUsers(int groupId, String fullName);

    /**
     * Phương thức lấy ra các bản ghi là các thông tin của một User.
     * 
     * @param offset
     *            Số thứ tự bản ghi cần lấy.
     * @param limit
     *            Số lượng bản ghi cần lấy.
     * @param groupId
     *            Điều kiện tìm kiếm theo groupId
     * @param fullName
     *            Điều kiện tìm kiếm theo tên.
     * @param sortType
     *            Tiêu chí ưu tiên sắp xếp.
     * @param sortByFullName
     *            Sắp xếp tăng hay giảm theo tên.
     * @param sortByCodeLevel
     *            Sắp xếp tăng hay giảm theo CodeLevel.
     * @param sortByEndDate
     *            Sắp xếp tăng hay giảm theo ngày hết hạn.
     * @return Một List các {@link UserInfor} thỏa mãn các điều kiện tìm kiếm và
     *         đã được sắp xếp.
     * 
     */
    public List<UserInfor> getListUsers(int offset, int limit, int groupId,
        String fullName, String sortType, String sortByFullName,
        String sortByCodeLevel, String sortByEndDate);

    /**
     * Phương thức kiểm tra xem loginName và password có tồn tại trong DB trên
     * cùng 1 bản ghi hay không.
     * 
     * @param loginName
     *            Tên đăng nhập
     * @param password
     *            Mật khẩu
     * @return true nếu tên đăng nhập và mật khẩu đồng thời tồn tại trong một
     *         bản ghi của bảng tbl_user
     * 
     */
    public boolean isAdmin(String loginName, String password);

    /**
     * Phương thức kiểm tra xem có tồn tại email nhập vào trong DB hay không.
     * 
     * @param email
     *            Email cần kiểm tra
     * @return true nếu tồn tại email trong DB.<br />
     *         false trong các trường hợp còn lại
     */
    public boolean existsEmailExcept(int userId, String email);

    /**
     * Phương thức kiểm tra xem có tồn tại tên đăng nhập nhập vào trong DB hay
     * không.
     * 
     * @param loginName
     *            Tên đăng nhập.
     * @return true nếu tồn tại Tên đăng nhập nhập vào trong DB.<br />
     *         false trong các trường hợp còn lại
     */
    public boolean existsLoginName(String loginName);

    /**
     * Phương thức ghi các thông tin tương ứng của một user vào bảng tbl_user
     * 
     * @param tblUser
     *            Một đối tượng chứa các thuộc tính là các thông tin cần thiết
     *            để ghi vào tbl_user
     * @param Một
     *            connection kết nối tới DB.
     * @return true nếu insert thành công.<br />
     *         false trong trường hợp còn lại.
     */
    public boolean insertUser(TblUser tblUser, Connection connection);

    /**
     * Phương thức lấy ra một đối tượng tblUser bao gồm đầy đủ các thuộc tinh.
     * 
     * @param userId
     *            Id của user muốn lấy ra.
     * @return Đối tượng TblUser.
     */
    public TblUser getUserById(int userId);

    /**
     * Phương thức lấy ra một đối tượng UserInfor bao gồm các thông tin hiển thị
     * ở màn hình ADM005.
     * 
     * @param userId
     *            Id của userInfor
     * @return Một đối tượng UserInfor.
     */
    public UserInfor getUserInforById(int userId);

    /**
     * Phương thức update các thông tin của một user.
     * 
     * @param tblUser
     *            Một đối tượng chứa các thuộc tính là các thông tin cần thiết
     *            để ghi vào tbl_user
     * @param connection
     *            Một connection kết nối tới DB.
     * @return true nếu update thành công.<br />
     *         false trong trường hợp còn lại.
     */
    public boolean updateUser(TblUser tblUser, Connection connection);

    /**
     * Phương thức xóa một bản ghi trong bảng tbl_user
     * 
     * @param userId
     *            Id của user muốn xóa.
     * @param connection
     *            Một connection kết nối tới DB.
     * @return true nếu xóa thành công.<br />
     *         false trong trường hợp còn lại.
     */
    public boolean deleteUser(int userId, Connection connection);

}
