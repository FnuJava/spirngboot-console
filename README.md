## 平台简介
JeeFast快速开发平台是基于多个优秀的开源项目，将各个亮点设计融于一体的快速开发平台。平台内置 用户管理、部门管理、角色管理、菜单管理、日志管理、数据源监控、定时任务 等功能。

## 具有如下特点
- 友好的代码结构及注释，便于阅读及二次开发
- 实现前后端分离，通过token进行数据交互，前端再也不用关注后端技术
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 完善的代码生成机制，可生成entity、xml、dao、service后台代码，减少70%以上的开发任务
- 使用quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复、运行日志查看功能
- 页面交互使用Vue2.x，极大的提高了开发效率
- 使用Hibernate Validator校验框架，轻松实现后端校验
- 使用swagger2支持，方便使用API接口文档

## 如何交流、反馈、参与贡献？
- Git仓库：https://gitee.com/theodo/jeefast
- QQ交流群：583154815
- 如需关注项目最新动态，请Watch、Star项目

## 技术选型：
- 核心框架：Spring Boot 1.5
- 安全框架：Apache Shiro 1.3
- 持久层框架：MyBatis 3.4.5、mybatis-plus 2.1.4
- 定时器：Quartz 2.3
- 数据库连接池：Druid 1.1.3
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x
- CSS框架：Twitter Bootstrap

## 本地部署
- 通过git下载源码
- 创建数据库jeefast，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse运行jeefast-system工程JeeFastSystemApplication.java，则可启动项目
- 项目访问路径：http://localhost:8080/jeefast
- 账号密码：admin/123456

## 效果图
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212319_6de26405_718698.jpeg "登录.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212334_a2f6eff7_718698.jpeg "系统首页.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212407_312db283_718698.jpeg "用户管理.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212416_b9296c7b_718698.jpeg "部门管理.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212423_eb914cfe_718698.jpeg "菜单管理.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212432_e3e5cc82_718698.jpeg "定时任务.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1106/212443_45d06e1f_718698.jpeg "系统日志.jpg")
![输入图片说明](https://gitee.com/uploads/images/2017/1103/092032_4d28ccb5_718698.jpeg "swagger.jpg")

