#!/bin/bash

# 当前路径
base_dir=$(cd `dirname $0`;pwd)

# service_name以进程中的唯一关键字命名，或者以jar包名命名
server_name="property"

cd ${base_dir}

# 进程号
pids=$(ps -ef | grep "${base_dir}/${server_name}" | grep -v grep | awk '{ print $2 }' | sed ':label;N;s/\n/ /;b label')

#某些服务会启动多个进程,则一次全部停止，同样作用于一个进程的服务
if [[ -z "$pids" ]]
then
    echo ${server_name} is already stopped
else
    for i in $pids
    do
      kill -9 $pids
      echo -e "$server_name is\033[1;31m STOP \033[0m"
    done
fi
