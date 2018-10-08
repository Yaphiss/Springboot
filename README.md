# Springboot

#### 项目介绍
- 这是一个基于springboot搭建的脚手架，使用gradle进行构建和打包
- 数据库使用MongoDB
- 不同环境下可以加载不同配置
- redis实现简单的资源锁

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
