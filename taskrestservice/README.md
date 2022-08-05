# taskrestservice

How to start the taskrestservice application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/taskrestservice-1.0-SNAPSHOT.jar server local_properties.yaml`
3. To check that your application is running enter url `http://localhost:8080`


To create docker image and run as docker container

1. CD into project folder
2. docker build -t taskrestservice .
3. docker run -it -p 8080:8080 -p 8081:8081 -e taskrestservice taskrestservice


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
