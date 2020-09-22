#### centos下安装docker

- yum install -y yum-utils device-mapper-persistent-data lvm2
- yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

检测哪个安装源最快并使用

- yum makecache fast

docker版本列表

- yum list docker-ce --showduplicates | sort -r

安装docker

- yum -y install docker-ce-18.09*

启动docker并设置开机自启

systemctl start docker

systemctl enable docker

#### 安装racher

- sudo docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher

#### 安装kubectl

- wget http://rancher-mirror.cnrancher.com/kubectl/v1.18.6/linux-amd64-v1.18.6-kubectl
- echo 'source <(kubectl completion bash)'>> .bashrc
- source .bashrc

#### 安装kubesphere

