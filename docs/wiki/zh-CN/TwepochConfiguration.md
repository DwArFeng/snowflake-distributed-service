# Twepoch Configuration - 起始时间戳配置

## 说明

`TWEPOCH` 是 Snowflake ID 生成算法中的起始时间戳，用于定义 ID 时间戳部分的起算基准。
生成 ID 时，服务会将当前毫秒时间戳减去 `twepoch`，并将得到的时间戳差值写入 ID 的时间戳位段；
解析 ID 时，服务会将 ID 中的时间戳差值加回 `twepoch`，还原出实际的毫秒时间戳。

本文档集中说明 `twepoch.properties` 的配置位置、配置项含义以及必须遵守的风险约束。
配置目录结构可参阅 [Conf Directory](./ConfDirectory.md)，
Snowflake ID 结构与时间窗口说明可参阅 [About Snowflake](./AboutSnowflake.md)。

## 配置位置

发布包中的配置文件位于：

```text
conf/snowflake/twepoch.properties
```

源码资源中对应的配置文件位于：

```text
snowflake-distributed-service-node/snowflake-distributed-service-node-all-he/src/main/resources/com/dwarfeng/sfds/node/all/he/snowflake/twepoch.properties
```

ALL-HE 构型发布时，该资源会被放入发布包的 `conf/snowflake/` 目录下。

## 配置项

`twepoch.properties` 中的配置项如下：

```properties
com.dwarfeng.sfds.snowflake.twepoch=1420041600000
```

`com.dwarfeng.sfds.snowflake.twepoch` 表示 Snowflake ID 的起始时间戳，单位为毫秒。
默认值为 `1420041600000`，对应日期为 2015-01-01；如果未配置此项，程序将使用 `SnowflakeConstants.DEFAULT_TWEPOCH` 定义的默认值。

该配置项直接影响 ID 中的时间戳部分：

- 生成 ID 时，时间戳差值为 `当前毫秒时间戳 - twepoch`。
- 解析 ID 时，实际毫秒时间戳为 `timestampDelta + twepoch`。

由于时间戳差值会被写入 ID 的高位时间戳区域，因此 `twepoch` 会决定 ID 可表达时间窗口的起点。

## 分布式节点一致性要求

所有 Snowflake 节点必须使用完全相同的 `twepoch` 值。

在分布式部署中，`twepoch` 是所有节点共同解释 ID 时间戳部分的基准。
如果不同节点使用不同的 `twepoch`，即使它们在同一真实时间生成 ID，写入 ID 的时间戳差值也会不同；
同一个 ID 在不同节点上解析时，也可能得到不同的实际时间戳。

配置不一致时，将破坏 ID 数值顺序与真实时间顺序之间的对应关系，导致跨节点生成的 ID 无法可靠排序。
在节点标识规划不严谨、节点迁移或配置回退等场景下，还可能放大重复 ID 的风险。

## 修改限制

`twepoch` 只适合在系统正式投入使用前统一确定。

一旦系统已经开始生成实际业务 ID，该配置项就不应再修改。
升级、迁移、扩容或重启节点时，也应保持原有 `twepoch` 不变，并确保所有节点的配置一致。

如果确实需要使用新的起始时间戳，应将其视为新的 ID 域进行规划，避免与已经产生业务数据的旧 ID 混用。

## 风险说明

正式使用后修改 `twepoch` 会改变新 ID 的时间戳计算基准，可能造成以下问题：

- 新旧 ID 的时间关系错乱，无法根据 ID 数值可靠判断生成先后。
- 数据库按 ID 排序时出现异常，影响分页、增量同步、归档等依赖顺序的逻辑。
- 新生成的 ID 可能小于历史 ID，削弱递增 ID 对索引写入性能的优化效果。
- 在多节点配置不一致或节点标识复用时，可能产生重复 ID，导致业务数据冲突。
- 依赖 ID 解析结果的日志、排障、审计和业务统计可能得到错误的时间信息。

因此，生产环境中应将 `twepoch` 视为不可变配置，并在配置管理或部署流程中避免误修改。

## 参阅

- [Conf Directory](./ConfDirectory.md) - 配置目录说明清单。
- [Conf Directory for ALL-HE](./ConfDirectoryAllHe.md) - ALL-HE 构型的配置目录说明。
- [About Snowflake](./AboutSnowflake.md) - Snowflake 原理、ID 结构与时间窗口说明。
