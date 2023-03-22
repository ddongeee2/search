# search
- 블로그 검색과 인기 검색어 목록 서비스를 제공합니다.
- Kakao 와 Naver 의 blog search api 를 연동하였습니다. 

# 환경
- Java 17 
- Spring Boot 2.7.3
- gradle.kotlin
- DB: H2, 
- "flyway" for data migration
- "Spock" for testing

# 빌드 결과물
[app-api-1.0-SNAPSHOT-boot.jar](https://github.com/ddongeee2/search)

# 프로젝트 구성
```
search
├── apps                  
│   └── app-api               - Search API APP
├── buildSrc                  - 의존성 버전 관리
└── libs
    ├── adapter-http          - http 를 이용한 3rd party 의존성 관리
    ├── adapter-persistence   - 영속성 관련 의존성 관리
    └── application           - 서비스 관련 로직
```

# API spec
## 1. 블로그 검색

### 요청
``
GET /api/v1/search/blog
``

Parameter

| Name           | Type      | Description                                      | Required | Default |
|----------------|-----------|--------------------------------------------------|----------|---------|
| `keyword`      | `String`  | 검색 키워드                                           | O        | n/a | 
| `restApiType`  | `String`  | 연동 api 타입(KAKAO_SEARCH_BLOGS, NAVER_SEARCH_BLOGS) | O        | KAKAO_SEARCH_BLOGS |
| `sort`         | `String`  | ACCURACY, RECENCY     | X        | n/a |
| `page`         | `Number` | 요청 페이지 번호                                        | X        | 1 |
| `size`         | `Number` | 한 페이지의 사이즈                                       | X        | 10 |


Response
```json
{
  "success": true,
  "code": "SUCCEED",
  "message": null,
  "data": {
    "documents": [
      {
        "title": "<b>트와이스</b> <b>twice</b>",
        "contents": "<b>트와이스</b> 정규 1집 &#39;twicetagram&#39; ▶likey baby one more time의 브리트니가 9명이 나오는 것 같은 &#39;<b>twice</b>&#39; https://www.youtube.com/watch?v=V2hlQkVJZhE 사진_멜론 가장 한국적인 걸그룹이자 보편적으로 다양한 연령층에서 제일 많이 좋아할 것 같은 그룹은 역시 &#39;<b>트와이스</b>&#39;다. 이들의 첫 정규앨범을 들어보자, 어느샌...",
        "url": "http://dreamsr.tistory.com/112",
        "blogName": "alien sound",
        "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/IST0yOgwR9H",
        "writtenAt": "2023-03-15 14:47:54"
      },
      ...
    ],
    "pagination": {
      "currentPage": 1,
      "totalPage": 799,
      "totalItemCount": 527444,
      "countPerPage": 10,
      "hasNextPage": true,
      "nextPage": 2
    }
  }
}
```


## 2. 검색어 통계 조회
``
GET /api/v1/statistics/search-blog/top-keyword
``


Parameter

| Name   | Type     | Description | Required | Default |
|--------|----------|-------------|----------|---------|
| `top`  | `Number` | 최대 인기 키워드 수 | X        | 10 |

Response
```json
{
  "success": true,
  "code": "SUCCEED",
  "message": null,
  "data": [
    {
      "keyword": "GOD",
      "count": 9977
    },
    {
      "keyword": "BTS",
      "count": 3123
    },
    {
      "keyword": "블랙핑크",
      "count": 777
    },
    {
      "keyword": "뉴진스",
      "count": 123
    },
    {
      "keyword": "트와이스",
      "count": 115
    }
  ]
}
```