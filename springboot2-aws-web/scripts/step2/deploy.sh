#!/bin/bash
#REPOSITORY git clone, REPOSITORY에 jar 파일 이동 및 실행

REPOSITORY=/home/ec2-user/app/step2
PROJECT_ROOT=book
PROJECT_NAME=springboot2-aws-web

echo "> build 파일복사"

cp ${REPOSITORY}/zip/*.jar ${REPOSITORY}/

echo "> confirm the running program pid"

CURRENT_PID=$(pgrep -f -${PROJECT_NAME}*.jar)

echo "> running program pid: $CURRENT_PID"

if [-z "$CURRENT_PID"]; then
    echo "> there are no programs running"
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 ${CURRENT_PID}
    sleep 5
fi

echo "> deploy application"

JAR_NAME=$(ls -tr ${REPOSITORY}/ | grep *.jar | tail -n 1)

echo "> JAR name: ${JAR_NAME}"

echo "> ${REPOSITORY}/${JAR_NAME} add execute permission"

chmod +x ${REPOSITORY}/${JAR_NAME}

cd ${REPOSITORY}

nohup java -jar -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-real.yml ${REPOSITORY}/${JAR_NAME} 2>&1 &


