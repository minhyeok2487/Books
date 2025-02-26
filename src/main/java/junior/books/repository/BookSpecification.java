package junior.books.repository;

import junior.books.domain.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import jakarta.persistence.criteria.Join;

public class BookSpecification {

    // 제목 부분 검색
    public static Specification<Book> titleContains(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    // 출판일 이후 필터링
    public static Specification<Book> publishedAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                date == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"), date);
    }

    // 저자 이름 부분 검색
    public static Specification<Book> authorNameContains(String authorName) {
        return (root, query, criteriaBuilder) -> {
            if (authorName == null) return null;
            Join<Object, Object> authorJoin = root.join("author");
            return criteriaBuilder.like(authorJoin.get("name"), "%" + authorName + "%");
        };
    }
}

