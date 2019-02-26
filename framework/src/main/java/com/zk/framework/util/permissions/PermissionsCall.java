package com.zk.framework.util.permissions;

/**
 * -
 *
 * @author 张科
 * @date 2018/12/6.
 */
public interface PermissionsCall {

    /**
     * 成功
     */
    void granted();


    /**
     * 拒绝
     *
     * @param permissionsArray -
     */
    void fail(String[] permissionsArray);

    /**
     * 提示用户
     *
     * @param permissionsArray -
     */
    void needShowRationale(String[] permissionsArray);
}
