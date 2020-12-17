/**
 * Copyright(C) 2016
 * MstJapanDao.java, 13/12/2016 LeThanhPhu
 */
package manageuser.dao;

import java.util.List;

import manageuser.entities.MstJapan;

/**
 * Interface chứa các phương thức thao tác với bảng mst_japan
 * 
 * @author LeThanhPhu
 *
 */
public interface MstJapanDao {
    /**
     * Phương thức lấy tất cả cả các MstJapan trong DB.
     * 
     * @return Một List các MstJapan
     * 
     */
    public List<MstJapan> getAllMstGroup();
}
