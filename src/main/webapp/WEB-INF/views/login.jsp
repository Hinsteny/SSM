<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<?xml version="1.0" encoding="UTF-8" ?>
<head>
    <c:url var="bootstrapCss" value="/static/css/bootstrap/bootstrap.css"/>
    <link href="${bootstrapCss}" rel="stylesheet" />
    <c:url var="awesomeCss" value="/static/css/awesome/font-awesome.min.css"/>
    <link href="${awesomeCss}" rel="stylesheet" />
    <c:url var="app_theme" value="/static/css/app/theme.css"/>
    <link href="${app_theme}" rel="stylesheet" />
    <c:url var="app_login" value="/static/css/app/login.css"/>
    <link href="${app_login}" rel="stylesheet" />
    <c:url var="jqueryJs" value="/static/js/jquery/jquery-3.1.0.min.js"/>
    <script src="${jqueryJs}" ></script>
    <c:url var="bootstrapJs" value="/static/js/bootstrap/bootstrap.js"/>
    <script src="${bootstrapJs}" ></script>
    <script src="/res/test.js" ></script>
    <title tiles:fragment="title">Customer login Page : Welcome</title>
</head>
<body>
<div class="body">
    <div class="nav-container" style="min-height: 55px;">
        <nav class="white">
            <div class="nav-bar">
                <div class="module left">
                    <a class="img-home-url" href="index.html">
                        <img src="/static/img/favicon.ico" class="img-home-icon img-responsive center-block btn-danger-outline" />
                    </a>
                </div>
                <div class="module widget-handle mobile-toggle right visible-sm visible-xs">
                    <i class="icon-circle-blank icon-large pull-left"></i>
                </div>
                <div class="module-group right">
                    <div class="module left">
                        <ul class="menu">
                            <li class="no-dropdown">
                                <a href="index.html" alt="Home">
                                    <i class="icon-home"></i>Home
                                </a>
                            </li>
                            <li class="no-dropdown">
                                <a href="#">
                                    Study
                                </a>
                            </li>
                            <li class="no-dropdown">
                                <a href="#" alt="About Us">
                                    Life
                                </a>
                            </li>
                            <li class="no-dropdown">
                                <a href="http://blog.csdn.net/hinstenyhisoka" alt="FAQ">
                                    Blog
                                </a>
                            </li>
                            <li class="no-dropdown">
                                <a href="https://github.com/Hinsteny" target="blank">
                                    Github
                                </a>
                            </li>
                            <li class="no-dropdown">
                                <a href="#">
                                    Contact
                                </a>
                            </li>
                        </ul>
                    </div>
                    <!--end of menu module-->
                </div>
                <!--end of module group-->
            </div>
        </nav>
    </div>
    <div class="media-middle" >
        <div class="message-show">
            <h4><legend>Please Login</legend></h4>
            <div th:if="${param.logout}" class="alert alert-info">
                You have been logged out.
            </div>
            <div th:if="${param.error}" class="alert alert-danger">
                Invalid username and password.
            </div>
        </div>
        <div class="login-form">
            <form name="f" th:th:action="@{/login}" method="post">
                <fieldset>
                    <input class="form-control" type="text" id="username" name="username" placeholder="username"/>
                    <input class="form-control" type="password" id="password" name="password" placeholder="password"/>
                    <div class="form-actions">
                        <button type="submit" class="btn">Log in</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>