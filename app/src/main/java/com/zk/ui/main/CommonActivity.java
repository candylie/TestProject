package com.zk.ui.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.framework.ui.ZBaseActivity;
import com.zk.mytest.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * -通用的Activity界面,用来装载Fragment的
 *
 * @author 张科
 * @date 2019/2/27.
 */
public class CommonActivity extends ZBaseActivity {
    public static final String FRAGMENT_PATH = "fragment";
    public static final String IS_SHOW_TITLE = "isShowTitle";
    public static final String TITLE = "Title";
    public static final String SUB_TITLE = "subTitle";


    @BindView(R.id.common_activity_back_iv)
    ImageView mBackIv;

    @BindView(R.id.common_activity_title_tv)
    TextView mTitleTv;

    @BindView(R.id.common_activity_subTitle_tv)
    TextView mSubTitleTv;

    @BindView(R.id.common_activity_right_tv)
    TextView mRightTv;

    @BindView(R.id.common_activity_right_iv)
    ImageView mRightIv;


    private String mFragmentPath;
    private boolean isShowTitle;
    private String mTitle;
    private String mSubTitle;

    /**
     * 加载的fragment
     */
    private Fragment mFragment;

    @Override
    public int getLayoutId() {
        return R.layout.layout_common_activity;
    }

    @NotNull
    @Override
    public String getActivityName() {
        return "通用Activity";
    }

    @Override
    public void initIntent() {
        Intent intent = getIntent();
        isShowTitle = intent.getBooleanExtra(IS_SHOW_TITLE, true);
        mTitle = intent.getStringExtra(TITLE) + "";
        mSubTitle = intent.getStringExtra(SUB_TITLE) + "";
        mFragmentPath = intent.getStringExtra(FRAGMENT_PATH) + "";
    }

    @Override
    public void findView() {

    }

    @Override
    public void initObject() {
        if (!TextUtils.isEmpty(mFragmentPath)) {
            try {
                Class clz = Class.forName(mFragmentPath);
                mFragment = (Fragment) clz.newInstance();
                mFragment.setArguments(getIntent().getExtras());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initView() {
        /*
         * 加载传进来的fragment
         */
        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.common_activity_content_fl, mFragment).commit();
        } else {
            showEmptyLayout();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.common_activity_back_iv)
    @Override
    public void finish() {
        super.finish();
    }
}
