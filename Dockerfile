FROM java:8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
RUN mkdir /app
ADD target/springbootDemo-0.0.1-SNAPSHOT.jar /app/app.jar
RUN sh -c 'touch /app/app.jar'
EXPOSE 10000
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/app.jar" ]