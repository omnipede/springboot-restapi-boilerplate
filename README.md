# springboot-restapi-boilerplate
매번 spring boot 프로젝트를 초기화하기 번거로워서 본 프로젝트를 만듦

## 사용방법
### Build
```shell script
$ ./gradlew build
```

### Test
```shell script
$ ./gradlew test
```

### Run (with gradle spring boot plugin)
```shell script
$ ./gradlew bootRun
```

### Run (with JAR)
```shell script
$ java -jar ./build/libs/*.jar
```

## API documentation
Restdocs 라이브러리를 활용하여 API 문서를 자동화함.
1. 서버 실행
2. ```{ServerDomain}/docs/api-guide.html``` 접속

## Admin dashboard
[application.properties](./src/main/resources/application.properties) 를 적절히 수정하여 어드민 서버 대시보드를 통해 본 서버를 모니터링 할 수 있음  
```properties
# 어드민 서버 URL 및 접속 정보
spring.boot.admin.client.url=http://localhost:18080
spring.boot.admin.client.username=omnipede
spring.boot.admin.client.password=password
```

어드민 서버 배포 방법에 대한 자세한 내용은 다음 [링크](https://github.com/omnipede/springboot-admin-server) 참조
