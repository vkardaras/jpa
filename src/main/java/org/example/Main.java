package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entities.Customer;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

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

            CriteriaBuilder builder = em.getCriteriaBuilder();

//            CriteriaQuery<Customer> cp = builder.createQuery(Customer.class);
//            Root<Customer> customerRoot = cp.from(Customer.class);
//            cp.select(customerRoot); // SELECT c FROM Customer c
//            TypedQuery<Customer> query = em.createQuery(cp);

//            CriteriaQuery<String> cp = builder.createQuery(String.class);
//            Root<Customer> customerRoot = cp.from(Customer.class);
//            cp.select(customerRoot.get("name")); // SELECT c.name FROM Customer c
//            TypedQuery<String> query = em.createQuery(cp);

            CriteriaQuery<Object[]> cp = builder.createQuery(Object[].class);
            Root<Customer> customerRoot = cp.from(Customer.class);
            cp.multiselect(customerRoot.get("name"), customerRoot.get("id")) // SELECT c.name, c.id FROM Customer c
                    .where(builder.ge(customerRoot.get("id"), 3)) // WHERE c.id >= 5
                    .orderBy(builder.desc(customerRoot.get("id"))); // ORDER BY c.id DESC
            TypedQuery<Object[]> query = em.createQuery(cp);

            query.getResultList().forEach(o -> System.out.println(o[0] + " " + o[1]));

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}