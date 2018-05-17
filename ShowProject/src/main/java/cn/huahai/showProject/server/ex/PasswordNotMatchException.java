package cn.huahai.showProject.server.ex;

public class PasswordNotMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8903428607881147665L;

	public PasswordNotMatchException(String message) {
		super(message);
	}

}
