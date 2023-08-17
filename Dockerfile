# Sử dụng hình ảnh OpenJDK làm hình ảnh cơ sở
FROM openjdk:11-jre-slim

# Thư mục làm việc trong container
WORKDIR /app

# Sao chép tệp JAR của ứng dụng Spring Boot vào container
COPY target/his-0.0.1-SNAPSHOT.jar app.jar

# Expose cổng 8080
EXPOSE 8085

# Lệnh để chạy ứng dụng Spring Boot khi container được khởi động
CMD ["java", "-jar", "app.jar"]
