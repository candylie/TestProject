package com.zk.framework.util.permissions;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Looper;

import com.zk.framework.util.ObjectUtil;

/**
 * -权限处理工具
 *
 * @author 张科
 * @date 2018/12/6.
 */
public class PermissionsUtil {
    static int REQUEST_CODE = 129;

    private static PermissionsUtil instance = new PermissionsUtil();


    private static PermissionsCall callBack;


    public static PermissionsUtil create() {
        return instance;
    }

    public PermissionsUtil setCallBack(PermissionsCall callBack) {
        PermissionsUtil.callBack = callBack;
        return instance;
    }

    public void request(final Activity activity, final String[] permissionsArray) {
        if (ObjectUtil.isEmpty(permissionsArray) && callBack != null) {
            callBack.fail(permissionsArray);
            callBack = null;
            return;
        }

        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PermissionFragment permissionFragment = getPermissionFragment(activity);
                    permissionFragment.requestPermissions(REQUEST_CODE, permissionsArray, PermissionsUtil.callBack);
                }
            });
        } else {
            PermissionFragment permissionFragment = getPermissionFragment(activity);
            permissionFragment.requestPermissions(REQUEST_CODE, permissionsArray, PermissionsUtil.callBack);
        }

    }

    private PermissionFragment getPermissionFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        PermissionFragment permissionFragment = (PermissionFragment) fragmentManager.findFragmentByTag("TKPermission");
        if (permissionFragment == null) {
            permissionFragment = new PermissionFragment();
            fragmentManager.beginTransaction().add(permissionFragment, "TKPermission").commit();
            fragmentManager.executePendingTransactions();
        }
        return permissionFragment;
    }

}
