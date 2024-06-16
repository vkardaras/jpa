package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

            Product p = new Product();
            p.setId(3L);
            p.setName("Coffee");

            em.persist(p);  // add this to the context -> NOT AN INSERT QUERY

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}