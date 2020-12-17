/**
 * Copyright(C) 2016
 * Common.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * Class chứa các phương thức common cho dự án.
 * 
 * @author Le Thanh Phu
 *
 */
public class Common {
    /**
     * Phương thức kiểm tra một request tới đã login hay chưa.
     * 
     * @param session
     *            {@link HttpSession} của request.
     * @return
     */
    public static boolean checkLogin(HttpSession session) {
    if (session == null || session.getAttribute("loginName") == null) {
        return false;
    }
    return true;
    }

    /**
     * Phương thức tính toán các số trang cần hiển thị.
     * 
     * @param totalRecords
     *            Tổng số bản ghi phù hợp với điều kiện tìm kiếm.
     * @param limit
     *            Số bản ghi hiển thị trên 1 page.
     * @param currentPage
     *            Số page hiện tại.
     * @param pageLimit
     *            Tổng số page hiển thị.
     * @return Một List chứa các số trang cần hiển thị.
     */
    public static List<Integer> getListPaging(int totalRecords, int limit,
        int currentPage) {
    List<Integer> listPaging = new ArrayList<>();
    int totalPage = getTotalPage(totalRecords, limit);
    if (totalPage == 0 || totalPage == 1) {
        return listPaging;
    }
    if (currentPage > totalPage || currentPage <= 0) {
        currentPage = 1;
    }
    int pageLimit = getPageRange();
    int startPage = getStartPage(currentPage, pageLimit);
    int endPage = getEndPage(startPage, pageLimit, totalPage);
    for (int i = startPage; i <= endPage; i++) {
        listPaging.add(i);
    }
    return listPaging;
    }

    /**
     * Phương thức lấy ra đoạn của trang hiện tại (pageLimit = 3, currentPage =
     * 1 hoặc 2 hoặc 3 => currentSegment = 1)
     * 
     * @param currentPage
     *            Số trang hiện tại.
     * @param pageLimit
     *            Tổng số page hiển thị.
     * @return Đoạn của trang hiện tại.
     */
    public static int getCurrentSegment(int currentPage, int pageLimit) {
    return (currentPage - 1) / pageLimit + 1;
    }

    /**
     * Phương thức lấy ra số trang đầu tiên cần hiển thị.
     * 
     * @param currentPage
     *            Số page hiện tại.
     * @param pageLimit
     *            Tổng số page hiển thị.
     * @return Số page đầu tiên cần hiển thị.
     */
    public static int getStartPage(int currentPage, int pageLimit) {
    int currentSegment = getCurrentSegment(currentPage, pageLimit);
    int startPage = (currentSegment - 1) * pageLimit + 1;
    return startPage;
    }

    /**
     * Phương thức lấy ra số trang cuối cùng cần hiển thị.
     * 
     * @param startPage
     *            Số page đầu tiên cần hiển thị.
     * @param pageLimit
     *            Tổng số page hiển thị
     * @param totalPage
     *            Tổng số page tương ứng với tổng số bản ghi.
     * @return Số page cuối cùng cần hiển thị.
     */
    public static int getEndPage(int startPage, int pageLimit, int totalPage) {
    int endPage = pageLimit;
    if (startPage + pageLimit - 1 <= totalPage) {
        endPage = startPage + pageLimit - 1;
    } else {
        endPage = totalPage;
    }
    return endPage;
    }

    /**
     * Phương thức lấy tổng số page muốn hiển thị trong file config.
     * 
     * @return Số trang hiển thị.
     */
    public static int getPageRange() {
    return Integer.parseInt(Properties.getConfig("pageRange"));
    }

    /**
     * Phương thức tính vị trí offset của bản ghi cần lấy trong DB.
     * 
     * @param currentPage
     *            Trang hiện tại.
     * @param limit
     *            Số bản ghi hiển thị trên 1 page.
     * @return Vị trí offset của bản ghi cần lấy trong DB
     */
    public static int getOffset(int currentPage, int limit) {
    return (currentPage - 1) * limit;
    }

    /**
     * Phương thức lấy tổng số bản ghi cần hiển thị trong file config.
     * 
     * @return Số bản ghi hiển thị trên 1 page.
     */
    public static int getLimit() {
    return Integer.parseInt(Properties.getConfig("limitADM002"));
    }

    /**
     * Phương thức tính tổng số page.
     * 
     * @param totalUser
     *            Tổng số bản ghi dữ liệu.
     * @param limit
     *            Số bản ghi hiển thị trên 1 page.
     * @return Tổng số page.
     */
    public static int getTotalPage(int totalUser, int limit) {
    if (totalUser <= 0) {
        return 0;
    }
    return ((totalUser - 1) / limit + 1);
    }

