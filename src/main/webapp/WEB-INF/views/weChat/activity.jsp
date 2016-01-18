<%--
  Created by IntelliJ IDEA.
  User: Hinsteny
  Date: 2016/1/14
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1> WeChat activity</h1>
<h2>wechatUser Openid: ${userInfo.openid}</h2>
<h2>wechatUser Name: ${userInfo.nickname}</h2>
<h2>wechatUser sex: ${userInfo.sex}</h2>
<h2>wechatUser language: ${userInfo.language}</h2>
<h2>wechatUser city: ${userInfo.city}</h2>
<h2>wechatUser province: ${userInfo.province}</h2>
<h2>wechatUser country: ${userInfo.country}</h2>
<h2>wechatUser headimgurl: </h2>
<div style="height: 200px; width: 200px;"><img src="${userInfo.headimgurl}" alt="微信用户图像"/></div>
<h2>wechatUser subscribe_time: ${userInfo.subscribe_time}</h2>
</body>
</html>
