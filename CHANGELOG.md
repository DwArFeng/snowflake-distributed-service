# ChangeLog

## Release_2.0.0_20260505_build_A

### 功能构建

- 优化项目的异常处理机制。
  - `snowflake-distributed-service-sdk` 子模块新增 `ServiceExceptionHelper` 工具类，统一维护项目自身的异常映射关系。
  - `snowflake-distributed-service-impl` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。
  - `snowflake-distributed-service-node` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。

- 依赖升级。
  - 升级 `spring-telqos` 依赖版本为 `2.0.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring-terminator` 依赖版本为 `2.0.0.a` 并解决兼容性问题，以应用其新功能。

- 优化文件格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- 移除 `snowflake-distributed-service-api` 子模块不需要的配置文件。
  - src/test/resources/spring/application-context-task.xml。
  - src/test/resources/snowflake/background.properties。
  - src/test/resources/snowflake/device.properties。
  - src/test/resources/snowflake/exception.properties。
  - src/test/resources/snowflake/twepoch.properties。

---

## 更早的版本

[View all changelogs](./changelogs)
