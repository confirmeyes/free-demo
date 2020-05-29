#### linux

`ps -ef|grep java`   or   `ps -ef|grep  tomcat`	查看java进程

`source /etc/profile`  让配置文件立即生效

`ps -aux|grep mysql` 查看mysql是否启动成功

> aux是BSD风格，-ef是System V风格。一个影响使用的区别是**aux会截断command列**，而-ef不会。

##### 注意端口开放及占用

`netstat -anp | grep 2181`

> linux网络状态	 -a，显示所有	-n，不用别名显示，只用数字显示		-p，显示进程号和进程名

##### linux进程

当前进程ID （父进程与子进程间时隔离的）
`echo $$`

`yum -y install psmisc`

`/bin/bash`

`pstree`

#### firewall

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
?				 `firewall-cmd --reload`
?				 `firewall-cmd --complete-reload`

---

#### 生产环境下JVM调优参数

> 最小堆、最大堆内存设为1G，使用G1垃圾回收器，最长的GC暂停时间设为200毫秒，如果时间过长，会相应调整空间的大小（单位是毫秒），新生代最小比例20%，最大比例30%，
>
> +DisableExplicitGC关闭系统调用GC功能 【System.gc() 默认会触发一次Full Gc】
> 打印GC及GC详细信息、GC时间戳，在out of memory的情况下的内存dump输出到指定路径，GC日志路径

`nohup java -jar -server 	-Xms1G 		-Xmx1G 		-XX:+UseG1GC                                                   -XX:MaxGCPauseMillis=200 	-XX:+UnlockExperimentalVMOptions                             -XX:G1NewSizePercent=20         -XX:G1MaxNewSizePercent=30                                  -XX:+DisableExplicitGC 	        -XX:+PrintGC 			-XX:+PrintGCDetails                                -XX:+PrintGCTimeStamps 		-XX:+HeapDumpOnOutOfMemoryError                             -XX:HeapDumpPath=/data/www/$project_name/online/logs/oom-error_8000.log                     -Xloggc:/data/www/$project_name/online/logs/gc_log_8000.log  ***.jar     > ***.log &
`

- 另一种GC日志输出
> 设置GC日志的路径，%t 会记录当前日期，+UseGCLogFileRotation开启滚动日志，
>
> NumberOfGCLogFiles设置文件数量5个，GCLogFileSize文件大小20M （超过20M，进入下一个文件）

`-Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log 	-XX:+UseGCLogFileRotation                    -XX:NumberOfGCLogFiles=5 			-XX:GCLogFileSize=20M 					-XX:+PrintGCDetails 				-XX:+PrintGCDateStamps 						-XX:+PrintGCCause`

---

##### JVM调优思路

1. - 常规JVM调优：当 Java 线上出现问题，如 CPU 飙升、负载突高、内存溢出等问题，需要查命令，查网络，然后 top、jps、jstat、jstack、jmap、jhat、hprof 等操作。
   - 对于OOM Erro dump转储文件、GC日志进行分析，解决问题
2. - 使用 Arthas 轻松定位，图形化界面、迅速解决，及时止损。
   - <https://blog.csdn.net/u013735734/article/details/102930307>
   - 监控： arthas /  jconsole /  jvisualVM  / Jprofiler（最好用）
3. - 线上查找 （ cmdline || arthas ）
   - jstat -gc 动态观察gc情况        stat -gc 4655 500 : 每个500个毫秒打印GC的情况    


##### 查看运行的 java 进程信息 （top、jps ）
$ jps -mlvV 

$ jps -mlvV | grep [xxx]

top 	&&	top -Hp	pid

> top	查看所有进程的 CPU、内存状态
>
> PID     USER    PR    NI    VIRT       RES        SHR       S     %CPU    %MEM     TIME+      COMMAND                                             7945   root      20   0    2813304 301908  14000    S      101.3     30.3         20:44.36       java
>
> top -Hp	pid	 查看指定进程下所有线程的 CPU、内存状态
>
>    PID   USER      PR  NI    VIRT      RES         SHR     S   %CPU     %MEM     TIME+     COMMAND                             7947   root      20   0   2813304 309196  14064   S   93.7         31.0        28:16.02       java 

##### 查看GC汇总信息  （ jstat ）

- jstat -gcutil 进程ID

>   S0        S1      E         O          M     CCS     YGC     YGCT    FGC    FGCT     GCT      
>   0.00   0.00  99.33  86.40  97.12  94.49     21     2.968     5        1.027    3.996
>
>   S0	--->	年轻代中第一个survivor（幸存区）已使用的占当前容量百分比
>   S1	--->	年轻代中第二个survivor（幸存区）已使用的占当前容量百分比
>   E 	--->	 年轻代中Eden（伊甸园）已使用的占当前容量百分比
>
>   O	--->	 old代已使用的占当前容量百分比
>   M	--->	 元数据空间使用比例
>   CCS	 --->	 压缩使用比例
>
>   YGC	 --->		从应用程序启动到采样时年轻代中gc次数
>   YGCT  --->	从应用程序启动到采样时年轻代中gc所用时间(s)
>   FGC  --->		从应用程序启动到采样时old代(全gc)gc次数
>   FGCT --->	从应用程序启动到采样时old代(全gc)gc所用时间(s)
>   GCT   --->	从应用程序启动到采样时gc用的总时间(s)

