package com.application.enums;

public enum UserType {
	ADMIN("Admin"), USER("User"), ADVISER("Adviser");

	private String code;

	UserType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static UserType fromCode(String userType) {
		for (UserType uType : UserType.values()) {
			if (uType.getCode().equals(userType)) {
				return uType;
			}
		}
		throw new UnsupportedOperationException("The code " + userType + " is not supported!");
	}
}
