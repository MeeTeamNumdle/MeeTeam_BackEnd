#!/bin/bash

DEPLOYMENT_ID=$DEPLOYMENT_ID
# 배포 ID가 제공되지 않으면 오류를 출력하고 종료
if [ -z "$DEPLOYMENT_ID" ]; then
  echo "Deployment ID is not set. Please ensure the deployment ID is provided."
  exit 1
fi

# AWS CLI를 사용하여 배포 세부 정보 가져오기
DEPLOYMENT_DETAILS=$(aws deploy get-deployment --deployment-id $DEPLOYMENT_ID)

# 배포 그룹 이름 추출
DEPLOYMENT_GROUP_NAME=$(echo $DEPLOYMENT_DETAILS | jq -r '.deploymentInfo.deploymentGroupName')

# 만약 배포가 prod 라면
if [ "$DEPLOYMENT_GROUP_NAME" = "spring-app" ]; then
  # 작업 디렉토리를 /home/ubuntu/app/prod으로 변경
  cd /home/ubuntu/app/prod

  # 환경변수 DOCKER_APP_NAME을 spring-app으로 설정
  DOCKER_APP_NAME=spring-app

  # 실행중인 blue가 있는지 확인
  # 프로젝트의 실행 중인 컨테이너를 확인하고, 해당 컨테이너가 실행 중인지 여부를 EXIST_BLUE 변수에 저장
  EXIST_REDIS=$(sudo docker ps --filter "name=redis" --filter "status=running")
  EXIST_BLUE=$(sudo docker ps --filter "ancestor=${DOCKER_APP_NAME}-blue" --filter "status=running")

  # 배포 시작한 날짜와 시간을 기록
  echo "배포 시작일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # green이 실행중이면 blue up
  # EXIST_BLUE 변수가 비어있는지 확인
  if [ -z "$EXIST_REDIS" ]; then
    # 로그 파일(/home/ubuntu/deploy.log)에 "blue up - blue 배포 : port:8081"이라는 내용을 추가
      echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

      # docker-compose.prod.yml 파일을 사용하여 prod-blue, redis 서비스를 빌드하고 실행
      sudo docker-compose -f docker-compose.prod.yml up redis ${DOCKER_APP_NAME}-blue -d --build

      while [ 1 = 1 ]; do
          echo ">>> spring blue health check ..."
          sleep 3
          REQUEST_SPRING=$(curl 127.0.0.1:8081/actuator/health)
          if [ -n "$REQUEST_SPRING" ]; then
            echo ">>> spring blue health check success !"
            break;
          fi
      done;

  elif [ -z "$EXIST_BLUE" ]; then

    # 로그 파일(/home/ubuntu/deploy.log)에 "blue up - blue 배포 : port:8081"이라는 내용을 추가
    echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

    # docker-compose.prod.yml 파일을 사용하여 prod-blue 서비스를 빌드하고 실행
    sudo docker-compose -f docker-compose.prod.yml up ${DOCKER_APP_NAME}-blue -d --build

    while [ 1 = 1 ]; do
        echo ">>> spring blue health check ..."
        sleep 3
        REQUEST_SPRING=$(curl 127.0.0.1:8081/actuator/health)
        if [ -n "$REQUEST_SPRING" ]; then
          echo ">>> spring blue health check success !"
          break;
        fi
    done;

    # /home/ubuntu/deploy.log: 로그 파일에 "green 중단 시작"이라는 내용을 추가
    echo "green 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

    # docker-compose.prod.yml 파일을 사용하여 spring-app-green 서비스 중지
    sudo docker-compose -f docker-compose.prod.yml down ${DOCKER_APP_NAME}-green

     # 사용하지 않는 이미지 삭제
    sudo docker image prune -af

    echo "green 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # blue가 실행중이면 green up
  else
    echo "green 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
    sudo docker-compose -f docker-compose.prod.yml up ${DOCKER_APP_NAME}-green -d --build

      while [ 1 = 1 ]; do
          echo ">>> spring green health check ..."
          sleep 3
          REQUEST_SPRING=$(curl 127.0.0.1:8082/actuator/health)
          if [ -n "$REQUEST_SPRING" ]; then
            echo ">>> spring green health check success !"
            break;
          fi
      done;

    echo "blue 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
    # docker-compose.prod.yml 파일을 사용하여 spring-app-blue 서비스 중지
    sudo docker-compose -f docker-compose.prod.yml down ${DOCKER_APP_NAME}-blue

    sudo docker image prune -af

    echo "blue 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  fi
    echo "배포 종료  : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

    echo "===================== 배포 완료 =====================" >> /home/ubuntu/deploy.log
    echo >> /home/ubuntu/deploy.log
#만약 배포가 개발이라면
else
  cd /home/ubuntu/app/dev
  # 현재 실행 중인 컨테이너를 중지하고 제거합니다
  docker-compose -f docker-compose.dev.yml down
  # 새 이미지를 빌드하고 컨테이너를 백그라운드에서 실행합니다
  docker-compose -f docker-compose.dev.yml up -d --build
  while [ 1 = 1 ]; do
      echo ">>> spring green health check ..."
      sleep 3
      REQUEST_SPRING=$(curl 127.0.0.1:1821/actuator/health)
      if [ -n "$REQUEST_SPRING" ]; then
        echo ">>> spring green health check success !"
        break;
      fi
  done;
  sudo docker image prune -af
fi