# springboot-restapi-boilerplate
매번 spring boot 프로젝트를 초기화하기 번거로워서 본 프로젝트를 만듦

## 사용방법
단, mysql 데이터베이스 서버가 가동중이어야 함
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

## 데이터베이스 접속 정보
본 boilerplate 는 mysql 데이터베이스를 사용하고 있으며 다른 데이터베이스로 교체시 적절한 dependency 를 추가해줘야 함  
[application-dev.properties](./src/main/resources/application-dev.properties) 를 수정하여 데이터베이스에 접속해야 함

```properties
# JDBC 접속 설정
spring.jpa.hibernate.ddl-auto=update

# jdbc:mysql://{{데이터베이스주소}}/{{데이터베이스스키마}}?{{타임존}}
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_development?serverTimezone=UTC&characterEncoding=UTF-8

# 접속할 계정 정보
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

## 로깅 설정
[logback-spring.xml](./src/main/resources/logback-spring.xml) 을 수정하여  
파일 로깅 / 콘솔 로깅 전환 가능 (기본값은 콘솔 로깅)

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
