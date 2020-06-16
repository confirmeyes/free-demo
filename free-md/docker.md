### centos下安装docker

- yum install -y yum-utils device-mapper-persistent-data lvm2
- yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

检测哪个安装源最快并使用

- yum makecache fast

安装docker

- yum -y install docker-ce
- 验证 docker version
- docker （ CS架构 ） go语言编写 ，docker 的 client 、server

拉取镜像

- docker pull  镜像名<:tags>        tags 镜像版本

创建容器，启动应用

- docker run 镜像名<:tags>
- docker run -p 8000:8080 镜像名<:tags>           将宿主机8000 端口 映射到 8080
- netstat -tulpn       查看端口号
- docker run -p 8000:8080 -d 镜像名<:tags>       -d表示后台运行
- docker start 镜像名<:tags>  

查看本地镜像

- docker images 

查看正在运行的镜像

- docker ps
- docker ps -a      所有容器
- docker stop containerID       停止指定容器

删除容器

- docker rm <-f>  容器ID          -f 强制删除，包括正在运行的

删除镜像

- docker rmi <-f>  镜像名<:tags>

docker容器存放位置

- cd /var/lib/docker



#### 容器中执行命令

- docker exec [-it]  容器ID  命令
- docker exec -it 容器ID /bin/bash
- cat /proc/version       linux内核版本



#### Dockerfile描述文件

自定义镜像

- docker build -t 机构/镜像名<:tags> dcokerfile目录

- ```
  From tomcat:latest
  设置基准镜像
  ```

- ```
  MAINTAINER
  设置机构
  ```

- ```
  WORKDIR /usr/local/tomcat/webapps
  等同于切换工作路径，不存在则创建
  ```

- ```
  ADD docker-web ./docker-web
  复制目录下所有文件到容器 目录
  ```
  
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


