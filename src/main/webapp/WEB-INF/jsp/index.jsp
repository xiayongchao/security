<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://security.xyc.com/mytag" prefix="mt" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
<mt:security module="user" permission="query">
    你有权限进行用户查询
</mt:security>
</body>
</html>
