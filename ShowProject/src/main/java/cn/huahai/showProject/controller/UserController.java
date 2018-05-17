package cn.huahai.showProject.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.huahai.showProject.bean.ResponseResult;
import cn.huahai.showProject.bean.User;
import cn.huahai.showProject.service.IUserServer;


@Controller
@RequestMapping("/user")
public class UserController {
//	显示登录页
	@Resource
	private IUserServer userService;
	
//	显示登录页
	@RequestMapping("/showLogin.do")
	public String showLogin() {
		return "login";
	}
//	用户登录
	@RequestMapping("/login.do")
	@ResponseBody
	public ResponseResult<Void> login(
			HttpServletRequest req,
			String Uname,
			String password,
			String received){
		User user = new User(Uname,password);
		ResponseResult<Void> rr = new ResponseResult<Void>();
		try {
			userService.login(req,received,user);
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			rr.setMessage("登陆成功");
			rr.setState(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rr.setMessage(e.getMessage());
			rr.setState(0);
		}
		
		return rr;
	}
	/**
	 * 生成验证码
	 * @param req
	 * @param rsp
	 */
	@RequestMapping("/codeImg.do")
	public void imgCode(HttpServletRequest req, HttpServletResponse rsp) {
		try {
			userService.kaptcha(req,rsp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 判断验证码是否正确
	 * @param req
	 * @return
	 */
	@RequestMapping("/verify.do")
	@ResponseBody
	public ResponseResult<Void> verify(HttpServletRequest req,String received){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		if(userService.verify(req,received)) {
			rr.setState(1);
			rr.setMessage("验证码正确");
		}else {
			rr.setState(0);
			rr.setMessage("验证码错误");
		}
		return rr;
	}
	/**
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/exit.do")
	public String exit(HttpSession session) {
		// 清除session
		session.invalidate();
		// return "redirect:../main/showIndex.do";
		return "forward:/user/showLogin.do";
	}
}
