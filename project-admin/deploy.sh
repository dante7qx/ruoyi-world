#! /bin/bash

# 全局将testapp修改为自己项目的名字
# <项目名> 替换成自己的项目名，和init.sh中保持一致
APP_NAME=testapp*.jar
JAVA_OPTS="-Xms1024m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m"
LOG_HOME=/data/deploy/logs/<项目名>
UPLOAD_PATH=/data/deploy/upload/<项目名>
APIKEY_PATH=/data/deploy/apikey/<项目名>
GREP_APP="testapp.*.jar"
## 启动端口号
SERVER_PORT=9001
## 健康检查的URI
HEALTH_CHECK="health_check"

# 使用说明，用来提示输入参数
usage() {
    echo "Usage: sh deploy.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist() {
    pid=`ps -ef | grep $GREP_APP | grep -v grep | awk '{print $2}' `
    #如果不存在返回1，存在返回0
    if [ -z "${pid}" ]; then
      return 1
    else
      return 0
    fi
}

#启动方法
start() {
   is_exist
   if [ $? -eq "0" ]; then
     echo "${APP_NAME} is already running. pid=${pid}."
   else
     echo "${APP_NAME} begin to start, listening to ${SERVER_PORT}"
     nohup java $JAVA_OPTS \
     	-Dfile.encoding=utf-8 \
     	-Djava.security.egd=file:/dev/./urandom \
     	-jar $APP_NAME \
     	--server.port=${SERVER_PORT} \
     	--spring.redis.database=0 \
     	--ruoyi.profile=$UPLOAD_PATH \
     	--ruoyi.apiKey=$APIKEY_PATH \
     	> /dev/null 2>&1 &
   fi
}

#停止方法
stop() {
   is_exist
   if [ $? -eq "0" ]; then
     kill -9 $pid
   else
     echo "${APP_NAME} is not running"
   fi
}

#输出运行状态
status() {
   is_exist
   if [ $? -eq "0" ]; then
     echo "${APP_NAME} is running. Pid is ${pid}"
   else
     echo "${APP_NAME} is not running."
   fi
}

#重启
restart() {
   stop
   start
}

#健康检查
healthCheck() {
    sleep 2
    SIGNAL=0
    while [ $SIGNAL != 1 ]; do
        RESULT=`curl -I -m 10 -o /dev/null -s -w %{http_code} http://localhost:${SERVER_PORT}/${HEALTH_CHECK}`
        if [ $RESULT = 200 ]; then
            SIGNAL=1
            tail -n 10 ${LOG_HOME}/sys-error.log
        else
            tail -n 20 ${LOG_HOME}/sys-error.log
            sleep 5
        fi
    done
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
   "start")
     start
     ;;
   "stop")
     stop
     ;;
   "status")
     status
     ;;
   "restart")
     restart
     ;;
   "healthCheck")
     healthCheck
     ;;
   *)
     usage
     ;;
esac