    /**
     * Phương thức chuyển số page dạng String sang int
     * 
     * @param number
     *            Số page dưới dạng String
     * @return Số page dưới dạng int
     */
    public static int convertPageNumber(String number) {
    int pageInt = 1;
    try {
        pageInt = Integer.parseInt(number);
    } catch (NumberFormatException e) {
        pageInt = 1;
    }
    if (pageInt <= 0) {
        pageInt = 1;
    }
    return pageInt;
    }

    /**
     * Phương thức chuyển group id dạng String sang int
     * 
     * @param id
     *            ID dạng String
     * @return ID dạng int
     */
    public static int convertGroupId(String id) {
    int idInt = 0;
    try {
        idInt = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        idInt = 0;
    }
    if (idInt < 0 || idInt > 4) {
        idInt = 0;
    }
    return idInt;
    }

    /**
     * Phương thức kiểm tra String input có nằm trong white list hay không.
     * 
     * @param input
     *            Chuỗi đầu vào.
     * @return true nếu input là một trong số white list.<br />
     *         false trong các trường hợp còn lại
     */
    public static boolean checkWhiteList(String input) {
    String[] listSortColumn = Properties.getConfig("whiteList").split("/");
    for (int i = 0; i < listSortColumn.length; i++) {
        if (listSortColumn[i].equals(input)) {
        return true;
        }
    }
    return false;
    }

    /**
     * Phương thức ghi vào log file stack trace đã catch được
     * 
     * @param exception
     *            Exception đã cath được muốn ghi stack trace vào file log.
     * @param logPath
     *            Đường dẫn tới file muốn ghi.
     * 
     * @throws IOException
     *             Ngoại lệ có thể xảy ra trong quá trình tạo hoặc ghi file.
     */
    public static void printStackTrace(Exception exception, String logPath)
        throws IOException {
    File file = new File(logPath);
    if (!file.exists()) {
        file.createNewFile();
    }
    PrintWriter printWriter = new PrintWriter(new FileWriter(file, true),
        true);
    Date date = new Date();
    printWriter.println("***********************************");
    printWriter.println(date.toString());
    exception.printStackTrace(printWriter);
    printWriter.close();
    }

    /**
     * Phương thức thay thế các ký tự đặc biệt của SQL để tránh lỗi.
     * 
     * @param input
     *            Chuỗi đầu vào.
     * @return Chuỗi sau khi đã thay thế ký tự đặc biệt.
     */
    public static String escapeSQLSpecialChar(String input) {
    return input.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
    }

