# kitchen-force


## Docker

* 도커 컨테이너 실행하기
```bash
$ cd $PROJECT_DIR
$ docker-compose up -d
```

* 컨테이너 종료
    * Volumn을 초기화 하지 않으면 남은 데이터는 저장됨

```bash
$ docker-compose down
```
* 볼륨 마운트까지 제거시 ```-v``` 옵션 추가
    * 이러면 DB에 저장된 값도 날라감.

```bash
$ docker-compose down -v
```