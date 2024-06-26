FROM registry.access.redhat.com/ubi9/openjdk-21@sha256:b5b1da09031a4a9302a660501301ba62df147b48f986a654194eb1717bde23cc as builder
RUN mkdir /opt/app
COPY gradle /opt/app/gradle
COPY gradlew /opt/app/gradlew
COPY build.gradle.kts settings.gradle.kts /opt/app
COPY src /opt/app/src
WORKDIR /opt/app
RUN ./gradlew test
RUN ./gradlew install

FROM registry.access.redhat.com/ubi9/openjdk-21@sha256:b5b1da09031a4a9302a660501301ba62df147b48f986a654194eb1717bde23cc
COPY --from=builder /opt/app/build/install/acrobot-slack /opt/app
WORKDIR /opt/app
CMD ["/opt/app/bin/acrobot-slack"]