    /**
     * Phương thức hash một chuỗi cùng với chuỗi salt sử dụng thuật toán hash
     * SHA-1
     * 
     * @param stringToGet
     *            Chuỗi cần hash.
     * @param salt
     *            Chuỗi salt muốn cộng với chuỗi đầu vào trước khi hash (salt +
     *            chuỗi đầu vào). Có thể truyền vào chuỗi rỗng nếu không muốn sử
     *            dụng.
     * @return Một chuỗi 40 ký tự hex sau khi đã hash.
     */
    public static String getSHA1Secure(String stringToGet, String salt) {
    String generatedString = null;
    try {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(salt.getBytes());
        messageDigest.update(stringToGet.getBytes());
        byte[] data = messageDigest.digest();
        generatedString = convertBytesToHexString(data);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
    return generatedString;
    }

    /**
     * Phương thức lấy ra mảng byte ngẫu nhiên.
     * 
     * @param length
     *            Độ dài mảng byte.
     * @return Mảng byte ngẫu nhiên.
     */
    public static byte[] getSecureRandomBytes(int length) {
    if (length < 1) {
        return null;
    }
    SecureRandom secureRandom = null;
    byte[] randomBytes = new byte[length];
    try {
        secureRandom = SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
    secureRandom.nextBytes(randomBytes);
    return randomBytes;
    }

    /**
     * Phương thức chuyển đổi một mảng byte thành một chuỗi string hexa.
     * 
     * @param input
     *            Mảng byte cần chuyển.
     * @return Chuỗi hexa sau khi đã chuyển.
     */
    public static String convertBytesToHexString(byte[] input) {
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < input.length; i++) {
        hexString.append(Integer.toHexString((input[i] & 0xff) + 0x100)
            .substring(1));
    }
    return hexString.toString().toUpperCase();
    }

    /**
     * Phương thức lấy ra danh sách các năm từ năm bắt đầu đến năm kết thúc.
     * 
     * @param startYear
     *            Năm bắt đầu.
     * @param endYear
     *            Năm kết thúc.
     * @return Một {@link List} các năm từ năm bắt đầu tới năm kết thúc.
     */
    public static List<Integer> getListYear(int startYear, int endYear) {
    List<Integer> listYears = new ArrayList<>();
    for (int i = startYear; i <= endYear; i++) {
        listYears.add(i);
    }
    return listYears;
    }

    /**
     * Phương thức lấy ra danh sách các tháng trong năm.
     * 
     * @return Một {@link List} các tháng trong năm.
     */
    public static List<Integer> getListMonth() {
    List<Integer> listMonths = new ArrayList<>();
    for (int i = 1; i <= 12; i++) {
        listMonths.add(i);
    }
    return listMonths;
    }

    /**
     * Phương thức lấy ra danh sách các ngày trong tháng.
     * 
     * @return Một {@link List} các ngày trong tháng.
     */
    public static List<Integer> getListDay() {
    List<Integer> listDays = new ArrayList<>();
    for (int i = 1; i <= 31; i++) {
        listDays.add(i);
    }
    return listDays;
    }

    /**
     * Phương thức lấy ra năm, tháng, ngày hiện tại.
     * 
     * @return Một {@link List} = {Năm, Tháng, Ngày} hiện tại
     */
    public static List<Integer> getCurrentYearMonthDay() {
    LocalDate now = LocalDate.now();
    int currentDay = now.getDayOfMonth();
    int currentMonth = now.getMonthValue();
    int currentYear = now.getYear();
    List<Integer> currentDate = new ArrayList<>();
    currentDate.add(currentYear);
    currentDate.add(currentMonth);
    currentDate.add(currentDay);
    return currentDate;
    }

    /**
     * Phương thức lấy ra năm hiện tại.
     * 
     * @return Năm hiện tại.
     */
    public static int getYearNow() {
    LocalDate now = LocalDate.now();
    return now.getYear();
    }

    /**
     * Phương thức chuyển đổi một ngày dưới dạng mảng String thành một
     * {@link List} chứa thông tin Ngày-Tháng-Năm.
     * 
     * @param date
     *            Một ngày dưới dạng Date.
     * 
     * @return Một {@link List} chứa thông tin Ngày-Tháng-Năm.
     */
    public static List<Integer> toDateArrayInt(String[] param) {
    List<Integer> yearMonthDay = new ArrayList<>();
    yearMonthDay.add(Integer.parseInt(param[0]));
    yearMonthDay.add(Integer.parseInt(param[1]));
    yearMonthDay.add(Integer.parseInt(param[2]));
    return yearMonthDay;
    }

    public static List<Integer> toDateArrayInt(String date) {
    String[] dateArray = date.split("/");
    return toDateArrayInt(dateArray);
    }

    /**
     * Phương thức chuyển đổi thông tin một ngày dưới dạng List thành dạng
     * String để hiển thị.
     * 
     * @param date
     *            Thông tin của ngày dưới dạng List.
     * @return Thông tin của ngày dưới dạng String.
     */
    public static String toDateString(List<Integer> date) {
    StringBuilder dateString = new StringBuilder();
    dateString.append(date.get(0));
    dateString.append("/");
    dateString.append(date.get(1));
    dateString.append("/");
    dateString.append(date.get(2));
    return dateString.toString();
    }

    /**
     * Phương thức mã hóa một mảng bytes thành một chuỗi ký tự ASCII theo thuật
     * toán Base64.
     * 
     * @param input
     *            Mảng byte cần mã hóa.
     * @return Chuỗi ký tự ASCII sau khi đã mã hóa.
     */
    public static String encodeBase64(String input) {
    byte[] encodeBytes = Base64.getEncoder().encode(input.getBytes());
    return new String(encodeBytes);
    }

    /**
     * Phương thức kiểm tra một chuỗi đầu vào có null hoặc rỗng không.
     * 
     * @param input
     *            Chuỗi cần kiểm tra.
     * @return true nếu chuỗi đầu vào null hoặc rỗng.<br />
     *         false trong các trường hợp còn lại.
     */
    public static boolean isNullOrEmpty(String input) {
    return ("".equals(input) || input == null);
    }

    /**
     * Phương thức kiểm tra độ dài một chuỗi có độ dài nằm trong khoản min và
     * max length hay không.
     * 
     * @param minLength
     *            Độ dài chuỗi nhỏ nhất.
     * @param maxLength
     *            Độ dài chuỗi lớn nhất.
     * @param input
     *            Chuỗi cần kiểm tra.
     * @return true nếu chuỗi có độ dài nằm trong đoạn minLength và
     *         maxLength.<br />
     *         false trong các trường hợp còn lại.
     */
    public static boolean checkLength(int minLength, int maxLength,
        String input) {
    if (input == null || minLength > maxLength) {
        return false;
    }
    int inputLength = input.length();
    if (inputLength < minLength || inputLength > maxLength) {
        return false;
    }
    return true;
    }

    /**
     * Kiểm tra chuỗi đầu vào có chứa ký tự 2 byte trở lên hay không.
     * 
     * @param input
     *            Chuỗi cần kiểm tra.
     * @return true nếu chuỗi đầu vào có ký tự nhiều hơn 1 byte. <br />
     *         false trong các trường hợp còn lại.
     */
    public static boolean checkOneByte(String input) {
    int stringLength = input.length();
    int byteLength = input.getBytes().length;
    if (byteLength > stringLength) {
        return true;
    }
    return false;
    }

    /**
     * Phương thức kiểm tra chuỗi đầu vào có phải là chuỗi ký tự Katakana hay
     * không. (Chỉ chấp nhận các ký tự fullsize)
     * 
     * @param input
     *            Chuỗi cần kiểm tra.
     * @return true nếu chuỗi đầu vào là chuỗi ký tự Katakana.<br />
     *         false trong các trường hợp còn lại.
     */
    public static boolean checkKanaString(String input) {
    char[] charArray = input.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
        if (!isKanaChar(charArray[i])) {
        return false;
        }
    }
    return true;
    }

