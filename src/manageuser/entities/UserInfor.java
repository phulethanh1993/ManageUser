/**
 * Copyright(C) 2016
 * UserInfoADM002.java, 22/12/2016 Le Thanh Phu
 */
package manageuser.entities;

import java.util.List;

/**
 * Class chứa các thuộc tính là các thông tin của một User.
 * 
 * @author Le Thanh Phu
 *
 */
public class UserInfor {
    private int userId;
    private String loginName;
    private String groupId;
    private String groupName;
    private String fullName;
    private String fullNameKana;
    private List<Integer> birthday;
    private String birthdayString;
    private String email;
    private String tel;
    private String password;
    private String passwordConfirm;
    private String codeLevel;
    private String nameLevel;
    private List<Integer> startDate;
    private String startDateString;
    private List<Integer> endDate;
    private String endDateString;
    private String total;
    private String salt;
    private int role;

    public UserInfor() {
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     *            the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName
     *            the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the fullNameKana
     */
    public String getFullNameKana() {
        return fullNameKana;
    }

    /**
     * @param fullNameKana
     *            the fullNameKana to set
     */
    public void setFullNameKana(String fullNameKana) {
        this.fullNameKana = fullNameKana;
    }

    /**
     * @return the birthday
     */
    public List<Integer> getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(List<Integer> birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the birthdayString
     */
    public String getBirthdayString() {
        return birthdayString;
    }

    /**
     * @param birthdayString
     *            the birthdayString to set
     */
    public void setBirthdayString(String birthdayString) {
        this.birthdayString = birthdayString;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the passwordConfirm
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * @param passwordConfirm
     *            the passwordConfirm to set
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * @return the codeLevel
     */
    public String getCodeLevel() {
        return codeLevel;
    }

    /**
     * @param codeLevel
     *            the codeLevel to set
     */
    public void setCodeLevel(String codeLevel) {
        this.codeLevel = codeLevel;
    }

    /**
     * @return the nameLevel
     */
    public String getNameLevel() {
        return nameLevel;
    }

    /**
     * @param nameLevel
     *            the nameLevel to set
     */
    public void setNameLevel(String nameLevel) {
        this.nameLevel = nameLevel;
    }

    /**
     * @return the startDate
     */
    public List<Integer> getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(List<Integer> startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the startDateString
     */
    public String getStartDateString() {
        return startDateString;
    }

    /**
     * @param startDateString
     *            the startDateString to set
     */
    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    /**
     * @return the endDate
     */
    public List<Integer> getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(List<Integer> endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the endDateString
     */
    public String getEndDateString() {
        return endDateString;
    }

    /**
     * @param endDateString
     *            the endDateString to set
     */
    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     *            the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(int role) {
        this.role = role;
    }

}
