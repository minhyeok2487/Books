package junior.books.domain;

import jakarta.persistence.*;
import junior.books.dto.author.AuthorUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    public void update(AuthorUpdateRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
