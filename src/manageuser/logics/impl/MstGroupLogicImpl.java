/**
 * Copyright(C) 2016
 * MstGroupLogicImpl.java, 22/12/2016 Le Thanh Phu
 */
package manageuser.logics.impl;

import java.util.ArrayList;
import java.util.List;

import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.logics.MstGroupLogic;

/**
 * Class thực thi Interface {@link MstGroupLogic}
 * 
 * @author Le Thanh Phu
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic {
    private MstGroupDaoImpl groupDaoImpl = new MstGroupDaoImpl();

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.MstGroupLogic#getAllGroup()
     */
    @Override
    public List<MstGroup> getAllGroup() {
        List<MstGroup> allGroup = new ArrayList<>();
        allGroup = groupDaoImpl.getAllMstGroup();
        return allGroup;
    }

}
