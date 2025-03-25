#! /bin/bash

export JAVA_BIN=$JAVA_HOME/bin
export JAVA_LIB=$JAVA_HOME/lib
export CLASSPATH=.:$JAVA_LIB/tools.jar:$JAVA_LIB/dt.jar
export PATH=$JAVA_BIN:$PATH

CUR_PATH=$(cd `dirname $0`;pwd)
APPLICATION=reconciliation
VERSION=$1

cd ${CUR_PATH}
#如果指定了后缀版本名，则直接启动
if [ "${VERSION}" != "" ];then
  echo "将启动jar文件： ${APPLICATION}-${VERSION}.jar "
  APPLICATION_JAR="${APPLICATION}-${VERSION}.jar"
  echo $APPLICATION_JAR
  nohup $JAVA_HOME/bin/java -jar $APPLICATION_JAR > /dev/null 2>&1 &
  exit
fi

#找到最新的jar包
for file in `ls -t *.jar`
do
  if [ -f $file ];
  then
    echo "将启动jar文件：${file} "
    nohup $JAVA_HOME/bin/java -jar $file >/dev/null 2>&1 &
    exit
  fi
done
