# ChangeLog

## Release_1.8.0_20251027_build_A

### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.7.0.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.7.2_20251012_build_A

### 功能构建

- 可执行制品目录结构优化。
  - 增加 `docs` 目录，以提供项目的文档和指南。

- 优化 `docs/wiki` 目录结构。
  - 将 `docs/wiki/en_US` 目录重命名为 `en-US`，以符合 rfc5646 规范。
  - 将 `docs/wiki/zh_CN` 目录重命名为 `zh-CN`，以符合 rfc5646 规范。
  - 更新 `docs/wiki/README.md` 中的链接指向。
  - 更新 `README.md` 中的链接指向。

- 优化启停脚本注释，以规避潜在的字符集问题。
  - binres/snowflake-start.bat。
  - binres/snowflake-start.sh。
  - binres/snowflake-stop.sh。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Cursor IDE 相关文件的忽略规则。

- 优化 api 模块下的 `logging` 目录结构。
  - 将 `logging/settings-windows.xml` 重命名为 `settings-ref-windows.xml`，以消除文件名的歧义。
  - 更新 `logging/README.md` 中的相关说明。

- 优化 impl 模块下的 `logging` 目录结构。
  - 将 `logging/settings-windows.xml` 重命名为 `settings-ref-windows.xml`，以消除文件名的歧义。
  - 更新 `logging/README.md` 中的相关说明。

- 优化 node 模块下的 `logging` 目录结构。
  - 将 `logging/settings-linux.xml` 重命名为 `settings-ref-linux.xml`，以消除文件名的歧义。
  - 将 `logging/settings-windows.xml` 重命名为 `settings-ref-windows.xml`，以消除文件名的歧义。
  - 更新 `logging/README.md` 中的相关说明。

- 依赖升级。
  - 升级 `zookeeper` 依赖版本为 `3.9.4` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.6.0.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.7.1_20250803_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/ShellScripts.md。
  - docs/wiki/zh_CN/BatchScripts.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.14.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.7.0_20250623_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/CompileBySource.md。

- 优化项目结构。
  - 新建 com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException 异常类，以符合项目结构规范。
  - 过时 com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException 异常类。
  - 调整相关的业务逻辑以及配置类。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.6.4_20250601_build_A

### 功能构建

- 优化部分类中的文档注释以及代码注释。
  - com.dwarfeng.sfds.impl.handler.GenerateHandlerImpl。
  - com.dwarfeng.sfds.sdk.util.SnowflakeConstants。

- Wiki 编写。
  - docs/wiki/zh_CN/AboutSnowflake.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.10.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.6.3_20250510_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/SystemRequirements.md。

### Bug 修复

- 修正错误的 dubbo 应用名称。
  - 修正 `api` 模块中错误的 dubbo 应用名称。
  - 修正 `impl` 模块中错误的 dubbo 应用名称。

### 功能移除

- (无)

---

## Release_1.6.2_20250506_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/QuickStart.md。

- 依赖升级。
  - 升级 `spring-telqos` 依赖版本为 `1.1.13.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.6.1_20250504_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/VersionBlacklist.md。

- 依赖升级。
  - 升级 `netty` 依赖版本为 `4.1.119.Final` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.9.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.12.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.6.0_20250324_build_A

### 功能构建

- 为部分工具类中方法的入口参数增加 `@NotNull` 注解。
  - com.dwarfeng.sfds.impl.service.telqos.CommandUtil。

- 依赖优化。
  - 去除 `com.dwarfeng:spring-telqos-api` 中不必要的排除项。

- 类名优化。
  - SnowFlakeConstants -> SnowflakeConstants。

- 更新 README.md。

- Wiki 编写。
  - 构建 wiki 目录结构。
  - docs/wiki/en_US/Contents.md。
  - docs/wiki/en_US/Introduction.md。
  - docs/wiki/zh_CN/Contents.md。
  - docs/wiki/zh_CN/Introduction.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.8.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.11.b` 以规避漏洞。

### Bug 修复

- 修复配置文件中错误的配置项。
  - `application-context-dubbo.xml`。

### 功能移除

- (无)

---

## Release_1.5.3_20241117_build_A

### 功能构建

- 优化项目启停脚本设置程序的根目录的方式。

- 优化 `node` 模块部分服务启停脚本的注释。
  - binres/snowflake-start.bat。
  - binres/snowflake-start.sh。

- 优化项目的启停脚本，以规避潜在的路径问题。
  - binres/statistics-stop.sh。
  - binres/statistics-start.sh。

- 部分 dubbo 消费者服务注册配置添加 `check="false"` 属性。
  - generateService。
  - longIdService。

