<!DOCTYPE html>
<html lang="en">
<head>
    <#assign ctx=request.contextPath/>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
这是主页
<form action="/login" method="post">
    用户名：<input type="text" name="username" value="chenqiang"/>
    密码：<input type="password" name="password" value="123456"/>
    <input type="submit" value="提交"/>
</form>
上下文路径:${ctx}
<a href="../html/test/test1.html">这是websocket测试页</a>
</body>
</html>