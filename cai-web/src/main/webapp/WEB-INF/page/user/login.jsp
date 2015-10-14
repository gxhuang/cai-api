<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
		var left = ($(window).width() - $('#login').width()) / 2;
		var top = ($(window).height() - $('#login').height()) / 2-100;

		$('#login').css({'left':left+'px','top':top+'px'})

	});
</script>


</head>
<body>

	<div style="width: 400px; border: 5px solid rgba(0,0,0,0.2);position: absolute;" id="login">
		<div style="color: #ffaa00; background: #f8f8f8; padding: 10px 20px;">
			欢迎后台系统</div>

		<div style="background: white; padding: 10px 20px;">
			<form action="${pageContext.request.contextPath }/user/login" method="post">
				<div class="form-group">
					<label for="password">用户名</label> <input type="text" name="name"
						class="form-control" id="name" placeholder="手机号">
				</div>
				<div class="form-group">
					<label for="password">密码</label> <input type="password" name="password"
						class="form-control" id="password" placeholder="密码">
				</div>
				<button type="submit" class="btn btn-default">登录</button>
			</form>
		</div>
	</div>
</body>
</html>