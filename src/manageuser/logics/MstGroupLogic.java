/**
 * Copyright(C) 2016
 * MstGroupLogic.java, 22/12/2016 Le Thanh Phu
 */
package manageuser.logics;

import java.util.List;

import manageuser.entities.MstGroup;

/**
 * Interface chứa các phương thức xử lý logic với bảng mst_group
 * 
 * @author Le Thanh Phu
 *
 */
public interface MstGroupLogic {
    /**
     * Phương thức lấy tất cả các group trong Database.
     * 
     * @return Một List các MstGroup.
     */
    public List<MstGroup> getAllGroup();
}
