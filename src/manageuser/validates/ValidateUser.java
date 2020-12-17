/**
 * Copyright(C) 2016
 * ValidateUser.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.validates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.UserInfor;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Properties;

/**
 * Class chứa phương thức xử lý khi người dùng nhập liệu.
 * 
 * @author Le Thanh Phu
 *
 */
public class ValidateUser {
    private TblUserLogicImpl tblUserLogicImpl;
    private MstGroupDaoImpl mstGroupDaoImpl;
    private MstJapanDaoImpl mstJapanDaoImpl;

    /**
     * Phương thức kiểm tra logiName và password.
     * 
     * @param loginName
     *            Tên đăng nhập.
     * @param password
     *            Mật khẩu.
     * @return Một {@link List} các lỗi.
     */
    public List<String> validateLogin(String loginName, String password) {
        List<String> listError = new ArrayList<>();
        tblUserLogicImpl = new TblUserLogicImpl();
        if (loginName == null || "".equals(loginName)) {
            listError.add(Properties.getErrorMessageJapan("ER001A"));
        }
        if (password == null || "".equals(password)) {
            listError.add(Properties.getErrorMessageJapan("ER001B"));
        }
        if (listError.size() != 0) {
            return listError;
        } else if (!tblUserLogicImpl.isAdmin(loginName, password)) {
            listError.add(Properties.getErrorMessageJapan("ER016"));
        }

        return listError;
    }

