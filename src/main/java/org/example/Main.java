package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.example.entities.Author;
import org.example.entities.Book;
import org.example.entities.BookShop;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "none");  // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

            // JOIN
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Tuple> cp = builder.createTupleQuery();

            Root<Book> bookRoot = cp.from(Book.class); // SELECT b FROM Book b
            Join<Book, Author> joinAuthor = bookRoot.join("authors");
            Join<Book, BookShop> joinBookShop = bookRoot.join("bookShops");

            cp.multiselect(bookRoot, joinAuthor, joinBookShop); // SELECT b, a FROM Book b INNER JOIN Author a

            TypedQuery<Tuple> q = em.createQuery(cp);

            q.getResultStream()
                            .forEach(t -> System.out.println(t.get(0) + " " + t.get(1) + " " + t.get(2)));

            // SUB QUERY
            CriteriaQuery<Author> mainquery = builder.createQuery(Author.class);
            Root<Author> authorRoot = mainquery.from(Author.class);

            /*
                SELECT a,
                    (SELECT count(b) FROM Book b JOIN Author a ON b.id IN a.books) n
                FROM Author a WHERE n > 2
             */

            Subquery<Long> subquery = mainquery.subquery(Long.class);
            Root<Author> subRootAuthor = subquery.correlate(authorRoot);
            Join<Author, Book> authorBookJoin = subRootAuthor.join("books");

            subquery.select(builder.count(authorBookJoin));
            mainquery.select(authorRoot)
                    .where(builder.greaterThan(subquery, 1L));

            TypedQuery<Author> qa = em.createQuery(mainquery);
            qa.getResultStream()
                            .forEach(System.out::println);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}