package com.pgt.internal.controller;

/**
 * Message keys for internal user controller
 * Created by Yove on 12/2/2015.
 */
public interface InternalUserMessages {

	String ERROR_LOGIN_NOT_EXIST = "Error.internalUser.login.notExist";
	String ERROR_LOGIN_INVALID = "Error.internalUser.login.invalid";
	String ERROR_LOGIN_DUPLICATE = "Error.internalUser.login.duplicate";

	String ERROR_PASSWORD_NOT_MATCH = "Error.internalUser.password.notMatch";
	String ERROR_PASSWORD_INVALID = "Error.internalUser.password.invalid";
	String ERROR_PASSWORD_CONFIRM_INVALID = "Error.internalUser.passwordConfirm.invalid";

	String ERROR_NAME_DUPLICATE = "Error.internalUser.name.duplicate";

	String ERROR_EMAIL_INVALID = "Error.internalUser.email.invalid";
	String ERROR_EMAIL_DUPLICATE = "Error.internalUser.email.duplicate";

	String ERROR_PHONE_INVALID = "Error.internalUser.phone.invalid";
	String ERROR_PHONE_DUPLICATE = "Error.internalUser.phone.duplicate";

	String WARN_USER_STATUS_INVALID = "Warn.internalUser.user.invalid";

	String ERROR_GENERAL_LOGIN_FAILED = "Error.internalUser.general.loginFailed";
	String ERROR_GENERAL_REGISTER_FAILED = "Error.internalUser.general.registerFailed";
	String ERROR_GENERAL_UPDATE_BATCH_AVAILABLE_FAILED = "Error.internalUser.general.updateBatchAvailableFailed";
	String ERROR_GENERAL_UPDATE_USER_FAILED = "Error.internalUser.general.updateUserFailed";

	String WARN_USER_IDS_EMPTY = "Warn.internalUser.userIds.invalid";

	String ERROR_USER_ID_INVALID = "Error.internalUser.userId.invalid";
}
