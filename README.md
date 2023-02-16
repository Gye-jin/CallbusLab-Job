# CallbusLab-Job
콜버스랩 백엔드 지원자: 정계진 사전과제

## 목차

* 개발환경
* 외부 라이브러리
* 빌드 및 실행하기
* API 명세서 및 ERD 구조
* 기능 요구사항
* 기술 요구
* 그외 기능


### 개발환경
* 기본 환경  
  * SpringBoot 2.7.8
  * Java 8
  * SpringSecurity
  * JPA
  * Maven
  * lombok
  * MySQL 8.0
  
### 외부 라이브러리

```
Java-jwt  3.10.2:
유저의 권한을 부여하고 로그인을 유지시키는 방식에서 JWT를 이용했기 때문에 사용함.
  
swagger2, swagger-ui 2.9.2:  
Swagger를 이용해서 기능을 API 기능들을 테스트 할 수 있는 UI를 제공하며,
즉각적인 테스트를 진행해 볼 수 있기 때문에 사용함.
```


### 실행하기
```
로컬 환경에서 KakaoMemberShipApplication 실행 가능
java -jar zaritalk-0.0.1-SNAPSHOT.jar 명령어로 빌드된 jar 파일 실행 가능
```
### 테스트 진행
http://localhost:8080/swagger-ui.html#/
Swagger 접속 후 컨트롤러 별로 실행 후 확인 가능.

### API 명세서 및 ERD 구조
<details>
<summary>API 명세서</summary>
<div>


|Controller|기능|Method|URL|
|---|---|---|---|
|User|회원가입|POST|/login|
|User|로그인|POST|/join|
|User|로그아웃|GET|/api/logout|
|User|회원탈퇴|DELETE|/api/delete|
|User|내가 작성한 게시글 확인|GET|/api/myboard|
|Board|게시글 작성|POST|/api/board|
|Board|게시글 조회|GET|/board/{boardNo}|
|Board|게시글 수정|PUT|/api/board/{boardNo}|
|Board|게시글 삭제|DELETE|/api/board/{boardNo}|
|BoardList|게시글 목록 조회|GET|/page/{pageNo}/{size}|
|BoardList|게시글 제목으로 검색하여 조회|GET|/page/{pageNo}/{size}/{boardTitle}|
|Comment|댓글 작성|POST|/api/board/{boardNo}/comment|
|Comment|댓글 수정|PUT|/api/board/{boardNo}/comment|
|Comment|댓글 삭제|DELETE|/api/board/{boardNo}/comment|
|Heart|좋아요 하기 / 취소|PUT|/api/board/{boardNo}/heart|
|Heart|User가 좋아요한 게시글 확인|GET|/api/board/{boardNo}/heart/{accountId}|
</div>
</details>

<details>
<summary>ERD 구조</summary>
<div>

![<ERD>](./issue/ERD.png)

</div>
</details>
   
   
   
   
### 기능 요구 사항
* 회원/ 비회원에 따라서 페이지에 접근할 수 있는 권한을 부여함.
  * 코드 작성 예시
    ```
 	http.authorizeRequests()
		.antMatchers("/login","/join","/board/**","/page/**").permitAll()
		.antMatchers("/api/**").authenticated()
  
 
    ```
   *  위와같이 url 요청 주소에 따라서 api라는 붙은 메소드는 인가된 회원만 접근이 가능하도록 설정함.

 


   
- 게시글 목록에선 작성한 사용자가 어떤 계정 타입인지와 좋아요 수, 자신이 좋아요한 글인지 아닌지를 표시해주어야 함.
   
   * 코드 작성 예시
      ```
      "boardList": [
       {
      "boardNo": 10,
      "boardTitle": "happybirthday",
      "boardContent": "happybirthday",
      "writtenDatetime": "2023-02-15T12:53:18.365",
      "modifiedDatetime": "2023-02-15T23:31:54.774",
      "heartCnt": 1,
      "user": {
        "userNo": 2,
        "userPw": "$2a$10$5TBLsDMAwQFtIIpCQo9pnuS7kYOazrCwqGgSRymV49Mc0C92apYBi",
        "nickName": "정계진",
        "accountId": "jkj1",
        "accountType": "공인중개사",
        "createTime": "2023-02-14T23:19:43.401",
        "quit": false
      },
      "comments": [
        {
          "commentNo": 26,
          "commentContent": "aaaaa",
          "writtenDatetime": "2023-02-15T16:05:22.247",
          "modifiedDatetime": "2023-02-15T16:05:22.247",
          "boardNo": 10,
          "userId": "jkj3"
        }
      ],
      "hearts": [
        {
          "heartNo": 14,
          "doHeart": true,
          "userId": "jkj1"
        }
      ]
      } 
         ```

  * 게시글 목록에서 게시글 번호와 작성자, 계정이 확인이 가능하며 게시글 내에 좋아요 받은 횟수또한 확인이 가능하며,
     hearts라는 DTO안에 좋아요를 한 유저가 어떤 사람인지 확인이 가능함.

  
    
    
### 기술 요구 사항
- 글 작성, 수정, 삭제
- 글 좋아요
- 글 목록
- 들어온 사용자가 임대인인지, 임차인인지, 공인중개사인지는 HTTP Header 중 Authentication 의 Value Prefix 에 따라 사용해 구분 할 수 있어야 합니다. 만약 Authentication 값이 없다면 외부 사용자로 취급합니다.
- 별도의 회원 테이블을 만들어 사용합니다.
- 글에 `좋아요`는 한 계정이 한 글에 한 번만 할 수 있습니다.
- 어떤 사용자가 어떤 글에 좋아요 했는지 히스토리를 확인할 수 있어야 합니다.
- 각 글은 작성시간, 마지막 수정시간, 삭제시간에 대한 히스토리를 확인할 수 있어야 합니다.
