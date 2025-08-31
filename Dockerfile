FROM apache/beam_java17_sdk:latest
RUN mkdir /App
WORKDIR App
COPY target/hello-world-0.1.0.jar /App
ENTRYPOINT [ "java", "-jar", "/App/hello-world-0.1.0.jar" ]
