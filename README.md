# springboot-restapi-boilerplate
매번 spring boot 프로젝트를 초기화하기 번거로워서 본 프로젝트를 만듦

## Usage
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