    /**
     * Phương thức kiểm tra xem 1 ký tự có phải là ký tự kana hay không.
     * 
     * @param input
     *            Ký tự cần kiểm tra.
     * @return true nếu ký tự là ký tự Katakana.<br />
     *         false trong trường hợp còn lại
     */
    public static boolean isKanaChar(char input) {
    int temp = (int) input;
    if ((temp >= 0x30A0 && temp <= 0x30FF)
        || (temp >= 0xFF65 && temp <= 0xFF9F)) {
        return true;
    }
    return false;
    }

    /**
     * Phương thức kiểm tra tính tồn tại của ngày nhập vào.
     * 
     * @param year
     *            Năm cần kiểm tra.
     * @param month
     *            Tháng cần kiểm tra.
     * @param day
     *            Ngày cần kiểm tra
     * 
     * @return true nếu ngày nhập vào thực sự tồn tại. <br />
     *         false trong các trường hợp còn lại.
     */
    public static boolean isRealDay(int year, int month, int day) {
    int yearNow = Common.getYearNow();
    if (year < 1900 || year > yearNow + 1) {
        return false;
    }
    switch (month) {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
        if (day < 1 || day > 31) {
        return false;
        }
        break;
    case 4:
    case 6:
    case 9:
    case 11:
        if (day < 1 || day > 30) {
        return false;
        }
        break;
    case 2:
        if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
        if (day < 1 || day > 29) {
            return false;
        }
        } else {
        if (day < 1 || day > 28) {
            return false;
        }
        }
        break;
    default:
        return false;
    }
    return true;
    }

    /**
     * Phương thức kiểm tra ngày hết hạn có nhỏ hơn ngày cấp hay không.
     * 
     * @param startDate
     *            Ngày cấp.
     * @param endDate
     *            Ngày hết hạn.
     * @return true nếu ngày cấp nhỏ hơn ngày hết hạn. <br />
     *         false trong trường hợp còn lại.
     */
    public static boolean compareDate(List<Integer> startDate,
        List<Integer> endDate) {
    LocalDate start = LocalDate.of(startDate.get(0), startDate.get(1),
        startDate.get(2));
    LocalDate end = LocalDate.of(endDate.get(0), endDate.get(1),
        endDate.get(2));
    int temp = start.compareTo(end);
    if (temp >= 0) {
        return false;
    } else {
        return true;
    }
    }

    /**
     * Phương thức kiểm tra tính đúng định dạng của chuỗi đầu vào theo một trong
     * các type:<br />
     * - Tên đăng nhập: type = loginName<br />
     * - Email: type = email<br />
     * - Số điện thoại: type = tel<br />
     * - Tổng điểm: type = total
     * 
     * @param type
     *            kiểu format kiểm tra.
     * @param input
     *            chuỗi cần kiểm tra.
     * @return true nếu type truyền vào tồn tại và chuỗi đầu vào đúng định dạng
     *         của type đã truyền vào.<br />
     *         false trong các trường hợp còn lại.
     */
    public static boolean checkFormat(String type, String input) {
    String regex = "";
    switch (type) {
    case "loginName":
        regex = "[a-zA-Z_]+[a-zA-Z0-9_]*";
        break;
    case "email":
        regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,5}$";
        break;
    case "tel":
        regex = "[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}";
        break;
    case "total":
        regex = "[0-9]{3}";
        break;
    default:
        return false;
    }
    return input.matches(regex);
    }

    /**
     * Phương thức kiểm tra một String có phải là số hay không.
     * 
     * @param str
     *            String cần kiểm tra.
     * @return true nếu chuỗi là một số.<br />
     *         false trong trường hợp còn lại.
     */
    public static boolean isStringNumbericPositive(String str) {
    if (str == null) {
        return false;
    }
    for (char c : str.toCharArray()) {
        if (!Character.isDigit(c)) {
        return false;
        }
    }
    return true;
    }

}
