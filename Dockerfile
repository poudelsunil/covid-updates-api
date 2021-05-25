# 1st stage, build the app
FROM registry.hub.docker.com/library/maven:3.6.1-jdk-11-slim as build

WORKDIR /app

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
ADD pom.xml .
RUN mvn package -Djava.net.preferIPv4Stack=true

# Do the Maven build!
# Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn package -Djava.net.preferIPv4Stack=true
RUN echo "done!"

# 2nd stage, build the runtime image
FROM registry.hub.docker.com/adoptopenjdk/openjdk11-openj9:alpine-slim
RUN apk add --no-cache redis sed bash busybox-suid && addgroup -S covidupdates && adduser -S covidupdates -G covidupdates && mkdir -p /home/covidupdates/app


# Copy the binary built in the 1st stage
COPY --from=build /app/target/covid-updates-api.jar /home/covidupdates/app/
COPY --from=build /app/target/libs /home/covidupdates/app/libs
RUN  chown -Rf covidupdates:covidupdates /home/covidupdates/app
WORKDIR /home/covidupdates/app

CMD ["java", "-jar", "covid-updates-api.jar"]

EXPOSE 8080