#! /bin/bash

#获取目录下所有的jar文件，逐个进行关闭
CUR_PATH=$(cd `dirname $0`;pwd)
VERSION=$1
APPLICATION=reconciliation
cd ${CUR_PATH}

#指定版本的关闭
if [ "${VERSION}" != "" ];then
  APPLICATION_JAR="${APPLICATION}-${VERSION}.jar"
  PID=$(jcmd | grep $file | awk '{print $1}')
  echo "正在关闭程序${APPLICATION_JAR}"
  kill -9 ${PID}
  exit
fi

#循环，将jar包逐个kill
for file in `ls -t *.jar`
do
  if [ -f $file ];
  then
    PID=$(jcmd | grep $file | awk '{print $1}')
    JAR_NAME=$(jcmd | grep $file | awk '{print $2}')
    if [ "${PID}" != "" ];
    then
      echo "正在关闭程序${JAR_NAME}"
      kill -9 ${PID}
    fi
  fi
done
