package com.application.config;

import java.util.ArrayList;
import java.util.List;

import com.application.model.Permission;

public class UrlMapping {

	public static final String CONTROLLER_DEFAULT = "/";

	public static final String CONTROLLER_AUTH = "/auth";
	public static final String CONTROLLER_AUTH_LOGIN = "/login";
	public static final String CONTROLLER_AUTH_SIGN_IN = "/signIn";
	public static final String CONTROLLER_AUTH_SIGN_OUT = "/signOut";
	public static final String CONTROLLER_AUTH_UNAUTHORIZED = "/unauthorized";

	public static final String CONTROLLER_HOME = "/home";
	public static final String CONTROLLER_HOME_INDEX = "/index";

	public static final String CONTROLLER_USER = "/user";
	public static final String CONTROLLER_USER_LIST = "/list";
	public static final String CONTROLLER_USER_SHOW = "/show/{id}";
	public static final String CONTROLLER_USER_EDIT = "/edit/{id}";
	public static final String CONTROLLER_USER_UPDATE = "/update";
	public static final String CONTROLLER_USER_DELETE = "/delete/{id}";
	public static final String CONTROLLER_USER_PROFILE = "/profile";
	public static final String CONTROLLER_USER_UPDATE_PROFILE = "/updateProfile";
	public static final String CONTROLLER_USER_ACTIVE = "/active";
	public static final String CONTROLLER_USER_EXPIRE_SESSION = "/expireSession/{id}";

	public static String generateUrl(String... str) {
		if (str.length == 0) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < str.length; i++) {
				sb.append(str[i]);
			}
			return (sb != null ? sb.toString() : null);
		}

	}

	public static String getLoginUrl() {
		return new StringBuilder(CONTROLLER_AUTH).append(CONTROLLER_AUTH_LOGIN).toString();
	}

	public static String getSignInUrl() {
		return new StringBuilder(CONTROLLER_AUTH).append(CONTROLLER_AUTH_SIGN_IN).toString();
	}

	public static String getLogOutUrl() {
		return new StringBuilder(CONTROLLER_AUTH).append(CONTROLLER_AUTH_SIGN_OUT).toString();
	}

	public static String getUnauthorizedUrl() {
		return new StringBuilder(CONTROLLER_AUTH).append(CONTROLLER_AUTH_UNAUTHORIZED).toString();
	}

	public static List<String> byPassUrlList() {
		List<String> urlList = new ArrayList<>();
		urlList.add(generateUrl(UrlMapping.generateUrl(CONTROLLER_AUTH, CONTROLLER_AUTH_LOGIN)));
		urlList.add(generateUrl(UrlMapping.generateUrl(CONTROLLER_AUTH, CONTROLLER_AUTH_SIGN_IN)));
		return urlList;
	}

	public static final String PERMISSION_USER_LIST = "USER_LIST";
	public static final String PERMISSION_USER_SHOW = "USER_SHOW";
	public static final String PERMISSION_USER_EDIT = "USER_EDIT";
	public static final String PERMISSION_USER_UPDATE = "USER_UPDATE";
	public static final String PERMISSION_USER_DELETE = "USER_DELETE";
	public static final String PERMISSION_USER_PROFILE = "USER_PROFILE";
	public static final String PERMISSION_USER_UPDATE_PROFILE = "USER_UPDATEPROFILE";
	public static final String PERMISSION_USER_ACTIVE = "USER_ACTIVE";
	public static final String PERMISSION_USER_EXPIRE_SESSION = "USER_EXPIRESESSION";

	public static List<Permission> getPermissionListForAdmin() {

		List<Permission> permissionList = new ArrayList<>();

		Permission userList = new Permission();
		userList.setName(PERMISSION_USER_LIST);
		permissionList.add(userList);

		Permission userShow = new Permission();
		userShow.setName(PERMISSION_USER_SHOW);
		permissionList.add(userShow);

		Permission userEdit = new Permission();
		userEdit.setName(PERMISSION_USER_EDIT);
		permissionList.add(userEdit);

		Permission userUpdate = new Permission();
		userUpdate.setName(PERMISSION_USER_UPDATE);
		permissionList.add(userUpdate);

		Permission userDelete = new Permission();
		userDelete.setName(PERMISSION_USER_DELETE);
		permissionList.add(userDelete);

		Permission userProfile = new Permission();
		userProfile.setName(PERMISSION_USER_PROFILE);
		permissionList.add(userProfile);

		Permission userUpdateProfile = new Permission();
		userUpdateProfile.setName(PERMISSION_USER_UPDATE_PROFILE);
		permissionList.add(userUpdateProfile);

		Permission userActiveList = new Permission();
		userActiveList.setName(PERMISSION_USER_ACTIVE);
		permissionList.add(userActiveList);

		Permission userExpireSession = new Permission();
		userExpireSession.setName(PERMISSION_USER_EXPIRE_SESSION);
		permissionList.add(userExpireSession);

		return permissionList;
	}

	public static List<Permission> getPermissionListForAdviser() {

		List<Permission> permissionList = new ArrayList<>();

		Permission userList = new Permission();
		userList.setName(PERMISSION_USER_LIST);
		permissionList.add(userList);

		Permission userShow = new Permission();
		userShow.setName(PERMISSION_USER_SHOW);
		permissionList.add(userShow);

		Permission userProfile = new Permission();
		userProfile.setName(PERMISSION_USER_PROFILE);
		permissionList.add(userProfile);

		Permission userUpdateProfile = new Permission();
		userUpdateProfile.setName(PERMISSION_USER_UPDATE_PROFILE);
		permissionList.add(userUpdateProfile);

		return permissionList;
	}

	public static List<Permission> getPermissionListForUser() {

		List<Permission> permissionList = new ArrayList<>();

		Permission userProfile = new Permission();
		userProfile.setName(PERMISSION_USER_PROFILE);
		permissionList.add(userProfile);

		Permission userUpdateProfile = new Permission();
		userUpdateProfile.setName(PERMISSION_USER_UPDATE_PROFILE);
		permissionList.add(userUpdateProfile);

		return permissionList;
	}

}
