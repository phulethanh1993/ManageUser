/**
 * Copyright(C) 2016
 * MstJapan.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.entities;

/**
 * Class mô phỏng bảng mst_japan
 * 
 * @author Le Thanh Phu
 *
 */
public class MstJapan {
    private String codeLevel;
    private String nameLevel;

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

}
