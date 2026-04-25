# Telqos Commands - Telqos 命令

## 命令列表

Snowflake Distributed Service 提供的 Telqos 命令如下所示：

| 命令                         | 说明                | 可用版本    |
|----------------------------|-------------------|---------|
| [lc](#lc-命令)               | 列出指令              | 1.4.0.a |
| [man](#man-命令)             | 显示指令的详细信息         | 1.4.0.a |
| [memory](#memory-命令)       | 内存监视              | 1.4.0.a |
| [shutdown](#shutdown-命令)   | 关闭/重启程序           | 1.4.0.a |
| [quit](#quit-命令)           | 退出                | 1.4.0.a |
| [dubbo](#dubbo-命令)         | 分布式服务上线/下线        | 1.4.0.a |
| [log4j2](#log4j2-命令)       | Log4j2 运维操作       | 1.5.2.a |
| [uptime](#uptime-命令)       | 系统运行时间            | 1.8.2.a |
| [jmxremote](#jmxremote-命令) | JMX 远程管理操作        | 1.8.2.a |
| [gen](#gen-命令)             | 生成 ID 并导出到 CSV 操作 | 1.4.7.a |
| [res](#res-命令)             | 解析 ID 操作          | 1.8.1.a |

鉴于所有指令都可以实际操作验证，因此本文对于较长的输出将予以省略，省略部分将会使用 `etc...` 进行标注。

## lc 命令

列出 Telqos 支持的所有命令。

### 语法

```text
usage: lc [-p prefix|--prefix prefix]
列出指令
 -p,--prefix <prefix>   列出包含指定前缀的命令
```

### 示例

```text
lc -p j
1   jmxremote   JMX 远程管理操作
-----------------------------
共 1 条
OK
```

## man 命令

显示指定命令的详细信息。

### 语法

```text
usage: man [command]
显示指令的详细信息
```

### 示例

```text
man gen
usage: gen -generate [-s size] [-f file-path]
生成 ID 并导出到 CSV 操作
 -f <arg>    生成的 CSV 的路径
 -generate   生成 ID
 -s <arg>    生成数量
```

## memory 命令

内存监视。

### 语法

```text
usage: memory [-u unit]
内存监视
 -u <arg>   显示单位
```

`-u` 参数支持的单位为 `EiB`、`PiB`、`TiB`、`GiB`、`MiB`、`KiB`、`B`。

### 示例

```text
memory -u mib
JVM 最大内存: 7264.00MiB
JVM 分配内存: 509.50MiB
JVM 可用内存: 370.79MiB
OK
```

## shutdown 命令

关闭/重启程序。

在本项目中，重启程序功能不可用，只能使用关闭程序功能。

### 语法

```text
usage: shutdown [-s/-r] [-e exit-code] [-c comment]
关闭/重启程序
 -c <comment>     备注
 -e <exit-code>   退出代码
 -r               重启程序
 -s               退出程序
```

增加 `-c` 参数，可以在关闭程序时添加备注信息，备注信息将会被记录到日志中。

`-r` 参数不可用。

### 示例

```text
shutdown -s -e 0 -c 正常停机
服务将会关闭，您可能需要登录远程主机才能重新启动该服务，是否继续? Y/N
Y
已确认请求，服务即将关闭...
服务端主动与您中断连接
再见!
```

## quit 命令

退出 Telqos 运维平台。

### 语法

```text
usage: quit
退出
```

### 示例

```text
quit
Bye
服务端主动与您中断连接
再见!


遗失对主机的连接。
```

## dubbo 命令

分布式服务查询/上线/下线。

### 语法

```text
usage: dubbo -online [service-name]
dubbo -offline [service-name]
dubbo -ls
分布式服务上线/下线
 -ls              列出服务
 -offline <arg>   下线服务
 -online <arg>    上线服务
```

`[service-name]` 参数为正则表达式，只有服务的全名称匹配正则表达式时，才会被上线/下线。

如果想要上线/下线名称中包含 `LongIdService` 的服务，则可以使用 `.*LongIdService.*` 作为正则表达式。

如果不指定 `[service-name]` 参数，则使所有服务上线/下线。

### 示例

#### 列出服务

```text
dubbo -ls
As Provider side:
+--------------------------------------------------------------+---+
|                     Provider Service Name                    |PUB|
+--------------------------------------------------------------+---+
| formal.general/com.dwarfeng.sfds.stack.service.ResolveService| Y |
+--------------------------------------------------------------+---+
etc...
As Consumer side:
+---------------------+---+
|Consumer Service Name|NUM|
+---------------------+---+
OK
```

#### 上线服务

```text
dubbo -online .*LongIdService.*
OK
```

#### 下线服务

```text
dubbo -offline .*LongIdService.*
OK
```

## log4j2 命令

Log4j2 运维操作。

### 语法

```text
usage: log4j2 -reconfigure
Log4j2 命令
 -reconfigure   重新配置 Log4j2
```

### 示例

```text
log4j2 -reconfigure
Log4j2 已重新配置!
OK
```

## uptime 命令

查看系统运行时间。

### 语法

```text
usage: uptime
系统运行时间
```

### 示例

```text
uptime
01:19:51 up 0 days, 00:03
OK
```

## jmxremote 命令

JMX 远程管理操作。

### 语法

```text
usage: jmxremote -start [-p port]
jmxremote -stop
jmxremote -status
JMX 远程管理操作
 -p <arg>   JMX 远程管理端口号
 -start     启动 JMX 远程管理
 -status    查看 JMX 远程管理状态
 -stop      停止 JMX 远程管理
```

`jmxremote -start` 未指定 `-p` 时，默认端口号为 `9999`。

如果通过 JVM 系统属性 `com.sun.management.jmxremote.port` 预先启用了 JMX 远程管理，
该命令会进入“只读提示模式”，不会重复执行启动或停止操作。

### 示例

#### 启动 JMX 远程管理

```text
jmxremote -start -p 23000
JMX 远程管理启动成功
  端口: 23000
  服务地址: service:jmx:rmi:///jndi/rmi://:23000/jmxrmi
OK
```

#### 查看 JMX 远程管理状态

```text
jmxremote -status
JMX 远程管理状态: 运行中
  端口: 23000
  服务地址: service:jmx:rmi:///jndi/rmi://:23000/jmxrmi
OK
```

#### 停止 JMX 远程管理

```text
jmxremote -stop
JMX 远程管理已停止
OK
```

## gen 命令

生成 ID 并导出到 CSV 操作。

### 语法

```text
usage: gen -generate [-s size] [-f file-path]
生成 ID 并导出到 CSV 操作
 -f <arg>    生成的 CSV 的路径
 -generate   生成 ID
 -s <arg>    生成数量
```

该命令必须显式指定 `-generate` 选项。

当未指定 `-s` 时，会进入交互模式，提示输入要生成的 ID 数量。

当指定 `-f` 时，结果将导出到 CSV 文件中；如果指定路径为目录，则默认文件名为 `export.csv`。

### 示例

#### 直接输出到屏幕

```text
gen -generate -s 5
0: 1497770090756567040
1: 1497770090756567041
2: 1497770090756567042
3: 1497770090756567043
4: 1497770090756567044
OK
```

#### 导出到 CSV 文件

```text
gen -generate -s 3 -f my-export.csv
CSV 文件已导出至 D:\project-root\my-export.csv
OK
```

## res 命令

解析 ID 操作。

### 语法

```text
usage: res -resolve [-id id]
解析 ID 操作
 -id <arg>   待解析的 ID
 -resolve    解析 ID
```

该命令必须显式指定 `-resolve` 选项。

当未指定 `-id` 时，会进入交互模式，提示输入待解析的 ID。

### 示例

```text
res -resolve -id 1497770090756567044
解析结果:
  originalId: 1497770090756567044
  sequence: 4
  workerId: 31
  datacenterId: 31
  timestampDelta: 357096216858
  timestamp: 1777137816858
  twepoch: 1420041600000
OK
```
