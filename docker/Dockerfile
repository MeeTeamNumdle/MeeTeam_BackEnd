### Docker 이미지를 생성할 때 기반이 되는 베이스 이미지를 설정한다.
FROM openjdk:17
### 타임존 설정
RUN apk add tzdata
### Dockerfile 내에서 사용할 변수 JAR_FILE을 정의한다.
ARG JAR_FILE=*.jar
### JAR_FILE 경로에 해당하는 파일을 Docker 이미지 내부로 복사한다.
COPY ${JAR_FILE} meeteam.jar
### Docker 컨테이너가 시작될 때 실행할 명령을 지정한다.
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","-Duser.timezone=Asia/Seoul","meeteam.jar"]
