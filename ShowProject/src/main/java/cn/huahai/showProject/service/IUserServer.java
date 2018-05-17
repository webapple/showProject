package cn.huahai.showProject.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

import cn.huahai.showProject.bean.User;


public interface IUserServer {
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(HttpServletRequest request,String received,User user);
	/**
	 * 生成验证码
	 * @param req
	 * @param rsp
	 * @throws Exception
	 */
	public void kaptcha(HttpServletRequest req,HttpServletResponse rsp)throws Exception;
	/**
	 * 判断验证码是否正确 正确返回true 不正确返回false
	 * @param request
	 * @return
	 */
	public boolean verify(HttpServletRequest request,String received);
	/**
	 *  生成json配置文件
	  * 读取项目根目录下面的userInfo，返回Map<String, String>;
	 * @return 返回Map<String, String>;
	 * @throws IOException
	 * @throws JSONException
	 */
	public Map<String, String> getJson(HttpSession session) throws IOException, JSONException;
}
