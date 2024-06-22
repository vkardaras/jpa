package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(
                name = "Author.eagerlyFetchBooks",
                attributeNodes = @NamedAttributeNode("books")
        ),
        @NamedEntityGraph(
                name = "Author.eagerlyFetchBookShops",
                subgraphs = @NamedSubgraph(
                        name = "books",
                        attributeNodes = @NamedAttributeNode("bookShops")
                )
        )
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
