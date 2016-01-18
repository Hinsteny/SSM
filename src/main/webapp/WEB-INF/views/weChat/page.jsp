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
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<h1> WeChat page</h1>

<h2>验证微信jsdk的配置</h2>
</body>
<script type="text/javascript">
    // 服务端输出的数据
    var serverData = {
        barCode: '${barCode}',
        isSelf: ${isSelf},
        count: ${count}
    };

    // 微信配置
    wx.config({
        debug: false,
        appId: '${sign.appId}',
        timestamp: ''+'${sign.timestamp}', // 必填，生成签名的时间戳
        nonceStr: '${sign.nonceStr}', // 必填，生成签名的随机串
        signature: '${sign.signature}',// 必填，签名，见附录1
        jsApiList: ${sign.jsApiList} // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });

    wx.ready(function() {
        //分享到朋友圈 - 初始
        wx.onMenuShareTimeline({
            title: '${signData.title}', // 分享标题
            link: '${signData.link}', // 分享链接
            imgUrl: '${signData.imgUrl}', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                alert("分享朋友圈成功");
            },
            cancel: function () {}
        });

        //分享给自己的朋友 - 初始
        wx.onMenuShareAppMessage({
            title: '${signData.title}', // 分享标题
            link: '${signData.link}', // 分享链接
            imgUrl: '${signData.imgUrl}', // 分享图标
            success: function () {
                alert("分享朋友成功");
            },
            cancel: function () {}
        });
    });
</script>
</html>
