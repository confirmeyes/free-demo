#### linux

`ps -ef|grep java`   or   `ps -ef|grep  tomcat`	�鿴java����

`source /etc/profile`  �������ļ�������Ч

`ps -aux|grep mysql` �鿴mysql�Ƿ������ɹ�

> aux��BSD���-ef��System V���һ��Ӱ��ʹ�õ�������**aux��ض�command��**����-ef���ᡣ

##### ע��˿ڿ��ż�ռ��

`netstat -anp | grep 2181`

> linux����״̬	 -a����ʾ����	-n�����ñ�����ʾ��ֻ��������ʾ		-p����ʾ���̺źͽ�����

##### linux����

��ǰ����ID �����������ӽ��̼�ʱ����ģ�
`echo $$`

`yum -y install psmisc`

`/bin/bash`

`pstree`

#### firewall

`firewall-cmd --zone=public --add-port=8080/tcp --permanent` 	����8081�˿�

`firewall-cmd --zone=public --remove-port=8080/tcp --permanent` 	ɾ��8081�˿�

##### ���ĳ�� IP���Ŷ˿�

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.142.166" port protocol="tcp" port="6379" accept"`

##### ����ָ��IP

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.0.233" accept"`

##### ɾ��ĳ��IP

`firewall-cmd --permanent --remove-rich-rule="rule family="ipv4" source address="192.168.1.51" accept"`

