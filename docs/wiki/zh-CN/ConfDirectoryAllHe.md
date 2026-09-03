# Conf Directory for ALL-HE - ALL-HE 构型的配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
│
├─dubbo
│      connection.properties
│
├─logging
│      README.md
│      settings.xml
│      settings-ref-linux.xml
│      settings-ref-windows.xml
│
├─snowflake
│      background.properties
│      device.properties
│      exception.properties
│      twepoch.properties
│
└─telqos
        connection.properties
```

鉴于大部分配置文件的配置项中都有详细的注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项，
省略的部分将会使用 `etc...` 进行标注。

## dubbo 目录

| 文件名                | 说明               |
|-----------------------|--------------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件。

```properties
com.dwarfeng.sfds.dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
com.dwarfeng.sfds.dubbo.registry.zookeeper.timeout=3000
com.dwarfeng.sfds.dubbo.protocol.dubbo.port=20000
com.dwarfeng.sfds.dubbo.protocol.dubbo.host=your-host-here
com.dwarfeng.sfds.dubbo.provider.group=
```

其中，`com.dwarfeng.sfds.dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`com.dwarfeng.sfds.dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 Snowflake 实例，那么需要为每个实例配置不同的 `com.dwarfeng.sfds.dubbo.protocol.dubbo.port`。

如果您在本机上部署了多个项目，每个项目中都使用了 Snowflake，
那么需要为每个项目配置不同的 `com.dwarfeng.sfds.dubbo.provider.group`，以避免微服务错误的调用。

## logging 目录

| 文件名                   | 说明                                 |
|--------------------------|--------------------------------------|
| README.md                | 说明文件                             |
| settings.xml             | 日志配置的配置文件                   |
| settings-ref-linux.xml   | Linux 系统中日志配置的配置参考文件   |
| settings-ref-windows.xml | Windows 系统中日志配置的配置参考文件 |

### settings.xml

日志配置及其参考文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!-- etc... -->
    </Appenders>

    <Loggers>
        <!-- etc... -->
    </Loggers>
</Configuration>
```

需要注意的是，日志配置 **必须** 定义在 `settings.xml` 中才能生效，所有的 `settings-ref-xxx.xml` 都是参考文件，
在这些文件中进行任何配置的修改 **均不会生效**。

常用的做法是，针对不同的操作系统，将参考文件中的内容直接复制到 `settings.xml` 中，随后对 `settings.xml` 中的内容进行修改。

- 如果服务运行一天产生的日志超过了配置上限，可上调 `rolling_file.rollover.max` 参数。
- 如果存在等保需求，日志至少需要保留 6 个月，需要调整 `rolling_file.rollover.delete_age` 参数至 `200D`。

## snowflake 目录

| 文件名                | 说明                                                    |
|-----------------------|---------------------------------------------------------|
| background.properties | 后台服务配置文件，包括线程池的线程数及其它              |
| device.properties     | Snowflake 设备配置文件，包括 Worker ID 和 Datacenter ID |
| exception.properties  | ServiceException 的异常代码的偏移量配置                 |
| twepoch.properties    | Snowflake ID 生成算法的起始时间戳（TWEPOCH）配置        |

### background.properties

后台服务配置文件，包括线程池的线程数及其它。

```properties
# 任务执行器的线程池数量范围。
com.dwarfeng.sfds.executor.pool_size=50-75
# 任务执行器的队列容量。
com.dwarfeng.sfds.executor.queue_capacity=100
# 任务执行器的保活时间（秒）。
com.dwarfeng.sfds.executor.keep_alive=120
```

该配置文件用于配置 Snowflake 分布式服务中的后台任务执行器，包括线程池的大小、队列容量和保活时间等参数。

### device.properties

Snowflake 设备配置文件，用于配置分布式 ID 生成器的设备参数。

```properties
# Worker ID，最大为 31，新的节点序列号向下递减，最少到 0。
com.dwarfeng.sfds.snowflake.worker_id=31
# Datacenter ID，最大为 31，新的节点序列号向下递减，最少到 0。
com.dwarfeng.sfds.snowflake.datacenter_id=31
```

该配置文件用于配置 Snowflake 分布式 ID 生成器的设备参数。在分布式环境中，每个节点都需要有唯一的 Worker ID 和 Datacenter ID
来确保生成的 ID 的唯一性。

如果您需要在本机启动多个 Snowflake 实例，那么需要为每个实例配置不同的 `com.dwarfeng.sfds.snowflake.worker_id` 和
`com.dwarfeng.sfds.snowflake.datacenter_id`。

### exception.properties

ServiceException 的异常代码的偏移量配置。

