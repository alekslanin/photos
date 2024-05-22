### create image as

> docker build -t wino-lanina .

### run docker image as 

> docker run --rm -d -p 8080:8080/tcp wino-lanina:latest
 
> docker run -v C:\Users\alexlanin\MyPictures:/root/media -p 8080:8080 wino-lanina:latest
 
note : use '-e REPO=repo:xyz' to set docker environment variables

> docker ps
 
> docker logs --tail 1000 -f xyz
 
### upload image to repository 

> docker tag wino-lanina alexlanin/wino-lanina:1.0.0

> docker login

> docker push alexlanin/wino-lanina:1.0.0
 

### Appendix

#### documentation

https://learnk8s.io/spring-boot-kubernetes-guide

https://mindmajix.com/intuit-interview-questions

https://interviewprep.org/intuit-interview-questions/

