package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
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

            // SELECT, UPDATE, DELETE

            /*
            // String jpql = "SELECT p FROM Product p";
            // String jpql = "SELECT p FROM Product p WHERE p.price > 5";
            String jpql = "SELECT p FROM Product p WHERE p.price > :price AND p.name LIKE :name";

            // SELECT p FROM Product p      ===> Fetch all the attributes of the Product entity from the current context
            // SELECT * FROM Product        ===> Fetch all the columns from the table product
            TypedQuery<Product> q = em.createQuery(jpql, Product.class);

            q.setParameter("price", 5);
            q.setParameter("name", "%a%"); // LIKE

            List<Product> products = q.getResultList();

            for (Product p: products) {
                System.out.println(p);
            }

             */


            String jpql = "SELECT AVG(p.price) FROM Product p"; // AVG, SUM, MIN, MAX ...

            TypedQuery<Double> q = em.createQuery(jpql, Double.class);

            Double avg = q.getSingleResult();

            System.out.println(avg);

            jpql = "SELECT COUNT(p.price) FROM Product p"; // AVG, SUM, MIN, MAX ...

            TypedQuery<Long> q2 = em.createQuery(jpql, Long.class);

            Long count = q2.getSingleResult();

            System.out.println(count);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}