```properties
# snowflake 自身的异常代号偏移量。
com.dwarfeng.sfds.snowflake.exception_code_offset=1500
# snowflake 工程中 subgrade 的异常代号偏移量。
com.dwarfeng.sfds.snowflake.exception_code_offset.subgrade=0
# snowflake 工程中 spring-telqos 的异常代号偏移量。
com.dwarfeng.sfds.snowflake.exception_code_offset.spring_telqos=3000
# snowflake 工程中 spring-terminator 的异常代号偏移量。
com.dwarfeng.sfds.snowflake.exception_code_offset.spring_terminator=4000
```

Subgrade 框架中，会将微服务抛出的异常映射为 `ServiceException`，每个 `ServiceException` 都有一个异常代码，
用于标识异常的类型。

如果您的项目中使用了多个基于 Subgrade 框架的微服务，那么，您需要为每个微服务配置一个异常代码偏移量，
以免不同的微服务生成异常代码相同的 `ServiceException`。

其中，`com.dwarfeng.sfds.snowflake.exception_code_offset.spring_telqos` 用于 spring-telqos 模块，
`com.dwarfeng.sfds.snowflake.exception_code_offset.spring_terminator` 用于 spring-terminator 模块，
以避免 Snowflake 工程内各模块的异常代码相互冲突。若存在多微服务部署场景，各微服务的各模块偏移量均需保持唯一。

### twepoch.properties

Snowflake ID 生成算法的起始时间戳（TWEPOCH）配置文件，用于定义 ID 中时间戳部分的起算基准（毫秒）。

分布式节点一致性： 此配置项在整个分布式系统的所有节点上必须完全一致。若不同节点使用了不同的 TWEPOCH 值，
将导致生成的 ID 无法正确排序，甚至出现 ID 冲突的风险。

修改时机限制： 此配置项只允许在系统正式投入使用之前进行修改。一旦系统开始正式使用并生成实际的业务数据，
此配置项就永远不能再更改。

违规后果： 若在系统正式使用后更改此配置项，将导致以下灾难性后果：

- 新生成的 ID 与旧 ID 的时间戳计算基准不一致，导致 ID 无法按时间正确排序。
- 可能出现新生成的 ID 小于旧 ID 的情况，破坏数据库索引性能。
- 在不同节点配置不一致时，可能产生重复的 ID，导致业务数据冲突。
- 依赖时间顺序的业务逻辑将出现严重错误。
- 可能导致已存储数据的 ID 与新生成 ID 之间的时间关系完全错乱。

配置建议：

- 建议在生产环境部署前，统一规划并确定此值。
- 建议在配置管理工具中锁定此配置项，防止误修改。
- 建议在部署文档中明确标注此配置项的不可修改性。

```properties
# 开始时间戳（TWEPOCH）配置
# 此配置项定义了 Snowflake ID 生成算法的起始时间戳（毫秒），用于计算生成 ID 中的时间戳部分。
#
# 关键约束和警告：
# 1. 分布式节点一致性要求：此配置项在整个分布式系统的所有节点上必须完全一致。
# 2. 配置修改时机限制：此配置项只允许在系统正式投入使用之前进行修改。
# 3. 违反规则的严重后果：新旧 ID 时间戳基准不一致、ID 无法排序、可能产生重复 ID 等。
# 4. 配置建议：生产部署前统一规划；在配置管理工具中锁定；在部署文档中标注不可修改性。
#
# 如果未配置此项，将使用 SnowflakeConstants.DEFAULT_TWEPOCH 的默认值。
com.dwarfeng.sfds.snowflake.twepoch=1420041600000
```

默认值为 `1420041600000`（对应 2015-01-01）。若未配置此项，将使用 `SnowflakeConstants.DEFAULT_TWEPOCH` 的默认值。
所有分布式节点必须配置相同的 TWEPOCH 值。

## telqos 目录

| 文件名                | 说明     |
|-----------------------|----------|
| connection.properties | 连接配置 |

### connection.properties

Telqos 连接配置文件。

```properties
# Telnet 端口。
com.dwarfeng.sfds.telqos.port=23
# 字符集。
com.dwarfeng.sfds.telqos.charset=UTF-8
# 白名单表达式。
com.dwarfeng.sfds.telqos.whitelist_regex=
# 黑名单表达式。
com.dwarfeng.sfds.telqos.blacklist_regex=
```

如果您的项目中有多个包含 Telqos 模块的服务，您应该修改 `com.dwarfeng.sfds.telqos.port` 的值，以避免端口冲突。

请根据操作系统的默认字符集，修改 `com.dwarfeng.sfds.telqos.charset` 的值，以避免乱码。
一般情况下，Windows 系统的默认字符集为 `GBK`，Linux 系统的默认字符集为 `UTF-8`。

如果您希望限制 Telqos 的使用范围，
您可以修改 `com.dwarfeng.sfds.telqos.whitelist_regex` 和 `com.dwarfeng.sfds.telqos.blacklist_regex` 的值。
