# spirngboot-console
Simple example

平台简介

springboot-console基于jeefast:https://gitee.com/theodo/jeefast 进行改造。平台内置 用户管理、部门管理、角色管理、菜单管理、日志管理、数据源监控、定时任务 等功能。

具有如下特点

友好的代码结构及注释，便于阅读及二次开发
实现前后端分离，通过token进行数据交互，前端再也不用关注后端技术
灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
完善的代码生成机制，可生成entity、xml、dao、service后台代码，减少70%以上的开发任务
使用quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复、运行日志查看功能
页面交互使用Vue2.x，极大的提高了开发效率
使用Hibernate Validator校验框架，轻松实现后端校验
使用很多优秀第三方工具：如http://feilong-core.mydoc.io/ 飞龙工具包。
整合分布式配置管理平台(Distributed Configuration Management Platform)：https://github.com/ihaolin/diablo 对Propertie属性文件统一管理。

技术选型：

核心框架：Spring Boot 1.5
安全框架：Apache Shiro 1.3
持久层框架：MyBatis 3.4.5、mybatis-plus 2.1.6
定时器：Quartz 2.3
数据库连接池：Druid 1.1.3
日志管理：SLF4J 1.7、Log4j
页面交互：Vue2.x
CSS框架：Twitter Bootstrap

本地部署

通过git下载源码
创建数据库jeefast，数据库编码为UTF-8
执行doc/db.sql文件，初始化数据
修改application-dev.yml，更新MySQL账号和密码
Eclipse运行reggie-console工程ConsoleApplication.java，则可启动项目
项目访问路径：http://localhost:8080/jeefast
账号密码：admin/123456

效果图

登录：
