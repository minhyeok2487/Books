# OpenJDK 17 사용
FROM openjdk:17-jdk-slim

# 시간대 설정
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

# JAR 파일 복사
ARG JAR_FILE=build/libs/books-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 포트 설정
ENV PORT 8080
EXPOSE $PORT

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]