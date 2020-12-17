/**
 * Copyright(C) 2016
 * Properties.java, 17/12/2016 LeThanhPhu
 */
package manageuser.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class thao tác với các file .properties
 * 
 * @author LeThanhPhu
 *
 */
public class Properties {
    /**
     * Phương thức lấy ra các config trong file config.properties
     * 
     * @param key
     *            Key tương ứng với giá trị config cần lấy ra.
     * @return Giá trị tương ứng với key đầu vào.<br />
     *         Null nếu key đầu vào không tồn tại trong file.
     */
    public static String getConfig(String key) {
        return getString("config", key, Locale.JAPANESE);
    }

    /**
     * Phương thức lấy ra các câu thông báo lỗi bằng tiếng Nhật với key tương
     * ứng trong file message_error_ja.properties
     * 
     * @param key
     *            Key tương ứng với giá trị muốn đọc ra.
     * @return Giá trị tương ứng với key đầu vào.<br />
     *         Null nếu key đầu vào không tồn tại trong file.
     */
    public static String getErrorMessageJapan(String key) {
        return getString("message_error", key, Locale.JAPANESE);
    }

    /**
     * Phương thức lấy ra các câu thông báo bằng tiếng Nhật với key tương ứng
     * trong file message_ja.properties
     * 
     * @param key
     *            Key tương ứng với giá trị muốn đọc ra.
     * @return Giá trị tương ứng với key đầu vào.<br />
     *         Null nếu key đầu vào không tồn tại trong file.
     */
    public static String getMessageJapan(String key) {
        return getString("message", key, Locale.JAPANESE);
    }

    /**
     * Phương thức lấy ra giá trị với key tương ứng.
     * 
     * @param fileName
     *            Tên file muốn đọc.
     * @param key
     *            Key tương ứng với giá trị muốn đọc ra.
     * @param locale
     *            Ngôn ngữ hoặc Quốc gia tương ứng với file properties (Ex: vi
     *            VN..) và hơn thế nữa. <br />
     * @see Locale
     * @return Giá trị tương ứng với key đầu vào.<br />
     *         Null nếu key đầu vào không tồn tại trong file.
     */
    private static String getString(String fileName, String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(fileName, locale);
        String isoValue = bundle.getString(key);
        if (key == null || !bundle.containsKey(key)) {
            return null;
        }
        String utf8Value = "";
        try {
            byte[] temp = isoValue.getBytes("ISO-8859-1");
            utf8Value = new String(temp, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return isoValue;
        }
        return utf8Value;
    }
}
