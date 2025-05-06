# Quick Start - 快速开始

## 确认系统需求

- CPU：2核以上。
- 内存：1G 以上。
- 硬盘：10G 以上。
- CentOS 7。
- JRE 1.8。
- Zookeeper 3.5.5。

## 获取软件包

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

## 解压软件包

软件包的名称格式为 `snowflake-distributed-service-node-${version}-release.tar.gz`，其中 `${version}` 为软件包的版本号。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf snowflake-distributed-service-node-${version}-release.tar.gz
mv snowflake-distributed-service-node-${version}-release snowflake
```

## 最小化配置

下文列出了启动程序需要改动的最少的配置文件，每个配置文件中仅展示需要改动的配置项。
在下文中，每个配置项之前的注释内容为如何修改该配置项，请参考注释内容修改该对应位置的配置文件中的内容（无需将注释粘贴到配置文件之中）。

`conf/dubbo/connection.properties` 文件中配置 Dubbo 连接信息。

```properties
# 改为服务连接的 zookeeper 的地址。
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
# 改为服务所在主机的 ip 地址。
dubbo.protocol.dubbo.host=your-host-here
```

`conf/telqos/connection.properties` 文件中配置 Telqos 服务信息。

```properties
# 改为服务所在主机的未被占用的任意端口号。
telqos.port=23
```

## 启动程序

在 `/usr/local/snowflake` 目录下执行如下命令：

```shell
sh  bin/snowflake-start.sh
```

可以通过以下行为观察服务的运行效果：

1. 进入服务的 telqos 运维系统并观察。

   在终端输入指令：

   ```shell
   # 将该命令中的 ${telqos.port} 替换为配置的端口号。
   telnet localhost ${telqos.port}
   ```

   观察 telqos 运维系统，您可以输入

   ```shell
   lc
   ```

   观察所有可用的指令。

2. 尝试生成 snowflake id。

   在 telqos 运维系统中输入指令：

   ```shell
   gen -s 10
   ```

   观察服务的输出。

## 停止程序

在 `/usr/local/snowfalke` 目录下执行如下命令：

```shell
sh  bin/snowflake-stop.sh
```

