FROM gradle:6.6.1 AS Build

ARG JAR_FILE=/tmp/iron/build/libs/*.jar
ARG PORT=9777
ARG TIME_ZONE=Asia/Shanghai

ADD / /tmp/iron
RUN cd /tmp/iron && gradle build -info
RUN mv ${JAR_FILE} /app.jar
RUN rm -rf /tmp/iron

ENV TZ=${TIME_ZONE}
ENV JVM_XMS="256m"
ENV JVM_XMX="256m"

VOLUME /tmp
EXPOSE ${PORT}
ENTRYPOINT java -Xms${JVM_XMS} -Xmx${JVM_XMX} -Djava.security.egd=file:/dev/./urandom -server -jar /app.jar