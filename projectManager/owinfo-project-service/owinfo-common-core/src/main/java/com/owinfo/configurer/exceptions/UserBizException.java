package com.owinfo.configurer.exceptions;



public class UserBizException extends BizException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/***
	 * 验证用户是否合法参数为空
	 */
	public static final int LOGIN_LOGNAME_NOT_EXIST = 20020005;
	/***
	 * 非法用户或用户不存在
	 */
	public static final int LOGIN_ENTITY_NOT_EXIST = 20020006;
	/***
	 * 登录：密码错误
	 */
	public static final int LOGIN_PWD_ERROR = 20020002;
	/***
	 * 用户不存在
	 */
	public static final int USERINFO_NOT_IS_EXIST = 20020007;

	public UserBizException() {
	}

	public UserBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public UserBizException(int code, String msg) {
		super(code, msg);
	}

}