##### ���һ��ip�η���

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.0.0/16" accept"`

##### ���ĳ�� IP�ο��Ŷ˿�

`firewall-cmd --permanent --add-rich-rule="rule family="ipv4" source address="192.168.1.0/24" port protocol="tcp" port="9200" accept"`

���¼���		`firewall-cmd --reload` 

������			   `systemctl restart firewalld.service`
������			    `systemctl start  firewalld`
�鿴״̬�� 		  `systemctl status firewalld` ���� `firewall-cmd --state`

�����Ƿ������� 	`systemctl disable | enable firewalld`
���ã� 		      `systemctl stop firewalld`

���·���ǽ����	
?				 `firewall-cmd --reload`
?				 `firewall-cmd --complete-reload`

---

#### ����������JVM���Ų���

> ��С�ѡ������ڴ���Ϊ1G��ʹ��G1���������������GC��ͣʱ����Ϊ200���룬���ʱ�����������Ӧ�����ռ�Ĵ�С����λ�Ǻ��룩����������С����20%��������30%��
>
> +DisableExplicitGC�ر�ϵͳ����GC���� ��System.gc() Ĭ�ϻᴥ��һ��Full Gc��
> ��ӡGC��GC��ϸ��Ϣ��GCʱ�������out of memory������µ��ڴ�dump�����ָ��·����GC��־·��

`nohup java -jar -server 	-Xms1G 		-Xmx1G 		-XX:+UseG1GC                                                   -XX:MaxGCPauseMillis=200 	-XX:+UnlockExperimentalVMOptions                             -XX:G1NewSizePercent=20         -XX:G1MaxNewSizePercent=30                                  -XX:+DisableExplicitGC 	        -XX:+PrintGC 			-XX:+PrintGCDetails                                -XX:+PrintGCTimeStamps 		-XX:+HeapDumpOnOutOfMemoryError                             -XX:HeapDumpPath=/data/www/$project_name/online/logs/oom-error_8000.log                     -Xloggc:/data/www/$project_name/online/logs/gc_log_8000.log  ***.jar     > ***.log &
`

- ��һ��GC��־���
> ����GC��־��·����%t ���¼��ǰ���ڣ�+UseGCLogFileRotation����������־��
>
> NumberOfGCLogFiles�����ļ�����5����GCLogFileSize�ļ���С20M ������20M��������һ���ļ���

`-Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log 	-XX:+UseGCLogFileRotation                    -XX:NumberOfGCLogFiles=5 			-XX:GCLogFileSize=20M 					-XX:+PrintGCDetails 				-XX:+PrintGCDateStamps 						-XX:+PrintGCCause`

---

##### JVM����˼·

1. - ����JVM���ţ��� Java ���ϳ������⣬�� CPU ���������ͻ�ߡ��ڴ���������⣬��Ҫ����������磬Ȼ�� top��jps��jstat��jstack��jmap��jhat��hprof �Ȳ�����
   - ����OOM Erro dumpת���ļ���GC��־���з������������
2. - ʹ�� Arthas ���ɶ�λ��ͼ�λ����桢Ѹ�ٽ������ʱֹ��
   - <https://blog.csdn.net/u013735734/article/details/102930307>
   - ��أ� arthas /  jconsole /  jvisualVM  / Jprofiler������ã�
3. - ���ϲ��� �� cmdline || arthas ��
   - jstat -gc ��̬�۲�gc���        stat -gc 4655 500 : ÿ��500�������ӡGC�����    


##### �鿴���е� java ������Ϣ ��top��jps ��
$ jps -mlvV 

$ jps -mlvV | grep [xxx]

top 	&&	top -Hp	pid

> top	�鿴���н��̵� CPU���ڴ�״̬
>
> PID     USER    PR    NI    VIRT       RES        SHR       S     %CPU    %MEM     TIME+      COMMAND                                             7945   root      20   0    2813304 301908  14000    S      101.3     30.3         20:44.36       java
>
> top -Hp	pid	 �鿴ָ�������������̵߳� CPU���ڴ�״̬
>
>    PID   USER      PR  NI    VIRT      RES         SHR     S   %CPU     %MEM     TIME+     COMMAND                             7947   root      20   0   2813304 309196  14064   S   93.7         31.0        28:16.02       java 

##### �鿴GC������Ϣ  �� jstat ��

- jstat -gcutil ����ID

>   S0        S1      E         O          M     CCS     YGC     YGCT    FGC    FGCT     GCT      
>   0.00   0.00  99.33  86.40  97.12  94.49     21     2.968     5        1.027    3.996
>
>   S0	--->	������е�һ��survivor���Ҵ�������ʹ�õ�ռ��ǰ�����ٷֱ�
>   S1	--->	������еڶ���survivor���Ҵ�������ʹ�õ�ռ��ǰ�����ٷֱ�
>   E 	--->	 �������Eden������԰����ʹ�õ�ռ��ǰ�����ٷֱ�
>
>   O	--->	 old����ʹ�õ�ռ��ǰ�����ٷֱ�
>   M	--->	 Ԫ���ݿռ�ʹ�ñ���
>   CCS	 --->	 ѹ��ʹ�ñ���
>
>   YGC	 --->		��Ӧ�ó�������������ʱ�������gc����
>   YGCT  --->	��Ӧ�ó�������������ʱ�������gc����ʱ��(s)
>   FGC  --->		��Ӧ�ó�������������ʱold��(ȫgc)gc����
>   FGCT --->	��Ӧ�ó�������������ʱold��(ȫgc)gc����ʱ��(s)
>   GCT   --->	��Ӧ�ó�������������ʱgc�õ���ʱ��(s)

- jstat -gc ��̬�۲�gc���        stat -gc 4655 500 : ÿ��500�������ӡGC�����    

##### ���̡��߳���Ϣ�����з���  ��jinfo ��jstack��jmap ��jhat ��
- jinfo pid

  ���̺��̵߳� ������Ϣ

- jstack

   jstack ��λ�̡߳�����״��

- jmap   -histo 4655 | head -20

  �����ж��ٶ������

- jmap -dump:live,format=b,file=heap.bin  pid

  ����dumpת���ļ�������ϵͳ���ڴ��ر��jmapִ���ڼ��Խ��̲����ܴ�Ӱ�죬�������٣����̲��ʺϣ�

- ʹ��MAT / jhat /jvisualvm ����dump�ļ�����
   https://www.cnblogs.com/baihuitestsoftware/articles/6406271.html 

- jhat -J-mx512M xxx.dump
  http://192.168.17.11:7000



##### ������־

dump �ļ��ֵ�ù�ע���߳�״̬�У�
- ������Deadlock���ص��ע�� 

- ִ���У�Runnable   

- �ȴ���Դ��Waiting on condition���ص��ע�� 

- �ȴ���ȡ��������Waiting on monitor entry���ص��ע��

- ��ͣ��Suspended

- ����ȴ��У�Object.wait() �� TIMED_WAITING

- ������Blocked���ص��ע��  

- ֹͣ��Parked

  >tidָJava Thread id��nidָnative�̵߳�id��prio���߳����ȼ���
  >
  >[0x00007f1d2c4be000] ���߳�ջ��ʼ��ַ��
  >
  >Thread.State:WAITING (parking) �ȴ����̹߳����С�
  >
  >parking to wait for  <0x00000000f5dee038>
  >
  >���߳̿϶����ڵȴ�ĳ�������ķ����������Լ����ѡ���Σ�SynchronousQueue ������һ�����У�ֻ���߳�֮���ƽ���Ϣ�Ļ��ƣ������ǰ�һ��Ԫ�ط��뵽 SynchronousQueue ��ʱ��������һ���߳����ڵȴ������ƽ��������������Ǳ��߳��ڵȴ���������

dump�ļ�ʵ������

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

##### JVM���ò������

- nohup java -jar -server 
> ����Ϊ�����ģʽ�� ���������� -client
- nohup java -jar project.jar >> log.out 2>&1 &

> ��������ͱ�׼�����׷�ӵ�log.out
- nohup java -jar Project.jar >/dev/null 2>log & 
>��׼�����/dev/null �� �ն� ����ֻ���������Ϣ��log

> nohup ���ж����У����û��ն��޹�
\>>      ����ض���
2>&1    (2)��׼�������   (>) �ض���  (&1)��׼���
2>&1 ��׼��������ض��򵽱�׼���
&       ��ʶ����Ϊ��̨����

- -Xms$max_memory               -Xmx$max_memory 

  ָ��Xms��С�ѣ�Xmx���ѵĴ�С������ÿ������������ɺ�JVM���·����ڴ�.

- -XX:+UseG1GC  

  G1����������

- -XX:MaxGCPauseMillis=200 

  ���GC��ͣʱ�䣬���ʱ�����������Ӧ�����ռ�Ĵ�С����λ�Ǻ��룩

- -XX:+UnlockExperimentalVMOptions 

  ��Щʱ������һ���ض���JVM����ʱ��JVM���������Unrecognized VM option������ֹ����������������������Ӧ�����ȼ�����Ƿ�����˲�����Ȼ�������������������ȷ�ģ�����JVM����ʶ�𣬻�����Ҫ����-XX:+UnlockExperimentalVMOptions ����������

- -XX:G1NewSizePercent=20      -XX:G1MaxNewSizePercent=30

  ��������С����20%��������30%

- -XX:+DisableExplicitGC           

  �ر�ϵͳ����GC����    System.gc() Ĭ�ϻᴥ��һ��Full Gc

- -XX:+PrintGC            -XX:+PrintGCDetails         -XX:+PrintGCTimeStamps 

  ��ӡGC��GC��ϸ��Ϣ��GCʱ���

- -XX:+HeapDumpOnOutOfMemoryError 
  -XX:HeapDumpPath=/data/www/$project_name/online/logs/oom-error_8000.log 

  ������OOM�ڴ����ʱ���ж�ת������ָ������·��

- -Xloggc:/data/www/$project_name/online/logs/gc_log_8000.log 

  ָ��GC��־·��


#### Redis

`wget http:``//download.redis.io/releases/redis-4.0.11.tar.gz`

