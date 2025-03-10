# 도서 관리 시스템 CRUD 구현

## 0. 실행방법
### 프로젝트 다운
```shell
git clone https://github.com/minhyeok2487/Books.git
cd book
```

### 빌드
```shell
./gradlew build   # Linux/Mac
gradlew build     # Windows
```

### 실행
```shell
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### Docker로 실행한다면(환경 테스트)
```shell
docker-compose up -d
```

### 서버 실행 후 http://localhost:8080/swagger-ui 에서 Api를 확인할 수 있습니다.

## 1. 기술 스택

- Java 17
- Spring Boot 3.3.1
- H2 DataBase
- Spring Data JPA

## 2. DB 설계

### 저자 (Author)

| 컬럼명   | 설명     | 제약 조건            |
|-------|--------|------------------|
| id    | 고유 식별자 | Primary Key      |
| name  | 저자 이름  | Non-null         |
| email | 이메일    | Non-null, Unique |

### 도서 (Book)

| 컬럼명              | 설명         | 제약 조건                    |
|------------------|------------|--------------------------|
| id               | 고유 식별자     | Primary Key              |
| title            | 도서 제목      | Non-null                 |
| description      | 도서 설명      | Null 허용                  |
| isbn             | 국제 표준 도서번호 | Non-null, Unique         |
| publication_data | 출판일        | Null 허용                  |
| author_id        | 해당 도서의 저자  | Foreign Key (Author 1:N) |

## 3. API

### 저자 (Author) API

| 기능    | 메서드    | 엔드포인트           | 설명                 | 비고                                                         |
|-------|--------|-----------------|--------------------|------------------------------------------------------------|
| 생성    | POST   | `/authors`      | 새로운 저자를 생성         | 응답 예시: <br/>`{"name": "홍길동", "email": "hong@example.com"}` |
| 목록 조회 | GET    | `/authors`      | 모든 저자 목록을 반환       | -                                                          |
| 상세 조회 | GET    | `/authors/{id}` | 특정 저자의 상세 정보를 반환   | -                                                          |
| 수정    | PUT    | `/authors/{id}` | 요청 본문을 통해 저자 정보 수정 | -                                                          |
| 삭제    | DELETE | `/authors/{id}` | 저자를 삭제             | - 연관 도서 있으면 삭제 불가                                          |

### 도서 (Book) API

| 기능    | 메서드    | 엔드포인트         | 설명                 | 요청 본문 예시                                                                                                                    |
|-------|--------|---------------|--------------------|-----------------------------------------------------------------------------------------------------------------------------|
| 생성    | POST   | `/books`      | 새로운 도서를 생성         | `{"title": "예제 도서", "description": "도서에 대한 설명", "isbn": "1234567890123", "publication_date": "2025-01-01", "author_id": 1}` |
| 목록 조회 | GET    | `/books`      | 모든 도서 목록을 반환       | - 필터링 (제목 포함, 출판일 이후, 저자 이름)                                                                                                |
| 상세 조회 | GET    | `/books/{id}` | 특정 도서의 상세 정보를 반환   | -                                                                                                                           |
| 수정    | PUT    | `/books/{id}` | 요청 본문을 통해 도서 정보 수정 | -                                                                                                                           |
| 삭제    | DELETE | `/books/{id}` | 도서를 삭제             | -                                                                                                                           |

## 4. 유효성 체크

- 고유성 체크
    - isbn은 도서마다 유일
        - ISBN-10 규칙을 사용
        - 10자리 숫자로 구성
            - 국가, 언어 식별 번호: 첫 번째 두 자리
                - 10~90 사이의 숫자 허용
            - 출판사 식별 번호: 다음 3~6자리
            - 책 식별 번호: 다음 7~9자리
            - 체크 디지트: 마지막 자리. 0을 사용.
    - email은 저자마다 유일.
- 에러 처리
    - 필수 항목이 null로 들어온 경우
    - 잘못된 입력
    - 존재하지 않은 리소스 접근