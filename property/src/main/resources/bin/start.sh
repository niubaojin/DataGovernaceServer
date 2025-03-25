#!/bin/bash

# 当前路径
base_dir=$(cd `dirname $0`;pwd)
# 程序名称
server_name="property"
# jar包名称
server_jar="property*.jar"
# 日志目录路径
server_log_location="logs/"

cd ${base_dir}

pid=$(ps -ef | grep ${base_dir}/${server_name} | grep -v grep| awk '{print $2}')
if [ "${pid}" != "" ]; then
        echo "warn: ${server_name} is runing"
        exit 1
fi

#==========================================================================================
# JVM Configuration
# -Xmx256m:设置JVM最大可用内存为256m,根据项目实际情况而定，建议最小和最大设置成一样。
# -Xms256m:设置JVM初始内存。此值可以设置与-Xmx相同,以避免每次垃圾回收完成后JVM重新分配内存
# -Xmn512m:设置年轻代大小为512m。整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小。
#          持久代一般固定大小为64m,所以增大年轻代,将会减小年老代大小。此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8
# -XX:MetaspaceSize=64m:存储class的内存大小,该值越大触发Metaspace GC的时机就越晚
# -XX:MaxMetaspaceSize=320m:限制Metaspace增长的上限，防止因为某些情况导致Metaspace无限的使用本地内存，影响到其他程序
# -XX:-OmitStackTraceInFastThrow:解决重复异常不打印堆栈信息问题
#==========================================================================================
JAVA_OPT="-server -Xms3024m -Xmx3024m -Xmn3284m -XX:MetaspaceSize=364m -XX:MaxMetaspaceSize=556m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"

nohup $JAVA_HOME/bin/java ${JAVA_OPT} -jar ${base_dir}/${server_jar} >/dev/null 2>&1 &

echo ${server_name} is starting ......

sleep 3

tail -f ${server_log_location}log_info.log
