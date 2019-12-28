#!/bin/sh
# 程序的根目录
basedir=/usr/share/snowflake
# 日志的根目录
logdir=/var/log/snowflake
# 可执行的jar名称
executable_jar_name=${project.name}-${project.version}.jar

cd $basedir || exit
nohup /bin/java -Dlog.dir=$logdir -jar $basedir/lib/$executable_jar_name >/dev/null 2>&1 &
echo $! >$basedir/snowflake.pid
