/**
 * Copyright(C) 2016
 * MstGroup.java, 18/12/2016 Le Thanh Phu
 */
package manageuser.entities;

/**
 * Class mô phỏng bảng mst_group
 * 
 * @author Le Thanh Phu
 *
 */
public class MstGroup {
    private int groupId;
    private String groupName;

    public MstGroup() {
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(int groupId) {
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
}
