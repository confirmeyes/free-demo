#### linux

`ps -ef|grep java`   or   `ps -ef|grep  tomcat`	�鿴java����

`source /etc/profile`  �������ļ�������Ч

`ps -aux|grep mysql` �鿴mysql�Ƿ������ɹ�

> aux��BSD���-ef��System V���һ��Ӱ��ʹ�õ�������**aux��ض�command��**����-ef���ᡣ

��װ������������ wget   vim   net-tools   lrzsz

```shell
yum install -y wget vim net-tools lrzsz
```

- ���������;�̬ip�� �����ens ������eth 

  ```shell
  vim /etc/sysconfig/network-scripts/ifcfg-ens33
  ```

- ɾ��UUID ��������dhcp  

- ���� ipaddr gateway netmask dns1 dns2   dns1������һ��

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

- ��������  

  ```shell
  systemctl restart network
  ```

- ����yumԴ  ������

  ```shell
  cd /etc/yum.repos.d/
  mv CentOS-Base.repo CentOS-Base.repo.bak
  ```

  ```shell
  weget -O /etc/yum.repos.d/Centos Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
  ```

  ������

  ```shell
  yum clean all && yum makecache
  ```

- ��װgit

  ```shell
  yum install -y git
  ```

- ��װ����java����

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

- ��װmaven����

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

  

##### ע��˿ڿ��ż�ռ��

`netstat -anp | grep 2181`

> linux����״̬	 -a����ʾ����	-n�����ñ�����ʾ��ֻ��������ʾ		-p����ʾ���̺źͽ�����

- �鿴8000 �˿�ռ�����

```shell
lsof -i:8000
```



##### linux����

��ǰ����ID �����������ӽ��̼�ʱ����ģ�
`echo $$`

`yum -y install psmisc`

`/bin/bash`

`pstree`

#### firewall

- �鿴���д򿪵Ķ˿ڣ� firewall-cmd --zone=public --list-ports
- �鿴�˿��Ƿ�ռ��  netstat -nltp

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
				 `firewall-cmd --reload`
				 `firewall-cmd --complete-reload`

---

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



#### openresty ��������

������װ			
`yum install libreadline-dev libncurses5-dev libpcre3-dev libssl-dev perl  `

openresty��װ
`wget https://openresty.org/download/openresty-1.13.6.2.tar.gz`

