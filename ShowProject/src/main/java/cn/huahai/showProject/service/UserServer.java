package cn.huahai.showProject.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import cn.huahai.showProject.bean.User;
import cn.huahai.showProject.server.ex.PasswordNotMatchException;
import cn.huahai.showProject.server.ex.VerifyNotPass;


@Service
public class UserServer implements IUserServer {
	
    @Autowired
//  生成验证码所需要的注入
    private Producer producer;
    
	public User login(HttpServletRequest request,String received,User user) {
		System.out.println(user);
		System.out.println(received);
		try {
			if(user.getPassword().equals(getJson(request.getSession()).get(user.getUname()))) {
				if(!verify(request,received)) {
					throw new VerifyNotPass("验证码不正确");
				}
				return user;
			}
		} catch (IOException e) {
			// 文件读取异常
			e.printStackTrace();
		} catch (JSONException e) {
			// 可是json的书写格式有问题
			e.printStackTrace();
		} 
		throw new PasswordNotMatchException("用户名或密码不正确");
	}
	/*获取输出流生成验证码
	 * (non-Javadoc)
	 * @see cn.huahai.tel.service.IUserServer#kaptcha(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void kaptcha(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		 HttpSession session = req.getSession();
	        rsp.setDateHeader("Expires", 0);
	        rsp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	        rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        rsp.setHeader("Pragma", "no-cache");
	        rsp.setContentType("image/jpeg");

	        String capText = producer.createText();
	        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
	        System.out.println("验证码: " + capText);
	        BufferedImage image = producer.createImage(capText);
	        ServletOutputStream out = rsp.getOutputStream();
	        ImageIO.write(image, "jpg", out);
	        try {
	            out.flush();
	        } finally {
	            out.close();
	        }
	}
	/**
	 * 验证 验证码是否正确 正确返回true 不正确返回false
	 * @param request
	 * @return
	 */
	 public boolean verify(HttpServletRequest request,String received) {
		 
        //从session中取出servlet生成的验证码text值
        String expected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取用户页面输入的验证码
        //不分大小写
        return received != null && received.equalsIgnoreCase(expected);
	 }
	/**
	 *  生成json配置文件
	  * 读取项目根目录下面的userInfo，返回Map<String, String>;
	 */
	 public Map<String, String> getJson(HttpSession session) throws IOException, JSONException {
    //	File file=new File("resource/userInfo.json");
			//获取服务器的真实路径
			String path = session.getServletContext().getRealPath("/");
		 	File file=new File(path+"\\WEB-INF\\classes\\userInfo.json");
	        String content= FileUtils.readFileToString(file,"UTF-8");
	        JSONObject jsonObject=new JSONObject(content);
	        JSONArray array = jsonObject.getJSONArray("userInfo");
	        Map<String, String> userMap = new HashMap<String, String>();
	        for(int i = 0;i<array.length();i++) {
	        	JSONObject item = (JSONObject)array.get(i);
	        	userMap.put((String)item.get("user"), (String)item.get("pwd"));
	        }
	        return userMap;
	 }
}
