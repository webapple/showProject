package cn.huahai.showProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
	@RequestMapping("/showIndex.do")
	public String showLogin() {
		return "index";
	}
}
