FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src
RUN mvn package -DskipTest
WORKDIR /build/target/
RUN java -Djarmode=layertools -jar modushop-hexagonal-archi-bs-ms-user.jar extract

FROM maven:3.9.9-eclipse-temurin-21-alpine AS final
COPY --from=builder /build/target/dependencies/ ./
COPY --from=builder /build/target/spring-boot-loader/ ./
COPY --from=builder /build/target/snapshot-dependencies/ ./
COPY --from=builder /build/target/application/ ./

EXPOSE 8081

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]