FROM openjdk:10-jdk
MAINTAINER Mike Solomon "mikesol@gmail.com"
EXPOSE 5050
COPY distributions/github-interview.zip /opt/github-interview/
RUN unzip /opt/github-interview/github-interview.zip -d /opt/github-interview
WORKDIR /opt/github-interview/github-interview
CMD ["./bin/github-interview", "-fg"]