package org.example;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.example.entities.Author;
import org.example.entities.Book;
import org.example.entities.BookShop;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

            // Author -> Book

            /*
            EntityGraph<?> graph = em.createEntityGraph(Author.class);
            graph.addAttributeNodes("books");

            em .createQuery("SELECT a FROM Author a", Author.class)
                            .setHint("jakarta.persistence.loadgraph", graph)
                            .getResultList()
                                    .forEach(a -> System.out.println(a.getBooks()));

             */

            // Author -> Book -> BookShop

            /*
            EntityGraph<?> graph = em.createEntityGraph(Author.class);
            Subgraph<?> bookSubgraph = graph.addSubgraph("books");
            bookSubgraph.addAttributeNodes("bookShops");

            em .createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a ->
                                    System.out.println(
                                            a.getBooks().stream()
                                                    .map(b -> b.getBookShops())
                                                    .collect(Collectors.toList())
                                    )
                    );

             */

            /*
            EntityGraph<?> graph = em.getEntityGraph("Author.eagerlyFetchBooks");

            em .createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a -> System.out.println(a.getBooks()));

             */

            EntityGraph<?> graph = em.getEntityGraph("Author.eagerlyFetchBookShops");

            em .createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a ->
                            System.out.println(
                                    a.getBooks().stream()
                                            .map(b -> b.getBookShops())
                                            .collect(Collectors.toList())
                            )
                    );


            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}