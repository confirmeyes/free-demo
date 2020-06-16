### Dockerfile 运行命令

RUN  （ 用于创建镜像 ）

- 在构建镜像时执行 , 修改镜像内部文件

- shell命令格式 || Exec命令格式
- 使用shell执行时，当前shell是父进程 ， 生成一个子shell进程 ， 在子shell中执行脚本，脚本执行完毕，退出子shell，回到当前shell

---

- 使用Exec方式， 用Exec进程替换当前进程，并保持PID不变 ， 执行完毕后，直接退出，并不会退回之前的进程环境
- CMD ["ps","-ef"]

CMD （ 用于创建容器 ）

- 用于设置默认执行都是命令
- 多个CMD，只有最后一个会被执行
- 容器run启动附加指令，则CMD会被忽略 ,不一定会运行

ENTRYPOINT  （ 用于创建容器 ）

- 入口点 ， 用于在容器启动时执行命令
- Dcokerfile中只有最后一个ENTRYPOINT  会被执行



centos 默认会退出 

- docker run -d --name data -it centos /bin/bash
- --link data    配置容器间 名称进行单向通信

网络信息

- docker inspect 容器ID

docker底层网络信息

- docker network is

创建网桥，应用绑定网桥，进行双向通信

- 每创建一个网桥，背后都创建一个虚拟网卡 （网关）。 

- docker network create -d bridge my-bridge
- docker network connect my-bridge 容器名称

---

### 挂载、共享容器

通过设置-v挂载宿主机目录

- docker run --name 容器名 -v 宿主机路径:容器内挂载路径 镜像名

通过 --volumes-form 共享容器内挂载点

- 创建共享容器 (不运行)
- docker create --name webpage  -v /webapps:/tomcat/webapps tomcat /bin/true
- 共享容器挂载点
- docker run --volumes-from webpage --name t1 -d tomcat



### Docker compose 多容器编排工具

- 单机多容器部署工具

- yml文件定义多容器部署

  

