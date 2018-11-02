<!DOCTYPE html>
<html lang="en">
<head>
    <#assign ctx=request.contextPath/>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
这是主页
<form action="/login">
    用户名：<input type="text" name="username" value="chenqiang"/>
    密码：<input type="password" name="password" value="123456"/>
    <input type="submit" value="提交"/>
</form>
上下文路径:${ctx}
</body>
</html>