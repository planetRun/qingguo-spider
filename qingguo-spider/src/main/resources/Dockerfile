FROM maven:3-jdk-8-alpine
LABEL maintainer="choviwu@163.com"

WORKDIR /opt
ADD . /tmp
ENV TZ=Asia/Shanghai \
#DB_USER="admin" \
#DB_PASSWORD="123456"

RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

RUN cd /tmp && mvn package -Pci && mv target/dist/halo/* /opt/halo/ \
    && rm -rf /tmp/* && rm -rf ~/.m2

EXPOSE 8888

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar"]
