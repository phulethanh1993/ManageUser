/**
 * Copyright(C) 2016
 * MstJapanLogicImpl.java, 13/12/2016 LeThanhPhu
 */
package manageuser.logics.impl;

import java.util.ArrayList;
import java.util.List;

import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapan;
import manageuser.logics.MstJapanLogic;

/**
 * Class thực thi Interface {@link MstJapanLogic}
 * 
 * @author LeThanhPhu
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {
    private MstJapanDaoImpl mstJapanDao = new MstJapanDaoImpl();

    /*
     * (non-Javadoc)
     * 
     * @see manageuser.logics.MstJapanLogic#getAllMstJapan()
     */
    @Override
    public List<MstJapan> getAllMstJapan() {

        List<MstJapan> allMstJapan = new ArrayList<>();
        allMstJapan = mstJapanDao.getAllMstGroup();
        return allMstJapan;
    }

}
