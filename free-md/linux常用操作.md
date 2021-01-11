#### linux

`ps -ef|grep java`   or   `ps -ef|grep  tomcat`	查看java进程

`source /etc/profile`  让配置文件立即生效

`ps -aux|grep mysql` 查看mysql是否启动成功

> aux是BSD风格，-ef是System V风格。一个影响使用的区别是**aux会截断command列**，而-ef不会。

安装虚拟机基本软件 wget   vim   net-tools   lrzsz

```shell
yum install -y wget vim net-tools lrzsz
```

- 配置网卡和静态ip， 虚拟机ens 云主机eth 

  ```shell
  vim /etc/sysconfig/network-scripts/ifcfg-ens33
  ```

- 删除UUID ，第三行dhcp  

- 增加 ipaddr gateway netmask dns1 dns2   dns1与网关一致

- ```shell
  TYPE=Ethernet
  PROXY_METHOD=none
  BROWSER_ONLY=no
  BOOTPROTO=static
  DEFROUTE=yes
  IPV4_FAILURE_FATAL=no
  IPV6INIT=yes
  IPV6_AUTOCONF=yes
  IPV6_DEFROUTE=yes
  IPV6_FAILURE_FATAL=no
  IPV6_ADDR_GEN_MODE=stable-privacy
  NAME=ens33
  DEVICE=ens33
  ONBOOT=yes
  
  DNS1=114.114.114.114
  DNS2=8.8.8.8
  GATEWAY=192.168.1.1
  IPADDR=192.168.1.9
  NETMASK=255.255.255.0
  ZONE=public
  ```

- 

- 重启网络  

  ```shell
  systemctl restart network
  ```

- 配置yum源  并备份

  ```shell
  cd /etc/yum.repos.d/
  mv CentOS-Base.repo CentOS-Base.repo.bak
  ```

  ```shell
  weget -O /etc/yum.repos.d/Centos Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
  ```

  清理缓存

  ```shell
  yum clean all && yum makecache
  ```

- 安装git

  ```shell
  yum install -y git
  ```

- 安装配置java环境

  ```shell
  yum install -y java-1.8*
  rpm -qa | grep java
  vim /etc/profile
  
  ----------------------
  export JAVA_HOME=/usr/lib/jvm/ java...
  export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export PATH=$PATH:$JAVA_HOME/bin
  -------------------------
  
  source /etc/profile
  ```

- 安装maven配置

  ```shell
  wget https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.5.4/binaries/apache-maven
  -3.5.4-bin.tar.gz
  tar -zxvf apache-maven-3.5.4-bin.tar.gz
  mv apache-maven-3.5.4-bin.tar.gz maven
  cd maven
  ```

  ```shell
  vim /etc/profile
  export M2_HOME=/usr/local/maven
  export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$M2_HOME/bin:$PATH
  ```

  

##### 注意端口开放及占用

`netstat -anp | grep 2181`

> linux网络状态	 -a，显示所有	-n，不用别名显示，只用数字显示		-p，显示进程号和进程名

- 查看8000 端口占用情况

```shell
lsof -i:8000
```



##### linux进程

当前进程ID （父进程与子进程间时隔离的）
`echo $$`

`yum -y install psmisc`

`/bin/bash`

`pstree`

#### firewall

- 查看所有打开的端口： firewall-cmd --zone=public --list-ports
- 查看端口是否占用  netstat -nltp

`firewall-cmd --zone=public --add-port=8080/tcp --permanent` 	开放8081端口

`firewall-cmd --zone=public --remove-port=8080/tcp --permanent` 	删除8081端口

##### 针对某个 IP开放端口

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.142.166" port protocol="tcp" port="6379" accept"`

##### 开放指定IP

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.0.233" accept"`

##### 删除某个IP

`firewall-cmd --permanent --remove-rich-rule="rule family="ipv4" source address="192.168.1.51" accept"`

##### 针对一个ip段访问

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.0.0/16" accept"`

##### 针对某个 IP段开放端口

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.1.0/24" port protocol="tcp" port="9200" accept"`

重新加载		`firewall-cmd --reload` 

重启：			   `systemctl restart firewalld.service`
启动：			    `systemctl start  firewalld`
查看状态： 		  `systemctl status firewalld` 或者 `firewall-cmd --state`

