언어 : `java11`

프레임 워크 : `SpringBoot 2.7.6`

빌드 툴 : `Maven 3.8.6`

Database : `H2`

실행 명령어 : 
```bash
cd {application dir}
mvn clean package
java -jar ./target/barogo-0.0.1-SNAPSHOT.jar
```

<a id="api_list"></a>

### 1. RESTFul API List

| Category | Resource |            URI            | Method | Description |
|:--------:|:--------:|:-------------------------:|:------:|:-----------:|
|    인증    |   로그인    |          /login           |  POST  |             | 
|          |  회원 가입   |          /users           |  POST  |             | 
|    주문    |  주문 조회   |          /orders          |  GET   |             | 
|          | 주문 주소 수정 | /orders/{orderId}/address | PATCH  |             | 

### 2. API 정의

- API 공통 Request

| Element       | Parameter Type | Data Type | Parent | Description  | Mandatory | 
|:--------------|:---------------|:---------:|:-------|:-------------|:---------:|
| Authorization | Header         |  String   |        | Bearer Token |     O     |

> Authorization : "Bearer {header}.{payload}.{signature}"

- API 공통 Response

| Element       | Data Type | Parent | Description               |
|---------------|-----------|--------|---------------------------|
| resultCode    | String    |        | 응답코드                      |
| resultMessage | String    |        | 응답메시지                     |
| systemDt      | String    |        | 시스템 처리시(yyyyMMddHH24MISS) |
| resultData    | Object    |        | 응답 Body                   |


```json
{
  "resultCode": "0000",
  "resultMessage": "성공",
  "systemDt": "20210726090000",
  "resultData": {}
}
```
##### 1.1.1. 회원가입

* `Protocol` : HTTP
* `Method` : POST
* `URI` : /users
* `Request Parameters`

| Element  | Parameter Type | Data Type | Parent | Description | Mandatory | 
|:---------|:---------------|:---------:|:-------|:------------|:---------:|
| userId   | Body           |  String   |        | 사용자 로그인 아이디 |     O     |
| password | Body           |  String   |        | 비밀번호        |     O     |
| username | Body           |  String   |        | 사용자 실명      |     O     |

* `Response Parameters and Body`

| Element    | Data Type | Parent | Description | 
|:-----------|:---------:|:-------|:------------|
| userId     |  String   |        | 사용자 로그인 아이디 |    
| username   |  String   |        | 사용자 실명      |    
| signUpDate |  String   |        | 회원 가입일자     | 



```json
{
  "resultCode": "0000",
  "systemDt": "20221224181033",
  "resultMessage": "성공",
  "resultData": {
    "userId": "abcdefg12345",
    "username": "바로고",
    "signUpDate": "20221224181033"
  }
}
```



##### 1.1.2. 로그인

* `Protocol` : HTTP
* `Method` : POST
* `URI` : /login
* `Request Parameters`

| Element  | Parameter Type | Data Type | Parent | Description | Mandatory | 
|:---------|:---------------|:---------:|:-------|:------------|:---------:|
| userId   | Body           |  String   |        | 사용자 아이디     |     O     |
| password | Body           |  String   |        | 비밀번호        |     O     |

* `Response Parameters and Body`

| Element     | Data Type | Parent | Description |
|:------------|:---------:|:-------|:------------|
| tokenType   |  String   |        | 토큰 유형       |
| accessToken |  String   |        | 토큰          |
| failMessage |  String   |        | 로그인 실패 메세지  |
| username    |  String   |        | 고객실명        |
| userId      |  String   |        | 고객 로그인 아이디  |
| id          |   Long    |        | 아이디 시퀀스     |



```json
{
  "code": "0000",
  "message": "Success",
  "systemDt": "20210726090000",
  "data": {
    "tokenType": "Bearer",
    "accessToken": "Bearer {header}.{payload},{signature}",
    "failMessage": "",
    "username": "홍길동",
    "userId" : "barogo12345",
    "id": 1
  }
}
```

##### 2.1.1. 주문 조회

* `Protocol` : HTTP
* `Method` : GET
* `URI` : /orders
* `Request Parameters`

