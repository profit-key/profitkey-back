FROM openjdk:17-jdk-slim AS spring-build

WORKDIR /spring
COPY ./ .
RUN ls -la
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17-jdk-slim AS spring-jar

WORKDIR /app

COPY --from=spring-build /spring/build/libs/stock-0.0.1-SNAPSHOT.jar /app/app.jar
COPY --from=spring-build /spring/profitkey.sh /app/profitkey.sh

# profitkey.sh 실행 권한 부여
RUN chmod +x /app/profitkey.sh

# 환경변수를 설정하고 애플리케이션 실행
ENTRYPOINT ["/bin/bash", "-c", "source /app/profitkey.sh && java -jar -Dspring.profiles.active=dev app.jar"]