package com.zk.framework.ui;

/**
 * -UI View层的init接口
 *
 * @author 张科
 * @date 2019/2/28.
 */
public interface UIInitCallBack {

    int getLayoutId();

    /**
     * 初始化intent
     */
    void initIntent();

    /**
     * 查找View
     */
    void findView();

    /**
     * 初始化对象
     */
    void initObject();

    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 展示内容View
     */
    void showContentView();

    /**
     * 展示错误界面
     */
    void showErrorLayout();

    /**
     * 展示无数据界面
     */
    void showEmptyLayout();

    /**
     * 展示加载界面
     */
    void showLoading();
}