`ps  aux|grep redis`	�鿴redis����      ` ./redis-server redis.conf`	��̨����

`./redis-cli`	redis�ͻ�������             ` ./redis-cli shutdown	`	����

�л�redis��ip:	 `./redis-cli -h 192.168.60.130 -p 6379`



#### zookeeper
`wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz`
##### ���û�������
`export ZOOKEEPER_INSTALL=/home/java/zookeeper-3.4.14/`
`export PATH=$PATH:$ZOOKEEPER_INSTALL/bin`
#####  ����˲���
`./zkServer.sh {start|start-foreground|stop|restart|status|upgrade|print-cmd}`
##### �ͻ�������
`./zkCli.sh -server 192.168.88.130,192.168.88.131,192.168.88.133`

`create [-s][-e]  path  data  acl`

����-s��ʾ˳��ڵ㣬-e��ʾ��ʱ�ڵ㡣Ĭ������£��������ǳ־ýڵ㡣

path�ǽڵ�·����data�ǽڵ����ݣ�acl����������Ȩ�޿��Ƶġ�



#### kafka
����kafka
`./bin/kafka-server-start.sh -daemon ./config/server.properties`
�г���Ⱥ��ǰ���п��õ�topic��
`bin/kafka-topics.sh --list --zookeeper ? zookeeper_address`
�鿴��Ⱥ�ض�topic ��Ϣ��
`bin/kafka-topics.sh --describe --zookeeper zookeeper_address??--topic topic_name`

