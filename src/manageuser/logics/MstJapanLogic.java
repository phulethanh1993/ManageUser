/**
 * Copyright(C) 2016
 * MstJapanLogic.java, 13/12/2016 LeThanhPhu
 */
package manageuser.logics;

import java.util.List;

import manageuser.entities.MstJapan;

/**
 * Interface chứa các phương thức xử lý logic với bảng mst_japan
 * 
 * @author LeThanhPhu
 *
 */
public interface MstJapanLogic {
    /**
     * Phương thức lấy tất cả các MstJapan trong Database.
     * 
     * @return Một List các MstJapan.
     */
    public List<MstJapan> getAllMstJapan();

}