- jstat -gc 动态观察gc情况        stat -gc 4655 500 : 每个500个毫秒打印GC的情况    

##### 进程、线程信息并进行分析  （jinfo 、jstack、jmap 、jhat ）
- jinfo pid

  进程和线程的 具体信息

- jstack

   jstack 定位线程、进程状况

- jmap   -histo 4655 | head -20

  查找有多少对象产生

- jmap -dump:live,format=b,file=heap.bin  pid

  生成dump转储文件，线上系统，内存特别大，jmap执行期间会对进程产生很大影响，甚至卡顿（电商不适合）

- 使用MAT / jhat /jvisualvm 进行dump文件分析
   https://www.cnblogs.com/baihuitestsoftware/articles/6406271.html 

- jhat -J-mx512M xxx.dump
  http://192.168.17.11:7000



##### 分析日志

dump 文件里，值得关注的线程状态有：
- 死锁，Deadlock（重点关注） 

- 执行中，Runnable   

- 等待资源，Waiting on condition（重点关注） 

- 等待获取监视器，Waiting on monitor entry（重点关注）

- 暂停，Suspended

- 对象等待中，Object.wait() 或 TIMED_WAITING

- 阻塞，Blocked（重点关注）  

- 停止，Parked

  >tid指Java Thread id。nid指native线程的id。prio是线程优先级。
  >
  >[0x00007f1d2c4be000] 是线程栈起始地址。
  >
  >Thread.State:WAITING (parking) 等待，线程挂起中。
  >
  >parking to wait for  <0x00000000f5dee038>
  >
  >本线程肯定是在等待某个条件的发生，来把自己唤醒。其次，SynchronousQueue 并不是一个队列，只是线程之间移交信息的机制，当我们把一个元素放入到 SynchronousQueue 中时必须有另一个线程正在等待接受移交的任务，因此这就是本线程在等待的条件。

dump文件实例分析

<https://www.cnblogs.com/zhengyun_ustc/archive/2013/01/06/dumpanalysis.html>

> "pool-1-thread-34" #41 prio=5 os_prio=0 tid=0x00007f1d501a2000 nid=0x2001 waiting on condition [0x00007f1d2c4be000]
>    java.lang.Thread.State: WAITING (parking)
> ?	at sun.misc.Unsafe.park(Native Method)
>
>  - parking to wait for  <0x00000000f5dee038>
>      (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
>        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
>        at       	java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
>        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088)
>        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)
>        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
>        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
>        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
>        at java.lang.Thread.run(Thread.java:748)

##### JVM常用参数详解

- nohup java -jar -server 
> 设置为服务端模式， 吞吐量优先 -client
- nohup java -jar project.jar >> log.out 2>&1 &

> 错误输出和标准输出，追加到log.out
- nohup java -jar Project.jar >/dev/null 2>log & 
>标准输出至/dev/null （ 空洞 ），只输出错误信息至log

> nohup 不中断运行，与用户终端无关
\>>      输出重定向
2>&1    (2)标准错误输出   (>) 重定向到  (&1)标准输出
2>&1 标准错误输出重定向到标准输出
&       标识进程为后台进程

- -Xms$max_memory               -Xmx$max_memory 

  指定Xms最小堆，Xmx最大堆的大小，避免每次垃圾回收完成后JVM重新分配内存.

- -XX:+UseG1GC  

  G1垃圾回收器

- -XX:MaxGCPauseMillis=200 

  最长的GC暂停时间，如果时间过长，会相应调整空间的大小（单位是毫秒）

- -XX:+UnlockExperimentalVMOptions 

  有些时候当设置一个特定的JVM参数时，JVM会在输出“Unrecognized VM option”后终止。如果发生了这种情况，你应该首先检查你是否输错了参数。然而，如果参数输入是正确的，并且JVM并不识别，或许需要设置-XX:+UnlockExperimentalVMOptions 来解锁参数

- -XX:G1NewSizePercent=20      -XX:G1MaxNewSizePercent=30

  新生代最小比例20%，最大比例30%

- -XX:+DisableExplicitGC           

  关闭系统调用GC功能    System.gc() 默认会触发一次Full Gc

- -XX:+PrintGC            -XX:+PrintGCDetails         -XX:+PrintGCTimeStamps 

  打印GC、GC详细信息、GC时间戳

- -XX:+HeapDumpOnOutOfMemoryError 
  -XX:HeapDumpPath=/data/www/$project_name/online/logs/oom-error_8000.log 

  当出现OOM内存溢出时进行堆转储，并指定具体路径

- -Xloggc:/data/www/$project_name/online/logs/gc_log_8000.log 

  指定GC日志路径


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




#### Docker 操作

检查是否安装成功			`docker version`

设置开机自启动或关闭		`systemctl enable||disable docker`

启动docker				`systemctl start docker`

重启docker				`systemctl restart docker.service`

通过查看`netstat`确认的输出是否`dockerd`正在侦听已配置的端口来检查更改是否得到遵守。

`netstat -lntp | grep dockerd`

查看镜像列表				`docker images`



#### openresty 常用命令

依赖安装			
`yum install libreadline-dev libncurses5-dev libpcre3-dev libssl-dev perl  `

openresty安装
`wget https://openresty.org/download/openresty-1.13.6.2.tar.gz`

