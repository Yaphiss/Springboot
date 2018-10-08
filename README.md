# klg-data-report

#### 项目介绍
{**以下是码云平台说明，您可以替换为您的项目简介**
码云是开源中国推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 项目结构以及说明


#### 使用说明

- 本地运行
    
    - gradle bootrun

- 测试环境运行

    - 关注文件：*/resources/application-dev.properties
    - 运行构建命令：gradle bootJar
    - 启动命令：java -jar build/libs/gs-spring-boot-0.1.0.jar --spring.profiles.active=dev
    
- 生产环境运行
    
    - 关注文件：*/resources/production-config.properties
    - 运行构建命令：gradle bootJar
    - 启动命令：java -jar build/libs/gs-spring-boot-0.1.0.jar --spring.profiles.active=production
    
    
    这里需要注意的是，如果配置文件中含有敏感信息的话，可以在运行jar包的时候进行配置。
    例如重置端口：java -jar xxx.jar --server.port=0000

    

##### REDIS
- 本项目中使用redis实现简单的分布式锁，使用注解的方式进行注入，使用的方式可参考api.*Controller
- 在接口上面加上@CacheLock(prefix="test""),动态值可以加上@CacheParam
- 例子：假如上面的test=1，那最终锁的资源key为test:1，如果有多个条件，则以此类推

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)