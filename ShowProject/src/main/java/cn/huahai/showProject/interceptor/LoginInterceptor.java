package cn.huahai.showProject.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginInterceptor implements HandlerInterceptor{
//这控制器执行之前
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.判断session，user是否null
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if(user == null) {
			String url = request.getContextPath()+"/user/showLogin.do";
			response.sendRedirect(url);
			return false;
		}else {
			return true;
		}
	}
//响应完视图之后
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
//控制器处理了，视图处理之前
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
