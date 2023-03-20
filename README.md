## 一、项目介绍

nacos-cluster目的解决nacos的单点问题实现nacos的高可用。

什么是nacos？

Nacos 官网 https://nacos.io/

一个更易于构建云原生应用的动态服务发现、配置管理和**服务管理平台**。**直白了说：既是一个注册中心也是一个配置中心**。

![image-20230312164310880](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8Bimage-20230312164310880.png)

## 二、nacos数据库配置

创建nacos数据库

使用项目中的nacos-mysql.sql创建nacos的数据库表。

![image-20230312164634101](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8Bimage-20230312164634101.png)

打开 Nacos 安装目录下的 conf/application.properties 文件。

![image-20230312164804569](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8Bimage-20230312164804569.png)

重启服务：看到use external storage表示成功

![image-20230312164835430](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8Bimage-20230312164835430.png)

## 三、nacos集群搭建

搭建三台nacos

首先随意创建一个目录，用于存放三个 Nacos 服务器。然后再复制原来配置好的单机版的 Nacos 到这个目录，并重命名为 nacos8847。

打开 nacos8847/conf，重命名其中的 cluster.conf.example 为 cluster.conf。然后打开该文件，在其中写入三个 nacos 的 ip:port。

![image-20230312085357769](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/image-20230312085357769.png)

然后再打开 nacos8847/conf/application.properties 文件，修改端口号为 8847，配置外置MySQL。

![image-20230312085519517](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/image-20230312085519517.png)

然后再将 nacos8847 目录复制三份，分别命名为 nacos8848、nacos8849。并重新指定端口号分别为 8848 与 8849。

![image-20230312085544041](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/image-20230312085544041.png)

启动集群

```
D:\nacos-cluster\nacos-8847\bin\startup.cmd -m cluster2 
D:\nacos-cluster\nacos-8848\bin\startup.cmd -m cluster3 
D:\nacos-cluster\nacos-8849\bin\startup.cmd -m cluster
```

![image-20230312085656023](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/image-20230312085656023.png)

项目配置

```yml
spring:
  application:
    name: msc-gateway
  cloud:
    # 指定nacos注册中心的地址
    nacos:
      config:
        server-addr: 127.0.0.1:8848,127.0.0.1:8847,127.0.0.1:8849
```

启动服务：

![image-20230320030620117](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/image-20230320030620117.png)

接口测试：http://127.0.0.1:1000/api-payment/payment/1

多次调用返回的执行结果

```json
{
    "id": 1,
    "message": "支付成功，payment{port:8000}{paymentName:damon-dev-cn}"
}

{
    "id": 1,
    "message": "支付成功，payment{port:8001}{paymentName:damon-dev-cn}"
}

{
    "id": 1,
    "message": "支付成功，payment{port:8002}{paymentName:damon-dev-cn}"
}
```


## 写在最后

由于本人目前在准备换工作，无暇编写更详细的文档，请小伙伴们先自行阅读核心部分的代码，若对项目有更好建议者，请发送邮件至670682988@qq.com，如果觉得本项目比较nice,麻烦动动您发财的小手**start**一下，感谢！