- 依赖升级。
  - 增加依赖 `spring` 以应用其新功能，版本为 `5.3.39`。
  - 增加依赖 `netty` 以应用其新功能，版本为 `4.1.115.Final`。
  - 增加依赖 `zookeeper` 以应用其新功能，版本为 `3.9.3`。
  - 增加依赖 `subgrade` 以应用其新功能，版本为 `1.5.7.a`。
  - 增加依赖 `spring-terminator` 以应用其新功能，版本为 `1.0.14.a`。
  - 增加依赖 `spring-telqos` 以应用其新功能，版本为 `1.1.11.a`。

### Bug 修复

- 修复装配文件中的缺失的配置。

- 修正 dubbo 配置中不合适的应用名称。
  - `snowflake-distributed-service-api` 模块 `application-context-dubbo.xml`。

### 功能移除

- (无)

---

## Release_1.5.2_20240731_build_A

### 功能构建

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.log4j2.Log4j2Command。

- 优化项目启停脚本设置程序的根目录的方式。

- 优化启停脚本的目录结构。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。
  - 优化日志配置结构，提供 `confext/logging-settings.xml` 配置文件，以供外部功能自定义日志配置。
  - 优化启动脚本，使服务支持新的日志配置结构。
  - 优化 `assembly.xml`，使项目打包时输出新的日志配置结构。
  - 优化 `confext/README.md`，添加新的日志配置结构的相关说明。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.37` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.108.Final` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.5.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.13.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.9.a` 以规避漏洞。

- 优化 `README.md` 文件中的内容。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.5.1_20231227_build_A

### 功能构建

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.31` 并解决兼容性问题，以应用其新功能。
  - 升级 `netty` 依赖版本为 `4.1.104.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.7.2` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.4.7.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.12.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.8.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.5.0_20230909_build_A

### 功能构建

- 优化项目结构。
  - 将 snowflake 算法的实现从 service 下放到 handler。
  - 过时 com.dwarfeng.sfds.stack.service.LongIdService 及其相关引用。
  - 新增 com.dwarfeng.sfds.stack.service.GenerateService。
  - 过时 com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher。
  - 新增 com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongGenerator。
  - 新增 com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongIdKeyGenerator。

- 将项目中的 aop 代理模式更改为基于类的代理模式。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `*.properties` 文件的格式。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.4.5.a` 并解决兼容性问题，以应用其新功能。

### Bug 修复

- 修复 impl 模块 dubbo 相关的配置错误。
  - spring/application-context-dubbo.xml。
  - dubbo/connection.properties。

### 功能移除

- (无)

---

## Release_1.4.12_20230821_build_A

### 功能构建

- dubbo 微服务增加分组配置。

- 增加依赖。
  - 增加依赖 `javassist` 以规避漏洞，版本为 `3.23.2-GA`。

- 依赖升级。
  - 升级 `dubbo` 依赖版本为 `2.7.22` 以规避漏洞。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.17.2` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.7.a` 以应用其新功能。

### Bug 修复

- 补充 `assembly.xml` 中缺失的配置。

- 补充 `.gitignore` 中缺失的配置。

### 功能移除

- (无)

---

## Release_1.4.11_20230420_build_A

### 功能构建

- 优化项目结构，增加项目目录。
  - `./confext/`。
  - `./libext/`。
  - `./opt/`。
  - `./optext/`。

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `snakeyaml` 依赖版本为 `2.0` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.21` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.86.Final` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.6.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.10_20221120_build_A

### 功能构建

- 优化 `README.md` 的格式。

- 增加依赖。
  - 增加依赖 `guava` 以规避漏洞，版本为 `31.1-jre`。
  - 增加依赖 `gson` 以规避漏洞，版本为 `2.8.9`。
  - 增加依赖 `snakeyaml` 以规避漏洞，版本为 `1.33`。

- 依赖升级。
  - 升级 `dubbo` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.5.7` 以规避漏洞。
  - 升级 `curator` 依赖版本为 `4.3.0` 以规避漏洞。
  - 升级 `aspectj` 依赖版本为 `1.9.7` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.2.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.14.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.10.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.5.a` 以规避漏洞。

### Bug 修复

- 修正所有 xml 配置文件的格式错误。

### 功能移除

- 删除不需要的依赖。
  - 删除 `el` 依赖。
  - 删除 `zkclient` 依赖。
  - 删除 `dozer` 依赖。

---

## Release_1.4.9_20220912_build_A

### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.2.10.a` 以规避漏洞。

- 优化 telqos 指令。
  - com.dwarfeng.sfds.impl.service.telqos.GenCommand。

### Bug 修复

- (无)

### 功能移除

- 删除 telqos 指令。
  - com.dwarfeng.sfds.impl.service.telqos.GenCsvCommand。

---

## Release_1.4.8_20220905_build_A

### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

