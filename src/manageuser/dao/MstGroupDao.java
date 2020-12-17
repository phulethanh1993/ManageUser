/**
 * Copyright(C) 2016
 * MstGroupDao.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.dao;

import java.util.List;

import manageuser.entities.MstGroup;

/**
 * Interface chứa các phương thức thao tác với bảng mst_group
 * 
 * @author Le Thanh Phu
 *
 */
public interface MstGroupDao {
    /**
     * Phương thức lấy tất cả cả các group trong DB.
     * 
     * @return Một List các Mstgroup
     */
    public List<MstGroup> getAllMstGroup();
}