`ZooKeeper -server host:port cmd args`



#### Mysql
##### ��װ

ʹ��wget���عٷ�yumԴ��rpm��

`wget <https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm>`

��ж��ԭ��mariadb-libs

`yum remove mariadb-libs -y` 

`rpm -ivh mysql57-community-release-el7-11.noarch.rpm`

ʹ��yum��װ myqsql-server

` yum -y install mysql-community-server`

##### ����ʹ��

����Mysql

`systemctl start mysqld` 	����	`systemctl restart mysqld`

�鿴Mysql����״̬

`systemctl status mysqld`

��������

`systemctl enable mysqld`

`systemctl daemon-reload`

##### �޸�����

�鿴��װ��ɺ���/var/log/mysqld.log�ļ��и�root������һ��Ĭ�����롣ʹ����������鿴

`grep 'temporary password' /var/log/mysqld.log`

��¼

`mysql -u root -p`  ����Ĭ������

�޸�����

`ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyData134.';`

���� `set password for 'root'@'localhost'=password('MyData4!');`

����鿴���ݿ������
`cat /var/log/mysqld.log | grep password` 

�������ݿ��½���棬����ող鵽�����룬�������ݿ�ĵ�½������ճ�����У�MySQL �ĵ�½���벻��ʾ
`mysql -u root -p`

ͨ��mysql���������鿴�������ɲ���

`show variables like '%password%';`

##### ���Զ�̵�¼�û�
`GRANT ALL PRIVILEGES ON *.* TO 'root' IDENTIFIED BY 'MyData4!' WITH GRANT OPTION;`

`GRANT REPLICATION SLAVE,RELOAD,SUPER ON *.* TO copyup@'192.168.88.135' IDENTIFIED BY 'CopyUp135.';`

##### ����Ĭ�ϱ���Ϊutf8
�޸�/etc/my.cnf�����ļ�
`[mysqld]
character_set_server=utf8
init_connect='SET NAMES utf8'`

#####  �����ݿ�����---�����Ȩ�û�

`mysql> CHANGE MASTER TO
?     MASTER_HOST='192.168.88.134', #����������ַ
?     MASTER_USER='copyup',    #����������Ȩ���˻�
?     MASTER_PASSWORD='CopyUp135.',  #����������Ȩ���˻�����
?     MASTER_PORT=3306,   #���ݿ�˿�
?     MASTER_LOG_FILE='mysql-bin.000002',  #��������log-file
?     MASTER_LOG_POS=0,    #��������Position
?     MASTER_CONNECT_RETRY=10;  #��������ʱ��`


#####  �鿴�ڵ��״̬
`show slave status\G;`
`show master status\G;`




#### Docker ����

����Ƿ�װ�ɹ�			`docker version`

���ÿ�����������ر�		`systemctl enable||disable docker`

����docker				`systemctl start docker`

����docker				`systemctl restart docker.service`

ͨ���鿴`netstat`ȷ�ϵ�����Ƿ�`dockerd`�������������õĶ˿����������Ƿ�õ����ء�

`netstat -lntp | grep dockerd`

�鿴�����б�				`docker images`



#### openresty ��������

������װ			
`yum install libreadline-dev libncurses5-dev libpcre3-dev libssl-dev perl  `

openresty��װ
`wget https://openresty.org/download/openresty-1.13.6.2.tar.gz`

