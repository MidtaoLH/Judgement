/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.example.fv.judgement.app.manager;

import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.WayCheckModel;

/**数据工具类
 * @author Lemon
 */
public class DataManager {
	private final String TAG = "DataManager";

	private Context context;
	private DataManager(Context context) {
		this.context = context;
	}

	private static DataManager instance;
	public static DataManager getInstance() {
		if (instance == null) {
			synchronized (DataManager.class) {
				if (instance == null) {
					instance = new DataManager(GlobalInformationApplication.getInstance());
				}
			}
		}
		return instance;
	}

	//用户 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private String PATH_USER = "PATH_USER";
	private String PATH_WAY = "PATH_WAY";

	public final String KEY_USER = "KEY_USER";
	public final String KEY_USER_ID = "KEY_USER_ID";
	public final String KEY_USER_NAME = "KEY_USER_NAME";
	public final String KEY_USER_PHONE = "KEY_USER_PHONE";

	public final String KEY_CURRENT_USER_ID = "KEY_CURRENT_USER_ID";
	public final String KEY_CURRENT_WAY_ID = "KEY_CURRENT_WAY_ID";

	public final String KEY_LAST_USER_ID = "KEY_LAST_USER_ID";
	public final String KEY_LAST_WAY_ID = "KEY_LAST_WAY_ID";

	/**判断是否为当前用户
	 * @param context
	 * @param userId
	 * @return
	 */
	public boolean isCurrentUser(String userId) {
		return userId!= "0" && userId == getCurrentUserId();
	}

	/**获取当前用户id
	 * @param context
	 * @return
	 */
	public String getCurrentUserId() {
		LoginUserModel user = getCurrentUser();
		return user == null ? "0" : user.getEmpID();
	}

	public String getCurrentWayId() {
		WayCheckModel Way = getCurrentWay();
		return Way == null ? "0" : Way.getIndex().toString();
	}

	/**获取当前用户
	 * @param context
	 * @return
	 */
	public LoginUserModel getCurrentUser() {
		SharedPreferences sdf = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE);
		return sdf == null ? null : getUser(sdf.getString(KEY_CURRENT_USER_ID, "0"));
	}

	public WayCheckModel getCurrentWay() {
		SharedPreferences sdf = context.getSharedPreferences(PATH_WAY, Context.MODE_PRIVATE);
		return sdf == null ? null : getWay(sdf.getString(KEY_CURRENT_WAY_ID, "0"));
	}


	/**获取用户
	 * @param context
	 * @param userId
	 * @return
	 */
	public LoginUserModel getUser(String userId) {
		SharedPreferences sdf = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE);
		if (sdf == null) {
			Log.e(TAG, "get sdf == null >>  return;");
			return null;
		}
		Log.i(TAG, "getUser  userId = " + userId);
		return JSON.parseObject(sdf.getString(StringUtil.getTrimedString(userId), null), LoginUserModel.class);
	}

	public WayCheckModel getWay(String userId) {
		SharedPreferences sdf = context.getSharedPreferences(PATH_WAY, Context.MODE_PRIVATE);
		if (sdf == null) {
			Log.e(TAG, "get sdf == null >>  return;");
			return null;
		}
		Log.i(TAG, "getUser  userId = " + userId);
		return JSON.parseObject(sdf.getString(StringUtil.getTrimedString(userId), null), WayCheckModel.class);
	}


	/**保存当前用户,只在登录或注销时调用
	 * @param context
	 * @param user  user == null >> user = new User();
	 */
	public void saveCurrentUser(LoginUserModel user) {
		SharedPreferences sdf = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE);
		if (sdf == null) {
			Log.e(TAG, "saveUser sdf == null  >> return;");
			return;
		}
		if (user == null) {
			Log.w(TAG, "saveUser  user == null >>  user = new User();");
			user = new LoginUserModel();
		}
		SharedPreferences.Editor editor = sdf.edit();
		editor.remove(KEY_LAST_USER_ID).putString(KEY_LAST_USER_ID, getCurrentUserId());
		editor.remove(KEY_CURRENT_USER_ID).putString(KEY_CURRENT_USER_ID, user.getEmpID());
		editor.commit();
		saveUser(sdf, user);
	}

	public void saveCurrentWay(WayCheckModel Way) {
		SharedPreferences sdf = context.getSharedPreferences(PATH_WAY, Context.MODE_PRIVATE);
		if (sdf == null) {
			Log.e(TAG, "saveUser sdf == null  >> return;");
			return;
		}
		if (Way == null) {
			Log.w(TAG, "saveUser  user == null >>  user = new User();");
			Way = new WayCheckModel();
		}
		SharedPreferences.Editor editor = sdf.edit();
		editor.remove(KEY_LAST_WAY_ID).putString(KEY_LAST_WAY_ID, getCurrentWayId());
		editor.remove(KEY_CURRENT_WAY_ID).putString(KEY_CURRENT_WAY_ID, Way.getIndex());
		editor.commit();
		saveWay(sdf, Way);
	}

	/**保存用户
	 * @param context
	 * @param user
	 */
	public void saveUser(LoginUserModel user) {
		saveUser(context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE), user);
	}

	public void saveWay(WayCheckModel Way) {
		saveWay(context.getSharedPreferences(PATH_WAY, Context.MODE_PRIVATE), Way);
	}

	/**保存用户
	 * @param context
	 * @param sdf
	 * @param user
	 */
	public void saveUser(SharedPreferences sdf, LoginUserModel user) {
		if (sdf == null || user == null) {
			Log.e(TAG, "saveUser sdf == null || user == null >> return;");
			return;
		}
		String key = StringUtil.getTrimedString(user.getId());
		Log.i(TAG, "saveUser  key = user.getId() = " + user.getId());
		sdf.edit().remove(key).putString(key, JSON.toJSONString(user)).commit();
	}

	public void saveWay(SharedPreferences sdf, WayCheckModel Way) {
		if (sdf == null || Way == null) {
			Log.e(TAG, "saveUser sdf == null || Way == null >> return;");
			return;
		}
		String key = StringUtil.getTrimedString(Way.getId());
		Log.i(TAG, "saveUser  key = user.getId() = " + Way.getId());
		sdf.edit().remove(key).putString(key, JSON.toJSONString(Way)).commit();
	}


	/**删除用户
	 * @param context
	 * @param sdf
	 */
	public void removeUser(SharedPreferences sdf, long userId) {
		if (sdf == null) {
			Log.e(TAG, "removeUser sdf == null  >> return;");
			return;
		}
		sdf.edit().remove(StringUtil.getTrimedString(userId)).commit();
	}


	/**设置当前用户姓名
	 * @param context
	 * @param name
	 */
	public void setCurrentUserName(String name) {
		LoginUserModel user = getCurrentUser();
		if (user == null) {
			user = new LoginUserModel();
		}
		user.setName(name);
		saveUser(user);
	}

	//用户 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
