# pet-clinic

[![pet-clinic CI with Maven](https://github.com/aliazani/pet-clinic/actions/workflows/maven.yml/badge.svg?branch=main&event=push)](https://github.com/aliazani/pet-clinic/actions/workflows/maven.yml)

This project is my version of [pet-clinic](https://github.com/spring-projects/spring-petclinic) for learning spring boot
and spring framework.

---
To run pet-clinic app from docker container you should do following steps:

1. First you should generate jar files with following command:
   run mvn clean package spring-boot:repackage
2. Then build the image with any name and version you like in here we used pet-clinic as name and v1 as version and then
   run :docker build -t=pet-clinic:v1 .
3. Lastly run the built image :docker run -p 8080:8080 pet-clinic:v1
