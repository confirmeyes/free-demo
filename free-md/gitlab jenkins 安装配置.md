

![image-20200907112355629](D:\ideaProjects\free-demo\free-md\img\image-20200907112355629.png)



#### 国内的源速度比较快/阿里源/清华源

```shell
vim /etc/yum.repos.d/gitlab-ce.repo
```

```shell
[gitlab-ce]
name=Gitlab CE Repository
baseurl=https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/yum/el$releasever/
gpgcheck=0
enabled=1
```

- 安装

```shell
yum makecache && yum install -y gitlab-ce
```

- 修改配置 external_url

```shell
vim /etc/gitlab/gitlab.rb

external_url 'IP地址+端口号'

gitlab-ctl reconfigure
```


- CENTOS 7 的防火墙 firewalld
```shell
firewall-cmd --zone=public --add-port=8081/tcp -- permanent
```

- 启动GitLab

```shell
gitlab-ctl restart
```



### jenkins 安装



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
  
- 使用清华源进行安装
```shell
yum install -y https://mirrors.tuna.tsinghua.edu.cn/jenkins/redhat/jenkins-2.244-1.1.noarch.rpm
```

- 进行配置 

```shell
vim /etc/sysconfig/jenkins
```

- 修改JENKINS_USER为 root

```shell
JENKINS_USER="jenkins"
```

#### 配置国内安装国外插件问题 不然无法访问

- 重启jenkins

```shell
systemctl restart jenkins
```

- 配置 hudson.model.UpdateCenter.xml 

```shell
<url>https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json</url>
```

- 获取管理员密码

```shell
cat /var/lib/jenkins/secrets/initialAdminPassword
```

- 配置完清华源后 配置 /var/lib/jenkins/updates/default.json 默认谷歌搜索

- 替换为百度搜索

```shell
sed -i 's/http:\/\/updates.jenkins-ci.org\/download/https:\/\/mirrors.tuna.tsinghua.edu.cn\/jenkins/g' default.json && sed -i 's/http:\/\/www.google.com/https:\/\/www.baidu.com/g' default.json
```

```shell
more -5 default.json
```
#### 持续部署

- war包格式 需要另外安装 Publish Over SSH插件