# snowflake-distributed-service - SnowFlake分布式服务

SnowFlake分布式服务是一个轻量级的全局ID分发服务，全局ID的生成算法使用了SnowFlake算法。
该服务可以提供大量的全局唯一的ID，ID的大小基于时间，新生成的ID要大于旧的ID，这保证了ID作为主键在数据库存储时的时间有效性。
该服务使用 dubbo rpc 分布式框架搭建，可以轻松的进行伸缩，部署新服务时仅仅需要改变配置即可。

### 项目的主要模块
模块名称|模块路径|说明
:---|:---|:---
**node**|**snowflake-distributed-service-node**|**可部署的服务节点**
**api**|**snowflake-distributed-service-api**|**可以通过引入该模块快速的调用此服务**
stack|snowflake-distributed-service-stack|接口定义
impl|snowflake-distributed-service-impl|项目实现
sdk|snowflake-distributed-service-sdk|开发工具

### 服务的使用
1. 下载该项目，推荐使用SSH

    `git clone [本项目名称]`

2. 使用maven安装该项目的依赖项目

    该项目引用同作者的其它项目，如dutil，不过这些项目并不在中心仓库中，如果提示找不到这些引用，请在github或者gitee查找同作者的项目，并使用以下指令。
    
    `mvn clean install`
    
    该项目同作者的依赖项目：
    * [[github] dutil-作者大学时代开始编写的Java实用工具集合](https://github.com/DwArFeng/dutil) 或者 [[gitee]  dutil-作者大学时代开始编写的Java实用工具集合](https://gitee.com/DwArFeng/dutil)
    
3. 使用maven安装本项目(为了api能够使用，请安装，而不是打包)

    ```mvn clean install```
    
4. 找到目录 `snowflake-distributed-service-node/target/snowflake-distributed-service-node-alpha-[项目版本]-release.tar.gz` 并解压

    ```shell script
    tar -zxcf snowflake-distributed-service-node-[项目版本]-release.tar.gz
    ```
   
5. 修改配置文件

    conf/dubbo/connection.properties
    ```
    # Zookeeper地址
    dubbo.zookeeper.address=zookeeper://192.168.XXX.XXX:2181
    # dubbo 提供者端口
    dubbo.port=20000
    # dubbo 提供者qos端口
    dubbo.qos.port=21000
    # dubbo 提供者主机名称
    dubbo.host=192.168.154.1
    ```
    conf/snow-flake/device.properties
    ```
    # Worker ID，最大为31，新的节点序列号向下递减，最少到0。
    snowflake.workder_id=31
    # Datacenter ID，最大为31，新的节点序列号向下递减，最少到0。
    snowflake.datacenter_id=31
    ```
   
6. 修改 .sh 文件

    bin/snowflake-start.sh
    ```shell script
    #!/bin/sh
    # 程序的根目录
    basedir=/usr/share/snowflake
    # 日志的根目录
    logdir=/var/log/snowflake
    # 可执行的jar名称(自动配置好，不需要修改)
    executable_jar_name=snowflake-distributed-service-node-[项目版本].jar
    
    cd $basedir || exit
    nohup /bin/java -Dlog.dir=$logdir -jar $basedir/lib/$executable_jar_name >/dev/null 2>&1 &
    echo $! >$basedir/snowflake.pid
    ```
    bin/snowflake-stop.sh
    ```shell script
    #!/bin/bash
    # 程序的根目录
    basedir=/usr/share/snowflake
    
    PID=$(cat $basedir/snowflake.pid)
    kill "$PID"
    ```
   
7. 启动
    ```shell script
    cd [项目所在目录]
    sh ./bin/snowflake-start.sh
    ```
   
 8. Enjoy it!
 
 ### 服务的调用
 
 1. 参照 ```snowflake-distributed-service-api``` 项目
 
    src/test/java/com/dwarfeng/sfds/api/impl/GuidApiImplTest.java
    ```java
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(locations = "classpath:spring/application-context*.xml")
    public class GuidApiImplTest {
    
        @Autowired
        private GuidApi guidApi;
    
        @Test
        public void nextGuid() throws ServiceException {
            for (int i = 0; i < 100; i++) {
                //CT.trace是作者的dutil项目中的的方法。
                //用于在控制台中输出系统时间与输出文本组成的格式化字符。
                CT.trace(guidApi.nextGuid());
            }
        }
    }
    ```