    /**
     * Phương thức kiểm tra tính hợp lệ của các thông tin người dùng nhập vào
     * trường hợp add mới User.
     * 
     * @param userInfor
     *            Đối tượng có các thuộc tính là các thông tin tại màn hình
     *            ADM003.
     * @param caseValid
     *            Trường hợp valid:<br />
     *            add - Trường hợp add mới user.<br />
     *            edit - Trường hợp edit user.
     * @return Một {@link List} các lỗi.
     */
    public List<String> validateUserInfor(UserInfor userInfor) {
        List<String> listError = new ArrayList<>();
        int userId = userInfor.getUserId();
        String loginName = userInfor.getLoginName();
        String groupId = userInfor.getGroupId();
        String fullName = userInfor.getFullName();
        String fullNameKana = userInfor.getFullNameKana();
        List<Integer> birthday = userInfor.getBirthday();
        String email = userInfor.getEmail();
        String tel = userInfor.getTel();
        String password = userInfor.getPassword();
        String passwordConfirm = userInfor.getPasswordConfirm();
        String codeLevel = userInfor.getCodeLevel();
        List<Integer> startDate = userInfor.getStartDate();
        List<Integer> endDate = userInfor.getEndDate();
        String total = userInfor.getTotal();

        if (userId == 0) {
            listError.add(checkLoginName(loginName));
            listError.add(checkPassword(password));
            listError.add(checkPasswordConfirm(password, passwordConfirm));
        }
        listError.add(checkEmail(userId, email));
        listError.add(checkGroupId(groupId));
        listError.add(checkfullName(fullName));
        if (!Common.isNullOrEmpty(fullNameKana)) {
            listError.add(checkfullNameKana(fullNameKana));
        }
        listError.add(checkBirthday(birthday));
        listError.add(checkTel(tel));
        if (!notExistsCodeLevel(codeLevel)) {
            listError.add(checkStartDate(startDate));
            listError.add(checkEndDate(startDate, endDate));
            listError.add(checkTotal(total));
        }
        listError.removeIf(Objects::isNull);
        return listError;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với tên tài khoản.
     * 
     * @param loginName
     *            Tên tài khoản cần kiểm tra.
     * @return Một {@link List} các lỗi. (Rỗng nếu không có lỗi)
     */
    private String checkLoginName(String loginName) {
        String error = null;
        if (Common.isNullOrEmpty(loginName)) {
            error = Properties.getErrorMessageJapan("ER001A");
        } else if (!Common.checkFormat("loginName", loginName)) {
            error = Properties.getErrorMessageJapan("ER019A");
        } else if (!Common.checkLength(6, 15, loginName)) {
            error = Properties.getErrorMessageJapan("ER007A");
        } else if (Common.checkOneByte(loginName)) {
            error = Properties.getErrorMessageJapan("ER008A");
        } else if (existsLoginName(loginName)) {
            error = Properties.getErrorMessageJapan("ER003A");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với group ID.
     * 
     * @param groupId
     *            Group ID cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkGroupId(String groupId) {
        String error = null;
        if ("0".equals(groupId)) {
            error = Properties.getErrorMessageJapan("ER002G");
        } else if (notExistsGroupId(groupId)) {
            error = Properties.getErrorMessageJapan("ER004G");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với full name.
     * 
     * @param fullName
     * 
     *            Full name cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkfullName(String fullName) {
        String error = null;
        if (Common.isNullOrEmpty(fullName)) {
            error = Properties.getErrorMessageJapan("ER001N");
        } else if (!Common.checkLength(0, 255, fullName)) {
            error = Properties.getErrorMessageJapan("ER006N");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với full name katakana.
     * 
     * @param fullNameKana
     * 
     *            Full name katakana cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkfullNameKana(String fullNameKana) {
        String error = null;
        if (!Common.checkKanaString(fullNameKana)) {
            error = Properties.getErrorMessageJapan("ER009K");
        } else if (!Common.checkLength(0, 255, fullNameKana)) {
            error = Properties.getErrorMessageJapan("ER006K");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với ngày sinh.
     * 
     * @param birthday
     * 
     *            Ngày sinh cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkBirthday(List<Integer> birthday) {
        String error = null;
        if (!Common.isRealDay(birthday.get(0), birthday.get(1), birthday.get(2))) {
            error = Properties.getErrorMessageJapan("ER011R");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với Email.
     * 
     * @param userId
     *            Id của user bỏ qua.
     * @param email
     *            Email cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkEmail(int userId, String email) {
        String error = null;
        if (Common.isNullOrEmpty(email)) {
            error = Properties.getErrorMessageJapan("ER001E");
        } else if (Common.checkOneByte(email)) {
            error = Properties.getErrorMessageJapan("ER008E");
        } else if (!Common.checkFormat("email", email)) {
            error = Properties.getErrorMessageJapan("ER005E");
        } else if (!Common.checkLength(0, 255, email)) {
            error = Properties.getErrorMessageJapan("ER006E");
        } else if (existsEmailExcept(userId, email)) {
            error = Properties.getErrorMessageJapan("ER003E");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với số điện thoại
     * 
     * @param tel
     * 
     *            Tel cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkTel(String tel) {
        String error = null;
        if (Common.isNullOrEmpty(tel)) {
            error = Properties.getErrorMessageJapan("ER001T");
        } else if (Common.checkOneByte(tel)) {
            error = Properties.getErrorMessageJapan("ER018T");
        } else if (!Common.checkFormat("tel", tel)) {
            error = Properties.getErrorMessageJapan("ER005T");
        } /*
             * else if (!checkLength(0, 255, tel)) { error =
             * Properties.getErrorMessageJapan("ER006T"); }
             */
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với mật khẩu
     * 
     * @param password
     *            Password cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkPassword(String password) {
        String error = null;
        if (Common.isNullOrEmpty(password)) {
            error = Properties.getErrorMessageJapan("ER001B");
        } else if (Common.checkOneByte(password)) {
            error = Properties.getErrorMessageJapan("ER008B");
        } else if (!Common.checkLength(8, 15, password)) {
            error = Properties.getErrorMessageJapan("ER007B");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với mật khẩu xác nhận
     * 
     * @param password
     *            password đã nhập.
     * @param passwordConfirm
     *            Password Confirm cần kiểm tra.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkPasswordConfirm(String password, String passwordConfirm) {
        String error = null;
        if (!passwordConfirm.equals(password)) {
            error = Properties.getErrorMessageJapan("ER017P");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với ngày cấp chứng chỉ.
     * 
     * @param startDate
     * 
     *            Ngày cấp chứng chỉ.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkStartDate(List<Integer> startDate) {
        String error = null;
        if (!Common.isRealDay(startDate.get(0), startDate.get(1), startDate.get(2))) {
            error = Properties.getErrorMessageJapan("ER011S");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với ngày hết hạn chứng chỉ.
     * 
     * @param endDate
     * 
     *            Ngày hết hạn chứng chỉ.
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkEndDate(List<Integer> startDate, List<Integer> endDate) {
        String error = null;
        if (!Common.isRealDay(endDate.get(0), endDate.get(1), endDate.get(2))) {
            error = Properties.getErrorMessageJapan("ER011D");
        } else if (!Common.compareDate(startDate, endDate)) {
            error = Properties.getErrorMessageJapan("ER012D");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra các lỗi có thể xảy ra với tổng điểm.
     * 
     * @param total
     *            Tổng điểm cần check
     * @return Một lỗi đầu tiên gặp phải.<br />
     *         Null nếu không có lỗi.
     */
    private String checkTotal(String total) {
        String error = null;
        if (Common.isNullOrEmpty(total)) {
            error = Properties.getErrorMessageJapan("ER001L");
        } else if (Common.checkOneByte(total)) {
            error = Properties.getErrorMessageJapan("ER018L");
        } else if (!Common.checkFormat("total", total)) {
            error = Properties.getErrorMessageJapan("ER005L");
        }
        return error;
    }

    /**
     * Phương thức kiểm tra một Tên đăng nhập đã tồn tại trong DB chưa.
     * 
     * @param loginName
     *            Tên đăng nhập cần kiểm tra
     * @return true nếu tên đăng nhập đã tồn tại.<br />
     *         false trong các trường hợp còn lại.
     */
    private boolean existsLoginName(String loginName) {
        tblUserLogicImpl = new TblUserLogicImpl();
        return tblUserLogicImpl.existsLoginName(loginName);
    }

    /**
     * Phương thức kiểm tra một Email đã tồn tại trong DB chưa, ngoại trừ user
     * có id truyền vào.
     * 
     * @param userId
     *            Id của user bỏ qua.
     * @param loginName
     *            Email cần kiểm tra
     * @return true nếu Email đã tồn tại.<br />
     *         false trong các trường hợp còn lại.
     */
    private boolean existsEmailExcept(int userId, String email) {
        tblUserLogicImpl = new TblUserLogicImpl();
        return tblUserLogicImpl.existsEmailExcept(userId, email);
    }

    /**
     * Phương thức kiểm tra sự tồn tại của code level.
     * 
     * @param codeLevel
     *            Code level cần kiểm tra.
     * @return true nếu code level không tồn tại. <br />
     *         false trong các trường hợp còn lại.
     */
    private boolean notExistsCodeLevel(String codeLevel) {
        mstJapanDaoImpl = new MstJapanDaoImpl();
        List<MstJapan> allMstJapan = mstJapanDaoImpl.getAllMstGroup();
        for (MstJapan mstJapan : allMstJapan) {
            if (mstJapan.getCodeLevel().equals(codeLevel)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Phương thức kiểm tra sự tồn tại của group id
     * 
     * @param groupId
     *            Group Id cần kiểm tra.
     * @return true nếu Group Id không tồn tại. <br />
     *         false trong các trường hợp còn lại.
     */
    private boolean notExistsGroupId(String groupId) {
        mstGroupDaoImpl = new MstGroupDaoImpl();
        List<MstGroup> allGroup = mstGroupDaoImpl.getAllMstGroup();
        for (MstGroup mstGroup : allGroup) {
            String temp = mstGroup.getGroupId() + "";
            if (temp.equals(groupId)) {
                return false;
            }
        }
        return true;
    }

}
