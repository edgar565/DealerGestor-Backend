# Dockerfile
# Este Dockerfile construye la aplicación backend de Spring Boot
FROM eclipse-temurin:23-jdk-alpine

# Crear directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el .jar generado desde la build al contenedor
COPY build/libs/*.jar app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]