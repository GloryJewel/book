#REPOSITORY git clone, REPOSITORY에 jar 파일 이동 및 실행
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=springboot2-aws-web

echo "> build 파일복사"

cp ${REPOSITORY}/zip/*.jar ${REPOSITORY}/

echo "> confirm the running program pid"

CURRENT_PID=$(pgrep -fl ${PROJECT_NAME} | grep jar | awk '{print $1}')

echo "> running program pid: ${CURRENT_PID}"

if [ -z "${CURRENT_PID}" ]; then
    echo "> there are no programs running"
else
    echo "> kill -15 ${CURRENT_PID}"
    kill -15 ${CURRENT_PID}
    sleep 5
fi

echo "> deploy application"

JAR_NAME=$(ls -tr ${REPOSITORY}/*.jar | tail -n 1)

echo "> JAR name: ${JAR_NAME}"

echo "> ${JAR_NAME} add execute permission"

chmod +x ${JAR_NAME}

cd ${REPOSITORY}

nohup java -jar -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-real.yml ${JAR_NAME} 2>&1 &