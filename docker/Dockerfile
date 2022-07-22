#FROM azul/zulu-openjdk:8
#FROM azul/zulu-openjdk-alpine:8
FROM bellsoft/liberica-openjdk-alpine:8
RUN rm -f /etc/localtime && ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
ADD ./nacos-sync-server-0.4.7.jar nacos-sync-server.jar
EXPOSE 8083
# Nacos是否启用
ENV nacos-enable true
# Nacos服务地址
ENV nacos-service-addr http://v2x-nacos:8849
# Nacos服务namespace
ENV nacos-service-namespace nacos-sync-prod
# 激活配置
ENV profiles-active online
ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-XX:+UnlockExperimentalVMOptions", "-XX:MaxRAMPercentage=90.0", "-XX:MinRAMPercentage=90.0", "-XX:MetaspaceSize=256M", "-XX:MaxMetaspaceSize=256M", "-XX:G1NewSizePercent=50", "-XX:G1MaxNewSizePercent=80", "-XX:MaxDirectMemorySize=256m", "-XX:-OmitStackTraceInFastThrow", "-jar",  "nacos-sync-server.jar"]