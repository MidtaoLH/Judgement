package com.example.fv.judgement.app.application;

import com.example.fv.judgement.app.manager.DataManager;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.WayCheckModel;

import zuo.biao.library.base.BaseApplication;

/**
 * Created by lh on 2020/2/18.
 */
//全局信息
public class GlobalInformationApplication  extends BaseApplication {
    private static final String TAG = "GlobalInformationApplication";

    private static GlobalInformationApplication context;
    public static GlobalInformationApplication getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    /**获取当前用户id
     * @return
     */
    public String getCurrentUserId() {
        currentUser = getCurrentUser();
        return currentUser.getEmpID();
    }
    private static LoginUserModel currentUser = null;
    private static WayCheckModel currentWay = null;

    public LoginUserModel getCurrentUser() {
        if (currentUser == null) {
            currentUser = DataManager.getInstance().getCurrentUser();
        }
        return currentUser;
    }

    public WayCheckModel getCurrentWay() {
        if (currentWay == null) {
            currentWay = DataManager.getInstance().getCurrentWay();
        }
        return currentWay;
    }

    public void saveCurrentUser(LoginUserModel user) {
        if (user == null) {
            return;
        }
        if (user.getEmpID()== "0" ) {
            return;
        }
        currentUser = user;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    public void saveCurrentWay(WayCheckModel Way) {
        if (Way == null) {
            return;
        }
        if (Way.getIndex()== "0" ) {
            return;
        }
        currentWay = Way;
        DataManager.getInstance().saveCurrentWay(currentWay);
    }

    public void logout() {
        currentUser = null;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    /**判断是否为当前用户
     * @param userId
     * @return
     */
    public boolean isCurrentUser(String userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return getCurrentUserId()!="0";
    }
}