package com.zk.framework.util.permissions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import com.zk.framework.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * -由于要吧授权结果返回给activity的回调，所以需要监听到这个回调
 * Fragment里面可以去获取权限和接收这回调，然后通过这个系统的回调，返回到PermissionsCall中
 *
 * @author 张科
 * @date 2018/12/6.
 */
public class PermissionFragment extends Fragment {
    private Activity mActivity;
    private PermissionsCall mPermissionCallBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected void requestPermissions(final int requestCode, @NonNull final String[] permissions, PermissionsCall callBack) {
        mPermissionCallBack = callBack;
        if (checkAPI()) {
            //获取权限
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkGranted(mActivity, permissions, requestCode);
                    }
                });
            } else {
                checkGranted(mActivity, permissions, requestCode);
            }
        } else {
            if (callBack != null) {
                callBack.granted();
            }
        }

    }

    private boolean checkAPI() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 检测权限
     *
     * @param permission -
     * @param context    -
     * @return -
     */
    private boolean checkGranted(String permission, Context context) {
        if (checkAPI()) {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return PermissionChecker.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
    }

    /**
     * 请求权限
     *
     * @param context     -
     * @param requestCode -
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkGranted(Activity context, String[] permissions, int requestCode) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String s : permissions) {
            if (!checkGranted(s, context)) {
                deniedPermissions.add(s);
            }
        }
        if (ObjectUtil.isEmpty(deniedPermissions)) {
            if (null != mPermissionCallBack) {
                mPermissionCallBack.granted();
            }
        } else {
            //请求权限
            requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限监听的系统回调
        if (requestCode == PermissionsUtil.REQUEST_CODE && grantResults.length > 0 && this.mPermissionCallBack != null) {
            this.onRequestPermissionsResult(permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onRequestPermissionsResult(String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        List<String> needShowRationalePermission = new ArrayList<>();

        for (int i = 0; i < permissions.length; ++i) {
            if (grantResults[i] != 0) {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (deniedPermissions.isEmpty()) {
            this.mPermissionCallBack.granted();
        } else {
            for (String deniedPermission : deniedPermissions) {
                if (!this.shouldShowRequestPermissionRationale(deniedPermission)) {
                    needShowRationalePermission.add(deniedPermission);
                }
            }

            if (!needShowRationalePermission.isEmpty()) {
                this.mPermissionCallBack.needShowRationale(needShowRationalePermission.toArray(new String[needShowRationalePermission.size()]));
            }

            this.mPermissionCallBack.fail(deniedPermissions.toArray(new String[deniedPermissions.size()]));
        }

    }

}
