#!/bin/bash

DOMAIN="https://cazait.shop/swagger-ui/index.html"
PROJECT_ROOT="/home/ubuntu/app"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

for RETRY_COUNT in {1..15}
do
  RESPONSE=$(curl -s $DOMAIN)
  SWAGGER_COUNT=$(echo $RESPONSE | grep 'swagger-ui' | wc -l)

  if [ $SWAGGER_COUNT -ge 1 ] # swagger-ui 문자열이 있는지 검증
  then
    echo "> 구동 완료" >> $DEPLOY_LOG
    break
  else
    echo "> 응답 없음..." >> $DEPLOY_LOG
  fi

  if [ $RETRY_COUNT -eq 10 ]
  then
    echo "> [ERROR] 구동 실패" >> $DEPLOY_LOG
    exit 1
  fi

  echo "재요청 시도" >> $DEPLOY_LOG
  sleep 5
done

exit 0
