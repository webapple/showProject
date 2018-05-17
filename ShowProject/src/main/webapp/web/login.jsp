<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8" />
	<title>华海产品管理后台登录</title>
	<meta name="author" content="DeathGhost" />
	<link rel="icon" href="${pageContext.request.contextPath}/web/img/huahai.jpg" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/style.css"
		tppabs="css/style.css" />
	<style>
	body {
		height: 100%;
		background: #16a085;
		overflow: hidden;
	}
	
	canvas {
		z-index: -1;
		position: absolute;
	}
	.J_codeimg{
		width: 100px;
	}
	#loginResult{
		color: red;
	}
	.ok{
		color: green!important;
	}
	.err{
		color: #000!important;
	}
	</style>
	<script src="http://www.jq22.com/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/web/js/verificationNumbers.js"
		tppabs="js/verificationNumbers.js"></script>
	<script src="${pageContext.request.contextPath}/web/js/Particleground.js" tppabs="${pageContext.request.contextPath}/web/js/Particleground.js"></script>
	<script>
		$(document).ready(function() {
			//粒子背景特效
			$('body').particleground({
				dotColor : '#5cbdaa',
				lineColor : '#5cbdaa'
			});
			/*点击图片更换验证码*/
			$("#mycode").on("click",function(){
				$(this).attr("src","${pageContext.request.contextPath}/user/codeImg.do?"+Math.random());
			});
			/*查看验证码是否正确  */
			$("#verify").on("input",function(){
				if($(this).val().length != 4){
					return
				}
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/user/verify.do",
					data:{
						received:$("#verify").val()
					},
					success:function(data){
						console.log(data);
						if(data.state){
							$("#showResult").val("验证码正确");
							$("#showResult").removeClass("err");
							$("#showResult").addClass("ok");
						}else{
							$("#showResult").val("验证码错误");
							$("#showResult").removeClass("ok");
							$("#showResult").addClass("err");
						}
					}
				});
			});
			/* 回车事件 */
			//$("#focus")为获取id为focus的元素
				$(".sub").keypress(function(event){
				    if(event.which === 13) { 
				        //点击回车要执行的事件
				        subFrom(event)
				     }
				});
			/*登陆方法  */
				/* 提交表单事件 */
				function subFrom(event){
					var event = event || window.event;
					  event.preventDefault(); // 兼容标准浏览器
					  event.returnValue = false; // 兼容IE6~8
					$.ajax({
						type:"post",
						url:"${pageContext.request.contextPath}/user/login.do",
						data:{
							Uname:$("#uname").val(),
							password:$("#pwd").val(),
							received:$("#verify").val()
						},
						success:function(data){
							console.log(data);
							if(data.state == 1){
								location = "${pageContext.request.contextPath}/main/showIndex.do";
							}else{
								$("#loginResult").html(data.message);
							}
						}
					});
				}
			$("#subBtn").on("click",function(event){
				subFrom(event);
			});
			
		});
	</script>
</head>
<body>
	<dl class="admin_login">
		<dt>
			<strong>华海产品管理系统</strong> <em>Management System</em>
		</dt>
		<dd class="user_icon">
			<input type="text" placeholder="请输入用户名" class="login_txtbx" id = "uname"/>
		</dd>
		<dd class="pwd_icon">
			<input type="password" placeholder="请输入密码" class="login_txtbx sub" id = "pwd"/>
		</dd>
		<dd class="val_icon">
			<div class="checkcode">
				<input type="text" id="verify" placeholder="验证码" maxlength="4"
					class="login_txtbx sub">
				<img class="J_codeimg" id="mycode" src="${pageContext.request.contextPath}/user/codeImg.do" ></img>
			</div>
			<input id="showResult"  type="button" value="验证码校验" class="ver_btn"/>
		</dd>
		<dd style="height:24px;">
			<p id="loginResult"></p>
		</dd>
		<dd>
			<input id="subBtn" type="button" value="立即登陆" class="submit_btn" />
		</dd>
		<dd>
			<p>© 2015-2018 华海信息科技有限公司  版权所有</p>
			<p>粤ICP备16081724号</p>
		</dd>
	</dl>
</body>
</html>