- 依赖升级。
  - 升级 `spring-terminator` 依赖版本为 `1.0.9.a`。
  - 升级 `dutil` 依赖版本为 `beta-0.3.1.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.9.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.4.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- 移除 `tomcat7-maven-plugin` 的版本声明。
- 移除 `joda-time` 的版本声明。

---

## Release_1.4.7_20220701_build_A

### 功能构建

- 增加 QOS 运维指令。
  - com.dwarfeng.sfds.impl.service.telqos.GenCsvCommand。
  - com.dwarfeng.sfds.impl.service.telqos.GenCommand。

- 增加 `LongIdService` 的批量生成方法。
  - com.dwarfeng.sfds.stack.service.LongIdService.nextLongId(int)。
  - com.dwarfeng.sfds.stack.service.LongIdService.nextLongIdKey(int)。

- 升级 `subgrade` 依赖为 `1.2.8.a`，并更新相对应的 API。
  - com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.6_20220607_build_A

### 功能构建

- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.20` 以规避漏洞。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.2.2.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.7.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.3.a` 以规避漏洞。

- 将工程中的 `Spring Bean` 注册方式尽可能地由 `@Autowired` 变更为构造器注入。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.5_20220523_build_A

### 功能构建

- 优化项目的依赖。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.4_20220521_build_A

### 功能构建

- 升级 `spring` 依赖版本为 `5.3.19` 以规避漏洞。

- 升级 `netty` 依赖版本为 `4.1.77.Final` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.3_20220303_build_A

### 功能构建

- 升级 `subgrade` 依赖版本为 `1.2.4.a`。
- 优化程序的启停脚本。
- 更新 README.md。

### Bug 修复

- 修正调用 `ServiceExceptionCodes.setExceptionCodeOffset` 后，报警代码不更新的 bug。

### 功能移除

- (无)

---

## Release_1.4.2_20220115_build_A

### 功能构建

- 升级 `spring` 依赖版本为 `5.3.14`。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.1_20220114_build_A

### 功能构建

- 优化程序的配置结构。

- 为 `dubbo` 增加超时时间的配置选项。

- 升级 `log4j2` 依赖版本为 `2.17.1` 以规避漏洞。
  - `CVE-2021-44228`。
  - `CVE-2021-45105`。

- 升级 `dubbo` 依赖版本为 `2.7.15`。

- 删除部分项目运行中不需要的依赖。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.0_20210219_build_A

### 功能构建

- 依赖更新。
- 引入 spring-telqos 组件。
- 规范配置文件的目录名称。

### Bug 修复

- 修复部分配置文件的单词拼写错误。
- 修复bat启动脚本对于带空格的路径表现异常的bug。

### 功能移除

- (无)

---

## Release_1.3.0_20200426_build_A

### 功能构建

- 更新工程的打包方式为最新规范。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.5_20200426_build_A

### 功能构建

- 升级subgrade依赖至1.0.0.a。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.4_20200327_build_A

### 功能构建

- 使用spring-terminator维护程序的启动和停止。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.3_20200326_build_B

### 功能构建

- 更新subgrade依赖至beta-0.3.2.b。
- 更新dutil依赖至beta-0.2.1.a。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.3_20200302_build_A

### 功能构建

- (无)

### Bug 修复

- 修改pom.xml配置，避免项目install时的报警。

### 功能移除

- (无)

---

## Release_1.2.2_20200301_build_A

### 功能构建

- 更改启动脚本的文件换行风格为unix。

### Bug 修复

- 去除掉无用的依赖，压缩项目体积。

### 功能移除

- (无)

---

## Release_1.2.1_20200225_build_A

### 功能构建

- 升级subgrade依赖版本为beta-0.2.5.a。

### Bug 修复

- 修正冲突的依赖并去掉无用的依赖。

### 功能移除

- (无)

---

## v1.2.0.a

### 功能构建

- ExceptionCode偏移量可配置化。
- 升级 subgrade 工程的依赖。
- 优化 log4j2.xml 日志配置。
- 更改该项目在dubbo中的应用名称为snowflake-node。

### Bug 修复

- 修正pom.xml中的坐标引入错误。
- 清除pom.xml中多余的坐标。

### 功能移除

- (无)

---

## v1.1.0.a

### 功能构建

- 优化程序结构，去掉api中无意义的rpc类。
- 以更合理的形式组织测试。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## v1.0.1.b

### 功能构建

- 升级subgrade依赖为beta-0.0.2.a

### Bug 修复

- (无)

### 功能移除

- (无)

---

## v1.0.1.a

### 功能构建

- 更新项目依赖。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## v1.0.0.a

### 功能构建

- 将该项目整合至subgrade中。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## v0.0.1.b

### 功能构建

- 删除api中多余的配置文件。
- 建立CHANGELOG.md，并且项目打包时装配该文件至根目录。
- 修改README.md中的部分错别字和语法错误。

### Bug 修复

- (无)

### 功能移除

- (无)