开机是否启动： 	`systemctl disable | enable firewalld`
禁用： 		      `systemctl stop firewalld`

更新防火墙规则：	
				 `firewall-cmd --reload`
				 `firewall-cmd --complete-reload`

---

#### Redis

`wget http:``//download.redis.io/releases/redis-4.0.11.tar.gz`

`ps  aux|grep redis`	查看redis进程      ` ./redis-server redis.conf`	后台启动

`./redis-cli`	redis客户端启动             ` ./redis-cli shutdown	`	结束

切换redis的ip:	 `./redis-cli -h 192.168.60.130 -p 6379`



#### zookeeper
`wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz`
##### 配置环境变量
`export ZOOKEEPER_INSTALL=/home/java/zookeeper-3.4.14/`
`export PATH=$PATH:$ZOOKEEPER_INSTALL/bin`
#####  服务端操作
`./zkServer.sh {start|start-foreground|stop|restart|status|upgrade|print-cmd}`
##### 客户端连接
`./zkCli.sh -server 192.168.88.130,192.168.88.131,192.168.88.133`

`create [-s][-e]  path  data  acl`

其中-s表示顺序节点，-e表示临时节点。默认情况下，创建的是持久节点。

path是节点路径，data是节点数据，acl是用来进行权限控制的。



#### kafka
启动kafka
`./bin/kafka-server-start.sh -daemon ./config/server.properties`
列出集群当前所有可用的topic：
`bin/kafka-topics.sh --list --zookeeper ? zookeeper_address`
查看集群特定topic 信息：
`bin/kafka-topics.sh --describe --zookeeper zookeeper_address??--topic topic_name`

`ZooKeeper -server host:port cmd args`



#### Mysql
##### 安装

使用wget下载官方yum源的rpm包

`wget <https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm>`

先卸载原包mariadb-libs

`yum remove mariadb-libs -y` 

`rpm -ivh mysql57-community-release-el7-11.noarch.rpm`

使用yum安装 myqsql-server

` yum -y install mysql-community-server`

##### 基本使用

启动Mysql

`systemctl start mysqld` 	重启	`systemctl restart mysqld`

查看Mysql服务状态

`systemctl status mysqld`

开机启动

`systemctl enable mysqld`

`systemctl daemon-reload`

##### 修改密码

查看安装完成后，在/var/log/mysqld.log文件中给root生成了一个默认密码。使用以下命令查看

`grep 'temporary password' /var/log/mysqld.log`

登录

`mysql -u root -p`  输入默认密码

修改密码

`ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyData134.';`

或者 `set password for 'root'@'localhost'=password('MyData4!');`

命令查看数据库的密码
`cat /var/log/mysqld.log | grep password` 

进入数据库登陆界面，输入刚刚查到的密码，进行数据库的登陆，复制粘贴就行，MySQL 的登陆密码不显示
`mysql -u root -p`

通过mysql环境变量查看密码生成策略

`show variables like '%password%';`

##### 添加远程登录用户
`GRANT ALL PRIVILEGES ON *.* TO 'root' IDENTIFIED BY 'MyData4!' WITH GRANT OPTION;`

`GRANT REPLICATION SLAVE,RELOAD,SUPER ON *.* TO copyup@'192.168.88.135' IDENTIFIED BY 'CopyUp135.';`

##### 配置默认编码为utf8
修改/etc/my.cnf配置文件
`[mysqld]
character_set_server=utf8
init_connect='SET NAMES utf8'`

#####  从数据库配置---添加授权用户

`mysql> CHANGE MASTER TO
?     MASTER_HOST='192.168.88.134', #主服务器地址
?     MASTER_USER='copyup',    #主服务器授权的账户
?     MASTER_PASSWORD='CopyUp135.',  #主服务器授权的账户密码
?     MASTER_PORT=3306,   #数据库端口
?     MASTER_LOG_FILE='mysql-bin.000002',  #主服务器log-file
?     MASTER_LOG_POS=0,    #主服务器Position
?     MASTER_CONNECT_RETRY=10;  #重新连接时间`


#####  查看节点的状态
`show slave status\G;`
`show master status\G;`



#### openresty 常用命令

依赖安装			
`yum install libreadline-dev libncurses5-dev libpcre3-dev libssl-dev perl  `

openresty安装
`wget https://openresty.org/download/openresty-1.13.6.2.tar.gz`

