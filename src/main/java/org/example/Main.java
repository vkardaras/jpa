package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Product;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

            Product p = new Product();
            p.setId(1L);
            p.setName("Beer");

            em.persist(p);  // add this to the context -> NOT AN INSERT QUERY

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}