# 使用 Maven 镜像作为构建基础
FROM maven:3.8.6-openjdk-11-slim AS builder

# 作者信息
MAINTAINER "Ledger"

# 设置工作目录
WORKDIR /app

# 将项目源码和 pom.xml 文件拷贝到容器中
COPY pom.xml /app/
COPY src /app/src

# 执行构建命令，生成 JAR 文件
RUN mvn clean package -DskipTests

# 使用较小的 JRE 镜像来运行已构建的 JAR 文件
FROM openjdk:11-jre-slim

# 设置时区
RUN apt-get update && apt-get install -y tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apt-get clean

# 创建并设置工作目录
WORKDIR /app

# 从构建镜像中拷贝构建好的 JAR 文件
COPY --from=builder /app/target/api_gateway-0.0.1-SNAPSHOT.jar /app/api_gateway-0.0.1-SNAPSHOT.jar

# 拷贝配置文件：application.yml 和 application-prod.yml
COPY --from=builder /app/src/main/resources/application.yml /app/application.yml
COPY --from=builder /app/src/main/resources/application-prod.yml /app/application-prod.yml

# 暴露服务端口
EXPOSE 12000

# 设置容器启动命令
CMD ["java", "-jar", "/app/api_gateway-0.0.1-SNAPSHOT.jar"]
