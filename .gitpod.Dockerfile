FROM gitpod/workspace-full

# Install Java 8
USER root
RUN apt-get update && apt-get install -y openjdk-8-jdk && rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Android SDK
RUN mkdir -p /opt/android-sdk && cd /opt/android-sdk && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip && \
    unzip commandlinetools-linux-9477386_latest.zip && \
    rm commandlinetools-linux-9477386_latest.zip

ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/bin:$ANDROID_HOME/platform-tools

# Accept licenses automatically
RUN yes | sdkmanager --licenses

# Install Gradle (6.1.1+)
RUN wget https://services.gradle.org/distributions/gradle-6.1.1-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-6.1.1-bin.zip \
    && ln -s /opt/gradle/gradle-6.1.1/bin/gradle /usr/bin/gradle

ENV PATH=$PATH:/opt/gradle/gradle-6.1.1/bin

USER gitpod