| Element    | Parameter Type | Data Type | Parent | Description         | Mandatory |
|:-----------|:---------------|:---------:|:-------|:--------------------|:---------:|
| searchFrom | Body           |  String   |        | 주문조회 시작일 (yyyyMMdd) |     O     |
| searchTo   | Body           |  String   |        | 주문조회 종료일 (yyyyMMdd) |     O     |
| userSeq    | Body           |   Long    |        | 고객 아이디              |     O     |

* `Response Parameters and Body`

| Element       | Data Type | Parent | Description                                                                    | 
|:--------------|:---------:|:-------|:-------------------------------------------------------------------------------|
| address       |  String   | orders | 배달 주소                                                                          |
| addressTitle  |  String   | orders | 배달 주소명                                                                         |    
| username      |  String   | orders | 주문자명                                                                           |  
| menu          |  String   | orders | 메뉴                                                                             |
| storeName     |  String   | orders | 가맹점 명                                                                          |    
| orderDateTime |  String   | orders | 주문 시간                                                                          |  
| orderStatus   |  String   | orders | 주문 상태 (ORDER_RECEIVING, PREPARING_DISH, DISH_HAND_OVER, DELIVERING, DELIVERED) |  

```json
{
  "resultCode": "0000",
  "systemDt": "20221225162152",
  "resultMessage": "성공",
  "resultData": {
    "orders": [
      {
        "address": "서울시 금천구 홍은2길",
        "addressTitle": "우리집",
        "username": "유호연",
        "menu": "신전떡볶이",
        "storeName": "신전떡볶이",
        "orderDateTime": "20221225162147",
        "orderStatus": "ORDER_RECEIVING"
      },
      {
        "address": "서울시 금천구 홍은2길",
        "addressTitle": "우리집",
        "username": "유호연",
        "menu": "족발",
        "storeName": "동경야시장",
        "orderDateTime": "20221225162147",
        "orderStatus": "DELIVERED"
      },
      {
        "address": "서울시 금천구 홍은2길",
        "addressTitle": "우리집",
        "username": "유호연",
        "menu": "매콤떡볶이",
        "storeName": "골목떡볶이",
        "orderDateTime": "20221225162147",
        "orderStatus": "DELIVERED"
      }
    ]
  }
}
```

##### 2.1.2. 주문 주소 수정

* `Protocol` : HTTP
* `Method` : POST
* `URI` : /orders/{orderId}/address
* `Request Parameters`

| Element      | Parameter Type | Data Type | Parent | Description | Mandatory | 
|:-------------|:---------------|:---------:|:-------|:------------|:---------:|
| orderId      | Path           |   Long    |        | 주문 아이디      |     O     |
| addressTitle | Body           |  String   |        | 주소 명        |     O     |
| address      | Body           |  String   |        | 상세주소        |     O     |

* `Response Parameters and Body`

| Element       | Data Type | Parent | Description                                                                    |
|:--------------|:---------:|:-------|:-------------------------------------------------------------------------------|
| address       |  String   |        | 배달 주소                                                                          |
| addressTitle  |  String   |        | 배달 주소명                                                                         |    
| username      |  String   |        | 주문자명                                                                           |  
| menu          |  String   |        | 메뉴                                                                             |
| storeName     |  String   |        | 가맹점 명                                                                          |    
| orderDateTime |  String   |        | 주문 시간                                                                          |  
| orderStatus   |  String   |        | 주문 상태 (ORDER_RECEIVING, PREPARING_DISH, DISH_HAND_OVER, DELIVERING, DELIVERED) |  


```json
{
  "code": "0000",
  "message": "Success",
  "systemDt": "20210726090000",
  "data": {
    "address": "서울시 금천구 홍은2길",
    "addressTitle": "우리집",
    "username": "유호연",
    "menu": "매콤떡볶이",
    "storeName": "골목떡볶이",
    "orderDateTime": "20221225162147",
    "orderStatus": "ORDER_RECEIVING"
  }
}
```
---
***Test UserInfo***
```text
Uri : /login
Method : POST
{
    "userId":"yhy1045",
    "password":"Barogo1234567"
}

Authorization Type : Bearer Token
Token Key : Authorization
form : Bearer {header}.{payload}.{signature}

```
