#!/bin/sh
# 程序的根目录
basedir="/usr/local/snowflake"

PID=$(cat "$basedir/snowflake.pid")
kill "$PID"
