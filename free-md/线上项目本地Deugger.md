#### 线上项目本地进行debugger调试

```linux
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,address=远程连接端口号,server=y,suspend=y jar名称.jar
```



1.Xdebug: debug方式启动 

Xrunjdwp: java 远程调试协议 (包含参数以下) 

 1.transport: 传输协议 

 2.address:远程连接端口 

 3.:server:是否是服务 

 4.suspend:是否挂起