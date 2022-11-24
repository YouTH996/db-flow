# 项目说明

- db-flow是一个定量级的，单体版的数据库增删改查平台，能快速处理数据库中的数据
- 采用SpringBoot、jdbc开发，无其他第三方依赖，门槛极低
- 项目前端采用Vue.js语法编写，但并未实现前后端分离，从而无需再次进行前后端的整合和部署，只用一个jar包，便可以做到网页版Navicat的功能
- 支持MySQL数据库（未适配其他数据库）
- 项目演示地址：[http://81.68.186.244:9000/db.html](http://81.68.186.244:9000/db.html)

#### 项目结构

```
db-flow
├─src
│  └─main
│      ├─java
│      │  └─com
│      │      └─axfiber
│      │          └─dbflow
│      │              ├─config
│      │              ├─controller
│      │              ├─dto
│      │              ├─service
│      │              └─utils
│      │                  └─exception
│      └─resources
│          ├─static
│          │  ├─css
│          │  │  └─fonts
│          │  └─js
│          └─templates
```

#### 技术选型：

- 核心框架：Spring Boot 2.2.4.RELEASE
- 数据库连接框架：JDBC 8.0.13
- 工具类框架：Hutool 5.8.0
- JSON框架：Fastjson 2.0.12
- 日志管理：Logback
- 页面交互：Vue2.x

#### 软件需求

- JDK1.8
- IntelliJ IDEA、Eclipse
- MySQL8.0

#### 本地部署

1. 下载源码
2. idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
3. 等待Maven自动下载依赖
4. 修改application.yml配置文件（一般保持默认即可）
5. 启动Application
6. 访问:[http://localhost:9000/index.html](http://localhost:9000/index.html)

![image-20221123152918202](https://filecunzhu.oss-cn-shanghai.aliyuncs.com/img/image-20221123152918202.png)

![image-20221123152953435](https://filecunzhu.oss-cn-shanghai.aliyuncs.com/img/image-20221123152953435.png)
