# SSM （Spring+SpringMVC+Mybatis）
Java web应用开发的基础框架, 数据库配置的Postgres Sql.

## 作者
* Hinsteny [Home](https://github.com/Hinsteny)

### 项目介绍
项目本身采用Maven管理, jdk1.8, 配置基础的SSM框架使用方法.

### 包含技术
*  Spring, Springmvc, mybatis,
*  postgres, mongodb 
*  redis, 
*  websocket, 

### 配置使用
* git clone https://github.com/Hinsteny/SSM.git
* 为编辑器(IDEA)添加Groovy编译支持(项目中掺杂了一些groovy代码, 如果导入项目到IDEA后依旧提示编译不过, 可以通过在项目名上右击选择添加框架支持, 里面找一下groovy).
* 配置数据库（修改: resources/config/DBPool.rpoperties 里面对应的数据库信息）
* 发布项目, 运行, 访问(https://127.0.0.1:80) 查看效果

...

### 第一期 基础框架组合与配置

功能点: 
* 基于SpringMVC 搭建了普通的web开发环境
* 采用SoringSecurity做的用户访问认证与授权;
* 使用了spring-websocket实现了客户端长连接接受服务器发布的订阅消息, [使用tomcat8部署的, 需要支持异步请求及Servlet3环境]
* 缓存用redis哨兵模式, Redis集群, [搭建教程](http://blog.csdn.net/hinstenyhisoka/article/details/53955592);
* Mybatis 做持久化对象存储映射;
* 默认使用PG数据库, 并用HikariCP建立数据库连接池;


#### I will back soon after...