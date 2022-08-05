FROM java:8
ADD target/taskrestservice-1.0-SNAPSHOT.jar taskrestservice-1.0-SNAPSHOT.jar
ADD local_properties.yaml local_properties.yaml
ENTRYPOINT [ "java","-jar","taskrestservice-1.0-SNAPSHOT.jar","server","local_properties.yaml" ]