Dockerize
-
> -- Maven 빌드를 통한 Jar 생성
>>$ mvn clean && mvn package
>
> -- 이미지 생성
>>$ docker build -t spring-boot-kotlin/security-jwt .
> 
> -- 컨테이너 실행
>>$ docker run -d -p 5000:5000 -e "SPRING_PROFILES_ACTIVE=local" --name api spring-boot-kotlin/security-jwt:latest