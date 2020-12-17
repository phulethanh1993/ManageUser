/**
 * Copyright(C) 2016
 * TblUserLogic.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.logics;

import java.util.List;

import manageuser.entities.UserInfor;

/**
 * Interface chứa các phương thức xử lý logic cho bảng tbl_user
 * 
 * @author Le Thanh Phu
 *
 */
public interface TblUserLogic {
    /**
     * Phương thức kiểm tra xem loginName và password có đúng là admin không.
     * 
     * @param loginName
     *            Tên đăng nhập
     * @param password
     *            Mật khẩu
     * @return true nếu tên đăng nhập và mật khẩu đồng thời tồn tại trong một
     *         bản ghi của bảng tbl_user
     */
    public boolean isAdmin(String loginName, String password);

    /**
     * Phương thức kiểm tra xem có tồn tại user có id truyền vào không.
     * 
     * @param userId
     * @return true nếu tồn tại user có id truyền vào.<br />
     *         false trong trường hợp còn lại.
     */
    public boolean isUser(int userId);

    /**
     * Phương thức tính tổng số User
     * 
     * @param groupId
     *            Group ID tìm kiếm.
     * @param fullName
     *            Tên tìm kiếm.
     * @return Tổng số user trong DB có group_id và full_name phù hợp với tên
     *         tìm kiếm
     */
    public int getTotalUsers(int groupId, String fullName);

    /**
     * Phương thức lấy các bản ghi thông tin của các user.
     * 
     * @param offset
     *            Vị trí đầu tiên cần lấy.
     * @param limit
     *            Số bản ghi cần lấy.
     * @param groupId
     *            Điều kiện tìm kiếm groupId.
     * @param fullName
     *            Điều kiện tìm kiếm fullName.
     * @param sortType
     *            Điều kiện sắp xếp ưu tiên.
     * @param sortByFullName
     *            Kiểu sắp xếp tăng hay giảm theo tên
     * @param sortByCodeLevel
     *            Kiểu sắp xếp tăng hay giảm theo CodeLevel
     * @param sortByEndDate
     *            Kiểu sắp xếp tăng hay giảm theo EndDate
     * @return
     */
    public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
            String sortByFullName, String sortByCodeLevel, String sortByEndDate);

    /**
     * Phương thức kiểm tra xem có tồn tại email nhập vào trong DB hay không
     * ngoại trừ user có id truyền vào.
     * 
     * @param email
     *            Email cần kiểm tra
     * @param userId
     *            id của user bỏ qua.
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
     * Phương thức tạo một user trong DB bao gồm các thông tin trong cả tbl_user
     * và tbl_detail_user_japan(nếu cần)
     * 
     * @param userInfor
     *            Đối tượng chứa các thuộc tính là các thông tin của một User
     * @return true nếu tạo thành công. <br / > false trong các trường hợp còn
     *         lại.
     */
    public boolean createUser(UserInfor userInfor);

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
     * Phương thức cập nhật thông tin của một user đã tồn tại trong DB.
     * 
     * @param userInfor
     *            Đối tượng chứa các thông tin chỉnh sửa.
     * @return true nếu update thành công <br />
     *         false trong các trường hợp còn lại.
     */
    public boolean updateUserInfor(UserInfor userInfor);

    /**
     * Phương thức xóa toàn bộ thông tin của một user khỏi DB
     * 
     * @param userId
     *            Id của user muốn xóa.
     * @return true nếu xóa thành công. <br />
     *         false trong trường hợp còn lại.
     */
    public boolean deleteUserInfor(int userId);
}
